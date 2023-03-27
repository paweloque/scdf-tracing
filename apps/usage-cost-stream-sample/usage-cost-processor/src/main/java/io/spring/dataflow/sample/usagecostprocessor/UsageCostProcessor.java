package io.spring.dataflow.sample.usagecostprocessor;

import java.util.Random;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class UsageCostProcessor {

	@Autowired
	private Tracer tracer;

	private double ratePerSecond = 0.1;

	private double ratePerMB = 0.05;

	@Bean
//	@SpanName(value = "UsageCostProcessor")
	public Function<UsageDetail, UsageCostDetail> processUsageCost() {
		return usageDetail -> {
			Span newSpan = tracer.nextSpan().name("UsageCostProcessor");
			try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
				System.out.println("***** hello from ze streamzy ****+");
				try {
					Thread.sleep(new Random().nextInt(500));
				} catch (InterruptedException e) {
				}
				UsageCostDetail usageCostDetail = new UsageCostDetail();
				usageCostDetail.setUserId(usageDetail.getUserId());
				usageCostDetail.setCallCost(usageDetail.getDuration() * this.ratePerSecond);
				usageCostDetail.setDataCost(usageDetail.getData() * this.ratePerMB);
				newSpan.event("usageCostCalculated");

				return usageCostDetail;
			}
			finally {
				// Once done remember to end the span. This will allow collecting
				// the span to send it to a distributed tracing system e.g. Zipkin
				newSpan.end();
			}


		};
	}
}
