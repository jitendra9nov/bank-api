/* (C) 2022 */
package com.bank.account.gateway;

import static com.bank.account.exchange.DataSource.form;
import static java.util.concurrent.CompletableFuture.completedFuture;

import com.bank.account.config.DataSourceProperties;
import com.bank.account.config.DataSourceProperties.Source;
import com.bank.account.ds.exchange.DataSourceRequest;
import com.bank.account.ds.exchange.DataSourceResponse;
import com.bank.account.exception.ServiceException;
import com.bank.account.exchange.DataSource;
import com.bank.account.utils.WebClientUtil;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DataSourceGatewayImpl implements DataSourceGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceGatewayImpl.class);

	private DataSourceProperties dataSourceProperties;

	private final WebClientUtil webClientUtil;

	@Autowired
	DataSourceGatewayImpl(WebClientUtil webClientUtil, DataSourceProperties dataSourceProperties) {
		this.dataSourceProperties=dataSourceProperties;
		this.webClientUtil = webClientUtil;
	}

	@Override
	@Async("asyncExecutor")
	public CompletableFuture<DataSourceResponse> validateAccount(DataSourceRequest request, DataSource source) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("ASYNC::validating the Account {} with Source {}", request.getAccountNumber(), source.val());
		}

		DataSourceResponse response = null;

		String url = dataSourceProperties.getList().stream().filter(src -> source == form(src.getName()))
				.map(Source::getUrl).findFirst().orElse(null);

		ResponseEntity<DataSourceResponse> resp = webClientUtil.callForResponse(url, request, DataSourceResponse.class);

		if (null != resp && resp.getStatusCode().is2xxSuccessful()) {
			response = resp.getBody();
			
		}
		if (null == response) {
			LOGGER.warn("ASYNC:: Failed to validate status for Account {} with Source {}", request.getAccountNumber(),
					source.val());
			
			throw new ServiceException(String.format("Unable to validate Account %s with Source %s",
					request.getAccountNumber(), source.val()), HttpStatus.NOT_FOUND);
		}
		response.setSource(source);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("ASYNC:: Validation status for Account {} with Source {} is {}", request.getAccountNumber(),
					source.val(), response.isValid());
		}

		return completedFuture(response);
	}

}
