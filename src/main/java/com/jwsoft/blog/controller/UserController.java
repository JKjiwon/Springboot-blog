package com.jwsoft.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwsoft.blog.model.KakaoProfile;
import com.jwsoft.blog.model.OAuthToken;
import com.jwsoft.blog.model.User;
import com.jwsoft.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    @Value("${cos.client-id}")
    private String clientId;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
    // 그냥 주소가 / 이면 index.jsp
    // static 이하에 있는 /js**, /css/**, image/**
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        // Data를 리턴해주는 컨트롤러 함수
        // POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> parms = new LinkedMultiValueMap<>();
        parms.add("grant_type", "authorization_code");
        parms.add("client_id", clientId);
        parms.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        parms.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakakoTokenRequest =
                new HttpEntity<>(parms, headers); // body값과 header값을 가지고 있는 entity

        // HTTP 요청하기 - post방식으로 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakakoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate rt2 = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakakoProfileRequest2 =
                new HttpEntity<>(headers2); // body값과 header값을 가지고 있는 entity

        // HTTP 요청하기 - post방식으로 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakakoProfileRequest2,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // username, password, email
        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        // 가입자 혹은 비가입자 체크 해서 처리
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        if (originUser.getUsername() == null) {
            userService.회원가입(kakaoUser);
        }
        // 로그인 처리
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //garbagePassword는 로그인 요청시마다 바뀐다. 그러면 authenticationManager 인증이 안되지 않냐?? -? cosKey 키로 동일한 값 할당.!!!

        return "redirect:/";
    }
}
