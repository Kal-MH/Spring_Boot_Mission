package dev.kalmh.community.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "#{autoGenQueue.name}")
public class SubscriberService {
    private static final Logger logger = LoggerFactory.getLogger(SubscriberService.class);

    @RabbitHandler
    public void receiveMessage(String messageRaw){
        logger.info("received: {}", messageRaw);
        //현재 나의 쿠키와 일치하는 지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication.getPrincipal().toString());
        //쿠키 값 삭제
    }
}
