package com.jwsoft.blog.controller;

import com.jwsoft.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    // /WEB-INF/views/index.jsp
    @GetMapping({"", "/"}) // 컨트롤러에서 세션을 어떻게 찾나?
    public String index(@AuthenticationPrincipal PrincipalDetail principal) { // 로그인 세션에 접근
        System.out.println("principal.getUsername() = " + principal.getUsername());
        return "index";
    }

}
