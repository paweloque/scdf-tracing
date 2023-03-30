package io.spring.dataflow.sample.usagecostprocessor;

import java.util.Random;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsageCostCalculator {

	@Autowired
	private Tracer tracer;

	private double ratePerSecond = 0.1;

	private double ratePerMB = 0.05;

	@NewSpan(name = "UsageCostCalculator.fnfn()")
	private UsageCostDetail fnfn(UsageCostDetail usageDetail) {
//		Span newSpan = tracer.nextSpan().name("UsageCostProcessor-explicit");
//		try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
		Span newSpan = this.tracer.nextSpan().name("UsageCostCalculator-within");
		try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {

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

		}

	@Bean
//	@SpanName(value = "UsageCostProcessor")
	public Function<UsageCostDetail, UsageCostDetail> processUsageCost() {
		return this::fnfn;

//		return usageDetail -> {
//			Span newSpan = this.tracer.nextSpan().name("UsageCostCalculator");
//			try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
//
//				System.out.println("***** hello from ze calculator ****+");
//				try {
//					Thread.sleep(new Random().nextInt(500));
//				} catch (InterruptedException e) {
//				}
//				UsageCostDetail usageCostDetail = new UsageCostDetail();
//				usageCostDetail.setUserId(usageDetail.getUserId());
//				usageCostDetail.setCallCost(usageDetail.getCallCost() * this.ratePerSecond);
//				usageCostDetail.setDataCost(usageDetail.getDataCost() * this.ratePerMB);
//
//				newSpan.tag("UserId", usageCostDetail.getUserId());
//				newSpan.tag("CallCost", ""+usageCostDetail.getCallCost());
//				newSpan.tag("DataCost", ""+usageCostDetail.getDataCost());
//				newSpan.event("CostReCalculated");
//
//				return usageCostDetail;
//			}
//			finally {
//				// Once done remember to end the span. This will allow collecting
//				// the span to send it to a distributed tracing system e.g. Zipkin
//				newSpan.end();
//			}
//		};
	}
}
