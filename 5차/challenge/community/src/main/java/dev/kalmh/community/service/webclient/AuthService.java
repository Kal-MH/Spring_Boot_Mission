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
    private final String REDIRECT_URI = "request_from=http://127.0.0.1:8080/home";

    public AuthService(
            @Autowired
            WebClient authClient
    ) {
        this.authClient = authClient;
    }

    public UserInfoDto getUserInfo(String tokenValue) {
        String uri = String.format("http://localhost:8000/request-userinfo");

        logger.info("AuthService uri : " + uri);
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
//        Optional<UserInfoDto> userInfoDto = this.ssoClient
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(UserInfoDto.class)
//                .flux()
//                .toStream()
//                .findFirst();
//
//        logger.info("SsoService getUserInfo uri : {}", uri);
//        if (userInfoDto.isEmpty()) {
//            return null;
//        }
//        logger.info("SsoService getUserInfo userInfoDto : {}", userInfoDto);
//        return userInfoDto.get();
    }
}
