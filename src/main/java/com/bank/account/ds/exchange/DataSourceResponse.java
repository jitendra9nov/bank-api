/* s (C)2022 */
package com.bank.account.ds.exchange;

import com.bank.account.exchange.DataSource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceResponse {

	@JsonProperty("isValid")
	private boolean isValid;

	@JsonIgnore
	private DataSource source;

	@JsonCreator
	public DataSourceResponse(@JsonProperty("isValid") boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the source
	 */
	public DataSource getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(DataSource source) {
		this.source = source;
	}

}
