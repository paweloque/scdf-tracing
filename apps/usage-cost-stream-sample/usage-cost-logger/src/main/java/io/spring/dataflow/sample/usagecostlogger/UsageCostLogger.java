package io.spring.dataflow.sample.usagecostlogger;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsageCostLogger {

	private static final Logger logger = LoggerFactory.getLogger(UsageCostLoggerApplication.class);

	@Autowired
	private Tracer tracer;

	@Bean
//	@SpanName(value = "UsageCostLogger")
	public Consumer<UsageCostDetail> process() {
		return usageCostDetail -> {
			Span newSpan = tracer.nextSpan().name("UsageCostLogger");
			try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {

				System.out.println("***** Souting that stuff *****");
				logger.info("***** Logging that stuff *****");
				logger.info(usageCostDetail.toString());

				// ...
				// You can tag a span
				newSpan.tag("UserId", usageCostDetail.getUserId());
				newSpan.tag("CallCost", ""+usageCostDetail.getCallCost());
				newSpan.tag("DataCost", ""+usageCostDetail.getDataCost());
				// ...
				// You can log an event on a span
//				newSpan.event("taxCalculated");
			}
			finally {
				// Once done remember to end the span. This will allow collecting
				// the span to send it to a distributed tracing system e.g. Zipkin
				newSpan.end();
			}

		};
	}
}