/* (C) 2022 */
package com.bank.account.service;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bank.account.ds.exchange.DataSourceRequest;
import com.bank.account.ds.exchange.DataSourceResponse;
import com.bank.account.exchange.AccountRequest;
import com.bank.account.exchange.AccountResponse;
import com.bank.account.exchange.DataSource;
import com.bank.account.gateway.DataSourceGateway;
import com.bank.account.utils.WebClientUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

	@InjectMocks
	AccountServiceImpl accountService;

	@Mock
	WebClientUtil webClientUtil;

	@Mock
	WebClient webClient;

	@Mock
	DataSourceGateway dataSourceGateway;

	@Test
	@DisplayName("When one account id is provided, validate against source with True")
	void validateAccountTest_True() {

		List<DataSource> lst = new ArrayList<>();
		lst.add(DataSource.DATA_SOURCE_1);
		AccountRequest req1 = new AccountRequest("12345678", lst);

		DataSourceResponse resp1 = new DataSourceResponse(true);
		resp1.setSource(DataSource.DATA_SOURCE_1);

		when(dataSourceGateway.validateAccount(any(DataSourceRequest.class), any(DataSource.class)))
				.thenReturn(completedFuture(resp1));

		AccountResponse res = accountService.validateAccount(req1);

		assertThat(res.getResult().get(0).isValid(), equalTo(true));

	}
	@Test
	@DisplayName("When one account id is provided, validate against source with False")
	void validateAccountTest_False() {

		List<DataSource> lst = new ArrayList<>();
		lst.add(DataSource.DATA_SOURCE_2);
		AccountRequest req1 = new AccountRequest("12345678", lst);

		DataSourceResponse resp1 = new DataSourceResponse(false);
		resp1.setSource(DataSource.DATA_SOURCE_1);

		when(dataSourceGateway.validateAccount(any(DataSourceRequest.class), any(DataSource.class)))
				.thenReturn(completedFuture(resp1));

		AccountResponse res = accountService.validateAccount(req1);

		assertThat(res.getResult().get(0).isValid(), equalTo(false));

	}

}
