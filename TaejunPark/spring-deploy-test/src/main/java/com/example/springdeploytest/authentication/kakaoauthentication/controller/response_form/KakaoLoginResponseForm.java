package com.example.springdeploytest.authentication.kakaoauthentication.controller.response_form;

import com.example.springdeploytest.authentication.kakaoauthentication.service.response.KakaoUserInfoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginResponseForm {
    final private String email;
    final private String nickname;
    final private String accessToken;

        public static KakaoLoginResponseForm from(
                KakaoUserInfoResponse kakaoUserInfoResponse,
                String temporaryUserToken){

            return new KakaoLoginResponseForm(
                    kakaoUserInfoResponse.getEmail(),
                    kakaoUserInfoResponse.getNickname(),
                    temporaryUserToken
            );
        }
    }