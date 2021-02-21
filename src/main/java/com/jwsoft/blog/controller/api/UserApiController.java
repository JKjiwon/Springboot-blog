package com.jwsoft.blog.controller.api;

import com.jwsoft.blog.dto.ResponseDto;
import com.jwsoft.blog.model.RoleType;
import com.jwsoft.blog.model.User;
import com.jwsoft.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // user, password, email
        System.out.println("UserApiController: save 호출 됨");
        userService.join(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON 으로 변환(Jackson)
    }
}
