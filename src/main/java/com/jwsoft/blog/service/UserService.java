package com.jwsoft.blog.service;

import com.jwsoft.blog.model.RoleType;
import com.jwsoft.blog.model.User;
import com.jwsoft.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC를 해준다.
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영혹화 시키고, 영속화된 User 오브젝트를 수정
        // Select를 해서 User오브젝트를 DB로 가져오는 이유는 영속화를 하기 위해서
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 말려줌

        User persistance = userRepository.findById(user.getId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("회원 수정 실패 : 해당 유저를 찾을 수 없습니다.");
                });

        // Validate 체크 !!
        if (persistance.getOauth() == null || persistance.getOauth().equals("")) { // 일반 사용자
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
        }

        persistance.setEmail(user.getEmail());

        // 회원 수정 함수 종료시 = 서비스 종료시 = 트랜잭션 종료 = commit 이 자동으로 된다.
        // 역속화된 persistanceUser 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });
        return user;
    }

}
