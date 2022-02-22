package com.bank.account.service;

import com.bank.account.exchange.AccountRequest;
import com.bank.account.exchange.AccountResponse;

/**
 * @author jitendrabhadouriya
 *
 */
public interface AccountService {

	AccountResponse validateAccount(AccountRequest request );
}
