/* s (C)2022 */
package com.bank.account.exchange;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountResponse {

	
	@JsonProperty("result")
	private List<Result> result;
	
	
	@JsonCreator
	public AccountResponse(@JsonProperty("result")List<Result> result) {
		this.result = result;
	}


	/**
	 * @return the result
	 */
	public List<Result> getResult() {
		return result;
	}


	/**
	 * @param result the result to set
	 */
	public void setResult(List<Result> result) {
		this.result = result;
	}


    
    
    
}
