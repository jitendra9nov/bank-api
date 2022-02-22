/* (C) 2022 */
package com.bank.account.gateway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.bank.account.config.DataSourceProperties;
import com.bank.account.config.DataSourceProperties.Source;
import com.bank.account.ds.exchange.DataSourceRequest;
import com.bank.account.ds.exchange.DataSourceResponse;
import com.bank.account.exchange.DataSource;
import com.bank.account.utils.WebClientUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class DataSourceGatewayImplTest {

	@InjectMocks
	DataSourceGatewayImpl dataSourceGateway;

	@Mock
	WebClientUtil webClientUtil;

	@Mock
	DataSourceProperties dataSourceProperties;

	@Mock
	WebClient webClient;

	@Test
	@DisplayName("When one account id is provided, validate against source with True")
	void validateAccountTest_True() throws InterruptedException, ExecutionException {

		List<Source> lst = new ArrayList<>();
		Source s = new Source();
		s.setName("source1");
		s.setUrl("https://source1.com/v1/api/account/validate");
		
		Source s1= new Source();
		s1.setName("source2");
		s1.setUrl("https://source2.com/v2/api/account/validate");
		lst.add(s);
		lst.add(s1);
		when(dataSourceProperties.getList()).thenReturn(lst);

		DataSourceRequest req = new DataSourceRequest("12345678");

		DataSourceResponse resp1 = new DataSourceResponse(true);
		resp1.setSource(DataSource.DATA_SOURCE_1);

		when(webClientUtil.callForResponse(any(String.class), any(Object.class), eq(DataSourceResponse.class)))
		.thenReturn(new ResponseEntity<>(resp1, HttpStatus.OK));

		CompletableFuture<DataSourceResponse> res = dataSourceGateway.validateAccount(req, DataSource.DATA_SOURCE_1);

		assertThat(res.get().isValid(), equalTo(true));

	}

	@Test
	@DisplayName("When one account id is provided, validate against source with False")
	void validateAccountTest_False() throws InterruptedException, ExecutionException {


		List<Source> lst = new ArrayList<>();
		Source s = new Source();
		s.setName("source1");
		s.setUrl("https://source1.com/v1/api/account/validate");
		
		Source s1= new Source();
		s1.setName("source2");
		s1.setUrl("https://source2.com/v2/api/account/validate");
		lst.add(s);
		lst.add(s1);
		when(dataSourceProperties.getList()).thenReturn(lst);

		DataSourceRequest req = new DataSourceRequest("12345678");

		DataSourceResponse resp1 = new DataSourceResponse(false);
		resp1.setSource(DataSource.DATA_SOURCE_2);

		when(webClientUtil.callForResponse(any(String.class), any(Object.class), eq(DataSourceResponse.class)))
				.thenReturn(new ResponseEntity<>(resp1, HttpStatus.OK));

		CompletableFuture<DataSourceResponse> res = dataSourceGateway.validateAccount(req, DataSource.DATA_SOURCE_2);

		assertThat(res.get().isValid(), equalTo(false));

	}

}
