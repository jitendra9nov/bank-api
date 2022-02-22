/* (C) 2022 */
package com.bank.account.service;

import static com.bank.account.exchange.DataSource.values;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.bank.account.ds.exchange.DataSourceRequest;
import com.bank.account.ds.exchange.DataSourceResponse;
import com.bank.account.exchange.AccountRequest;
import com.bank.account.exchange.AccountResponse;
import com.bank.account.exchange.DataSource;
import com.bank.account.exchange.Result;
import com.bank.account.gateway.DataSourceGateway;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	private final DataSourceGateway dataSourceGateway;

	@Autowired
	AccountServiceImpl(DataSourceGateway dataSourceGateway) {
		this.dataSourceGateway = dataSourceGateway;
	}

	@Override
	public AccountResponse validateAccount(AccountRequest request) {

		AccountResponse atmD = null;

		final String accountNumber = request.getAccountNumber();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling Data Source Service for Account {}", accountNumber);
		}

		final List<DataSource> sources = isEmpty(request.getSources()) ? Arrays.asList(values()) : request.getSources();

		List<CompletableFuture<DataSourceResponse>> futures = sources.stream()
				.map(src -> dataSourceGateway.validateAccount(new DataSourceRequest(accountNumber), src))
				.collect(toList());

		// Wait until they are all done

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

		atmD = new AccountResponse(futures.stream().map(fut -> {
			 Result result = null;
			try {
				final DataSourceResponse dataSourceResponse = fut.get();
				result=new Result(dataSourceResponse.getSource(), dataSourceResponse.isValid());
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.warn("Exception occurred while processing response for Account {}", accountNumber, e);
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}

			return result;
		}).collect(toList()));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Finished call Data Source Service for Account {}", accountNumber);
		}

		return atmD;
	}

}
