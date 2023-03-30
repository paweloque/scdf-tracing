package io.spring.dataflow.sample.usagecostprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
public class MyConfigController {

    @Autowired
    private Tracer tracer;

    @GetMapping("/configs")
    public List<String> cachedConfig() {
        Span newSpan = this.tracer.nextSpan().name("cachedConfig");
        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
            ArrayList<String> configs = new ArrayList<>();
            configs.add("hello");
            newSpan.tag("UserId", "getUserId()");
            try {
                Thread.sleep(new Random().nextInt(500));
            } catch (InterruptedException e) {
            }

            Span newSpan2 = this.tracer.nextSpan().name("cachedConfig-within");
            try (Tracer.SpanInScope ws2 = this.tracer.withSpan(newSpan2.start())) {
                configs.add("nesko");
                newSpan2.tag("UserId", "getUserId()-within");
                try {
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                }
            }
            finally {
                // Once done remember to end the span. This will allow collecting
                // the span to send it to a distributed tracing system e.g. Zipkin
                newSpan.end();
            }
            return configs;
        }
        finally {
            // Once done remember to end the span. This will allow collecting
            // the span to send it to a distributed tracing system e.g. Zipkin
            newSpan.end();
        }
    }

}
