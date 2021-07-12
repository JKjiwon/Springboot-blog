package com.jwsoft.blog.test;

import lombok.*;

//@Getter
//@Setter
@Data // getter, setter
@NoArgsConstructor
//@RequiredArgsConstructor // final 붙은 맴버변수에 대한 생성자
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
