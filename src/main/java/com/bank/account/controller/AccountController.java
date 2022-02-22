/* (C) 2022 */
package com.bank.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.exchange.AccountRequest;
import com.bank.account.exchange.AccountResponse;
import com.bank.account.service.AccountService;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/validate")
	@ResponseStatus(HttpStatus.OK)
	public AccountResponse validateAccount(@Valid @RequestBody AccountRequest accountRequest) {

		return accountService.validateAccount(accountRequest);

	}

}
