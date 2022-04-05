package dev.kalmh.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PublisherService {
    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public PublisherService(
            RabbitTemplate rabbitTemplate,
            FanoutExchange fanoutExchange
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchange = fanoutExchange;
    }

    public void publishMessage(String cookieToken){
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                "",
                cookieToken
        );
    }
}
