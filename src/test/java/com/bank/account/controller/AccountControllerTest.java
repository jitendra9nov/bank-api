/* (C) 2022 */
package com.bank.account.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.account.ds.exchange.DataSourceRequest;
import com.bank.account.ds.exchange.DataSourceResponse;
import com.bank.account.exchange.AccountRequest;
import com.bank.account.exchange.DataSource;
import com.bank.account.gateway.DataSourceGateway;
import com.bank.account.service.AccountService;
import com.bank.account.utils.WebClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	AccountService accountStervice;

	@Autowired
	DataSourceGateway dataSourceGateway;

	@MockBean
	WebClientUtil webClientUtil;

	private static final ObjectMapper om = new ObjectMapper();

	@BeforeEach
	public void init() {

		DataSourceResponse resp1 = new DataSourceResponse(false);
		resp1.setSource(DataSource.DATA_SOURCE_1);
		DataSourceResponse resp2 = new DataSourceResponse(true);
		resp1.setSource(DataSource.DATA_SOURCE_2);
		
		DataSourceRequest req=new DataSourceRequest("12345678");

		when(webClientUtil.callForResponse("https://source1.com/v1/api/account/validate", req,
				DataSourceResponse.class)).thenReturn(new ResponseEntity<>(resp1, HttpStatus.OK));

		when(webClientUtil.callForResponse("https://source2.com/v2/api/account/validate", req,
				DataSourceResponse.class)).thenReturn(new ResponseEntity<>(resp2, HttpStatus.OK));

	}

	@Test
	@DisplayName("When one account id is provided, validate against source")
	void validateAccountTest() throws Exception {

		AccountRequest req = new AccountRequest("12345678", null);
		mockMvc.perform(post("/accounts/validate").content(om.writeValueAsBytes(req)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.result[0].isValid", equalTo(false)));

		verify(webClientUtil, times(2)).callForResponse(any(), any(), any());
		verify(dataSourceGateway, times(1)).validateAccount(any(), any());
	}

}
