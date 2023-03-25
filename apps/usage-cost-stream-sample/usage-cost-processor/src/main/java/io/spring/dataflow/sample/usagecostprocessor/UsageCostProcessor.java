package io.spring.dataflow.sample.usagecostprocessor;

import java.util.function.Function;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class UsageCostProcessor {

	private double ratePerSecond = 0.1;

	private double ratePerMB = 0.05;

	@Bean
	public Function<UsageDetail, UsageCostDetail> processUsageCost() {
		return usageDetail -> {
			System.out.println("***** hello from ze streamzy ****+");
			UsageCostDetail usageCostDetail = new UsageCostDetail();
			usageCostDetail.setUserId(usageDetail.getUserId());
			usageCostDetail.setCallCost(usageDetail.getDuration() * this.ratePerSecond);
			usageCostDetail.setDataCost(usageDetail.getData() * this.ratePerMB);
			return usageCostDetail;
		};
	}
}
