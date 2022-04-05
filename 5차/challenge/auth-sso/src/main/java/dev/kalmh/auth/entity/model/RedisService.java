package dev.kalmh.auth.entity.model;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    private final RedisRepository redisRepository;

    public RedisService(
            RedisRepository redisRepository
    ) {
        this.redisRepository = redisRepository;
    }

    public void saveUserInfo(String userId, String username) throws InterruptedException {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setUsername(username);
        userInfo.setStatus(1);
        userInfo = redisRepository.save(userInfo);

        logger.info("Saved UserInfo : {}", userInfo.getId());
    }

    public UserInfo retrieveUserInfo(String userId) {
        logger.info("RedisService retrieveUserInfo userId : {}", userId);
        Optional<UserInfo> userInfoOptional = this.redisRepository.findById(userId);
        if (userInfoOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userInfoOptional.get();
    }
}
