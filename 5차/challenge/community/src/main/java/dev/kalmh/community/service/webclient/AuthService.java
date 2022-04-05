package dev.kalmh.community.service.webclient;

import dev.kalmh.community.controller.dto.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final WebClient authClient;

    public AuthService(
            @Autowired
            WebClient authClient
    ) {
        this.authClient = authClient;
    }

    public UserInfoDto getUserInfo(String tokenValue) {
        logger.info("AuthService tokenValue : " + tokenValue);
        UserInfoDto userInfoDto = this.authClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/request-userinfo")
                                .queryParam("token_value", tokenValue)
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.error(clientResponse.statusCode().toString());
                    return Mono.empty();
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)))
                .bodyToMono(UserInfoDto.class)
                .block();
        return userInfoDto;
    }
}
