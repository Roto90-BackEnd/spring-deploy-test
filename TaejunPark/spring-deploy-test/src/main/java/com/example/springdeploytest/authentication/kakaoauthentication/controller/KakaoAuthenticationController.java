package com.example.springdeploytest.authentication.kakaoauthentication.controller;

import com.example.springdeploytest.authentication.kakaoauthentication.controller.response_form.KakaoLoginResponseForm;
import com.example.springdeploytest.authentication.kakaoauthentication.service.KakaoAuthenticationService;
import com.example.springdeploytest.authentication.kakaoauthentication.service.response.KakaoUserInfoResponse;
import com.example.springdeploytest.authentication.redis_cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao-authentication")
public class KakaoAuthenticationController {

    final private KakaoAuthenticationService kakaoAuthenticationService;
    final private RedisCacheService redisCacheService;

    @GetMapping("/login")
    public KakaoLoginResponseForm requestLogin(@RequestParam("code") String code) throws IOException {
        log.info("requestLogin(): code {}", code);

        KakaoUserInfoResponse response = kakaoAuthenticationService.handleLogin(code);
        String accessToken = response.getAccessToken();
        String temporaryUserToken = createTemporaryUserToken(accessToken);

        return KakaoLoginResponseForm.from(response, temporaryUserToken);
    }

    private String createTemporaryUserToken(String accessToken) {
        try{
            String temporaryUserToken = UUID.randomUUID().toString();
            redisCacheService.setKeyAndValue(temporaryUserToken, accessToken, Duration.ofMinutes(5));
            return temporaryUserToken;
        } catch (Exception e) {
            throw new RuntimeException("Redis Token 생성 중 에러 발생: " + e.getMessage());
        }
    }

}