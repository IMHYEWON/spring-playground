package com.hyewon.springplayground.external.oauth.client;

import com.hyewon.springplayground.external.oauth.dto.KakaoTokenDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoTokenRequester {

    public KakaoTokenDto.Response requestKakaoToken(String contentType, KakaoTokenDto.Request request) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", contentType);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", request.getGrant_type());
        map.add("client_id", request.getClient_id());
        map.add("redirect_uri", request.getRedirect_uri());
        System.out.println("redirect_uri at KaKaoTokenRquester : " + request.getRedirect_uri());
        map.add("code", request.getCode());
        map.add("client_secret", request.getClient_secret());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<KakaoTokenDto.Response> responseEntity = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                KakaoTokenDto.Response.class
        );

        return responseEntity.getBody();
    }

}