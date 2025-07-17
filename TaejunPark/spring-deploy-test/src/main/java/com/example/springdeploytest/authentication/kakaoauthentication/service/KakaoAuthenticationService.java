package com.example.springdeploytest.authentication.kakaoauthentication.service;


import com.example.springdeploytest.authentication.kakaoauthentication.service.response.KakaoUserInfoResponse;

public interface KakaoAuthenticationService {
    KakaoUserInfoResponse handleLogin(String code);




}
