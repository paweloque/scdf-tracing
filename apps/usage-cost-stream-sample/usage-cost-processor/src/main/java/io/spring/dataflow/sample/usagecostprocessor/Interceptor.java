package io.spring.dataflow.sample.usagecostprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalChannelInterceptor
public class Interceptor implements ChannelInterceptor {

    private final Logger log = LoggerFactory.getLogger(Interceptor.class);

    @Autowired
    private Tracer tracer;

    @Override
    public Message<?> preSend(Message<?> msg, MessageChannel mc) {
        log.info("In preSend");
        System.out.println("******** In preSend ********");

//        Span longSpan = tracer.nextSpan().name("Interceptor-preSend");
        return msg;
    }

    @Override
    public void postSend(Message<?> msg, MessageChannel mc, boolean bln) {
        log.info("In postSend");
        System.out.println("******** In postSend ********");

//        Span span = tracer.currentSpan();
//        if (span != null) {
//            span.end();
//        }
    }

    @Override
    public void afterSendCompletion(Message<?> msg, MessageChannel mc, boolean bln, Exception excptn) {
        log.info("In afterSendCompletion");
        System.out.println("******** In afterSendCompletion ********");
    }

    @Override
    public boolean preReceive(MessageChannel mc) {
        log.info("In preReceive");
        System.out.println("******** In preReceive ********");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> msg, MessageChannel mc) {
        log.info("In postReceive");
        System.out.println("******** In postReceive ********");
        return msg;
    }

    @Override
    public void afterReceiveCompletion(Message<?> msg, MessageChannel mc, Exception excptn) {
        log.info("In afterReceiveCompletion");
        System.out.println("******** In afterReceiveCompletion ********");
    }

}