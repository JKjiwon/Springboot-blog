package com.jwsoft.blog.test;

import com.jwsoft.blog.model.RoleType;
import com.jwsoft.blog.model.User;
import com.jwsoft.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor // 성성자 + DI
@RestController // html파일이 아니라 data를 리턴해주는 controller = RestController
public class DummyControllerTest {

    private final UserRepository userRepository;


    // http://localhost:8080/blog/dummy/join (요청)
    // http 의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) { // key=value (약속된 규칙)
        System.out.println("id = " + user.getId());
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("email = " + user.getEmail());
        System.out.println("role = " + user.getRole());
        System.out.println("createDate = " + user.getCreateDate());

        user.setRole(RoleType.USER);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    // {id} : 주소로 파라메터를 받을 수 있습니다.
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // user/4을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될것 아니냐?
        // 그럼 return null 이 리턴이 되자나, 그럼 프로그램에 문제가 있지 않겠니?
        // Optional로 너의 user객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해요
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
            }
        });

//        람다식
//        User user = userRepository.findById(id).orElseThrow(()->{
//          return new IllegalArgumentException("해당 유저는 없습니다. id: "+ id);
//        });

//      요청 : 웹브라우저
//      user 객체 = 자바 오브젝트
//      변환(웹브라우저가 이해할 수 있는 데이터) -> Json
//      스프링부트 = MessageConverter라는 애가 응답시에 자동 작성
//      만약 자바 오브젝트를 리턴하게 되면 MessageCoverter가 Jackson 라이브러리를 호출해서
//      user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.!!
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건의 데이터를 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }
}
