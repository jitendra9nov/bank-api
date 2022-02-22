/* s (C)2022 */
package com.bank.account.exchange;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequest {

	@NotNull(message="accountNumber can not be null")
	@JsonProperty("accountNumber")
	private String accountNumber;
	
	@JsonProperty("sources")
	private List<DataSource> sources;
	
	
	@JsonCreator
	public AccountRequest(@JsonProperty("accountNumber")String accountNumber, @JsonProperty("sources")List<DataSource> sources) {
		this.accountNumber = accountNumber;
		this.sources = sources;
	}


	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}


	/**
	 * @return the sources
	 */
	public List<DataSource> getSources() {
		return sources;
	}


	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	/**
	 * @param sources the sources to set
	 */
	public void setSources(List<DataSource> sources) {
		this.sources = sources;
	}
	
	
	
	
	
    
    
    
}
