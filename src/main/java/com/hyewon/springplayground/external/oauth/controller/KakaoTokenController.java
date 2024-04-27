package com.hyewon.springplayground.external.oauth.controller;

import com.hyewon.springplayground.external.oauth.client.KakaoTokenRequester;
import com.hyewon.springplayground.external.oauth.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    private final KakaoTokenRequester kakaoTokenRequester;

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/oauth/kakao/callback")
    public @ResponseBody String loginCallback(String code) throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
                .redirect_uri("http://localhost:8080/oauth/kakao/callback")
                .build();
        System.out.println(kakaoTokenRequestDto.toString());
        System.out.println("redirect_uri at controller : " + kakaoTokenRequestDto.getRedirect_uri());

        String response = kakaoTokenRequester.requestKakaoToken(contentType, kakaoTokenRequestDto).toString();
        return "kakao token : " + response;
    }

}
