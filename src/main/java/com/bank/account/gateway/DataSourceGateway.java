package com.bank.account.gateway;

import java.util.concurrent.CompletableFuture;

import com.bank.account.ds.exchange.DataSourceRequest;
import com.bank.account.ds.exchange.DataSourceResponse;
import com.bank.account.exchange.DataSource;

/**
 * @author jitendrabhadouriya
 *
 */
public interface DataSourceGateway {

	CompletableFuture<DataSourceResponse> validateAccount(DataSourceRequest request , DataSource source);
}
