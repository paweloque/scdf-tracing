package io.spring.dataflow.sample.usagedetailsender;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties(prefix = "custom.nesko")
public class UsageDetailSender {

	@Value("${custom.nesko.karenzfrist}")
	private String karenzfrist;

	private String[] users = {"user1", "user2", "user3", "user4", "user5"};

	@Bean
	public Supplier<UsageDetail> sendEvents() {
		return () -> {
			System.out.println(String.format("*** Our custom property: %s ***", karenzfrist));
			UsageDetail usageDetail = new UsageDetail();
			usageDetail.setUserId(this.users[new Random().nextInt(5)]);
			usageDetail.setDuration(new Random().nextInt(300));
			usageDetail.setData(new Random().nextInt(700));
			return usageDetail;
		};
	}
}
