package dev.kalmh.auth.config.redis;

import com.google.gson.Gson;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublishConfig {
    @Bean
    public FanoutExchange fanoutExchange() {return new FanoutExchange("boot.fanout.exchange");}
}
