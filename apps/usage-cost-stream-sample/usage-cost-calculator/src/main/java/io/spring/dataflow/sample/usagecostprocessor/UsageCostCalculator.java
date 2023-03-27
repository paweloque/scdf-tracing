package io.spring.dataflow.sample.usagecostprocessor;

import java.util.Random;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsageCostCalculator {

	@Autowired
	private Tracer tracer;

	private double ratePerSecond = 0.1;

	private double ratePerMB = 0.05;

	@Bean
//	@SpanName(value = "UsageCostProcessor")
	public Function<UsageCostDetail, UsageCostDetail> processUsageCost() {
		return usageDetail -> {
			Span newSpan = this.tracer.nextSpan().name("UsageCostCalculator");

			try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {

				System.out.println("***** hello from ze calculator ****+");
				try {
					Thread.sleep(new Random().nextInt(500));
				} catch (InterruptedException e) {
				}
				UsageCostDetail usageCostDetail = new UsageCostDetail();
				usageCostDetail.setUserId(usageDetail.getUserId());
				usageCostDetail.setCallCost(usageDetail.getCallCost() * this.ratePerSecond);
				usageCostDetail.setDataCost(usageDetail.getDataCost() * this.ratePerMB);

				newSpan.tag("UserId", usageCostDetail.getUserId());
				newSpan.tag("CallCost", ""+usageCostDetail.getCallCost());
				newSpan.tag("DataCost", ""+usageCostDetail.getDataCost());
				newSpan.event("CostReCalculated");

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
