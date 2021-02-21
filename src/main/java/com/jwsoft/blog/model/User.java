package com.jwsoft.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
// ORM -> JAVA(다른언어) Object -> 테이블로 맵핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성된다.
//@DynamicInsert // Insert할 때 null인 필드는 제외!
public class User {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 30, unique = true)
    private String username; // id

    @Column(nullable = false, length = 100) // 123457 => 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

     // @ColumnDefault("'USER'")
    // DB SMS RoleType이 없다
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는게 좋다. // ADMIN, USER 도메인(범위) 설정!!

    @CreationTimestamp // 유저 생성시간에 시간이 자동으로 입력된다.
    private Timestamp createDate;
}
