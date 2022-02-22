/* (C) 2022 */
package com.bank.account.exchange;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Optional;

/**
 * @author jitendrabhadouriya
 *
 */
public enum DataSource {

	@JsonProperty("source1")
	DATA_SOURCE_1("source1"), 
	
	@JsonProperty("source2")
	DATA_SOURCE_2("source2");

	private final String value;

	private static final Map<String, DataSource> FORMAT_MAP = of(values()).collect(toMap(s -> s.value, identity()));

	private DataSource(final String value) {
		this.value = value;
	}

	@JsonCreator
	public static DataSource form(final String src) {
		return Optional.ofNullable(FORMAT_MAP.get(src)).orElseThrow(() -> new IllegalArgumentException());
	}

	public String val() {
		return value;
	}
	

}
