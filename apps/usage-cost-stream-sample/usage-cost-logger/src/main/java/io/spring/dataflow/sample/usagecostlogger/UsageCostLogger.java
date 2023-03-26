package io.spring.dataflow.sample.usagecostlogger;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.sleuth.SpanName;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsageCostLogger {

	private static final Logger logger = LoggerFactory.getLogger(UsageCostLoggerApplication.class);

	@Bean
//	@SpanName(value = "UsageCostLogger")
	public Consumer<UsageCostDetail> process() {
		return usageCostDetail -> {
			System.out.println("***** Souting that stuff *****");
			logger.info("***** Logging that stuff *****");
			logger.info(usageCostDetail.toString());
		};
	}
}