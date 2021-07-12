package com.jwsoft.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // 스프링이 com.jwsoft.blog 패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는것은 아니고
// 특정 어노테이션이 붙어있는 클래스파일을 new해서(IoC) 스프링 컨테이너에서 관리해준다.
public class BlogControllerTest {
    @GetMapping("/test/hello")
    public String hello() {
        return "<h1>hello spring boot</h1>";
    }
}
