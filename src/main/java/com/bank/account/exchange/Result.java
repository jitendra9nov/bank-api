package com.bank.account.exchange;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	@JsonProperty("source")
	private DataSource source;

	@JsonProperty("isValid")
	private boolean isValid;

	@JsonCreator
	public Result(@JsonProperty("source") DataSource source, @JsonProperty("isValid") boolean isValid) {
		this.source = source;
		this.isValid = isValid;
	}

	/**
	 * @return the source
	 */
	public DataSource getSource() {
		return source;
	}

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(DataSource source) {
		this.source = source;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
