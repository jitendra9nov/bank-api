package com.bank.account.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sources")
public class DataSourceProperties {

	private List<Source> list = new ArrayList<>();

	/**
	 * @return the list
	 */
	public List<Source> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Source> list) {
		this.list = list;
	}

	public static class Source {

		private String name;

		private String url;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}

	}

}
