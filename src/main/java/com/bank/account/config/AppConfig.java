/* s (C)2022 */
package com.bank.account.config;

import java.util.concurrent.Executor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableAsync
@EnableConfigurationProperties(DataSourceProperties.class)
public class AppConfig {
	
	

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.exchangeStrategies(
						ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().maxInMemorySize(-1)).build())
				.build();
	}
	
	@Bean("asyncExecutor")
	  public Executor asyncExecutor() 
	  {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(3);
	    executor.setMaxPoolSize(3);
	    executor.setQueueCapacity(100);
	    executor.setThreadNamePrefix("AsynchThread-");
	    executor.initialize();
	    return executor;
	  }

}
