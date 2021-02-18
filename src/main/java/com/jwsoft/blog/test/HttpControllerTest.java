package com.jwsoft.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML 파일)
//@Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
    private static final String TAG = "HttpController : ";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member ssar = Member.builder().username("ssar").password("1234").email("jiwon@gmail.com").build();
        System.out.println(ssar);
        ssar.setId(5000);
        System.out.println(ssar);
        return "롬복 테스트 완료";
    }

    @GetMapping("/http/get")
    public String getTest(Member m) { // 쿼리 스트링
        return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getEmail() + ", " + m.getPassword();
    }

    // text/plain : @RequestBody String text
    // application/json: @RequestBody Member m
    // Member m :
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) { // MessageConverter(스프링부트)
        return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getEmail() + ", " + m.getPassword();

    }

    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getEmail() + ", " + m.getPassword();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
