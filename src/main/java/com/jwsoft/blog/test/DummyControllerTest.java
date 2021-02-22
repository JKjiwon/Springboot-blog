package com.jwsoft.blog.test;

import com.jwsoft.blog.model.Board;
import com.jwsoft.blog.model.RoleType;
import com.jwsoft.blog.model.User;
import com.jwsoft.blog.repository.BoardRepository;
import com.jwsoft.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor // 성성자 + DI
@RestController // html파일이 아니라 data를 리턴해주는 controller = RestController
public class DummyControllerTest {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    // http://localhost:8080/blog/dummy/join (요청)
    // http 의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) { // key=value (약속된 규칙)
        user.setRole(RoleType.USER);
        System.out.println("id = " + user.getId());
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("email = " + user.getEmail());
        System.out.println("role = " + user.getRole());
        System.out.println("createDate = " + user.getCreateDate());


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
    public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
//        List<User> users = pagingUser.getContent();
        return pagingUser;
    }

    @GetMapping("/dummy/content")
    public Page<Board> contentList(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> pagingUser = boardRepository.findAll(pageable);
//        List<User> users = pagingUser.getContent();
        return pagingUser;
    }

//    save함수는 id를 전달하지 않으면 insert를 해주고
//    save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
//    save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요.

    // email, password
    @Transactional // 함수종료시에 자동 commit이 됨
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // Json 데이터 요청 => Java Object(Message Converter의 Jackson라이브러리가 변환해서 받아줌)

        System.out.println("id = " + id);
        System.out.println("password = " + requestUser.getPassword());
        System.out.println("email = " + requestUser.getEmail());


        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패했습니다");
        }); // 영속화 되는 시점

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail()); // 변경을 해야 upadate문이 수행된다.!

//        userRepository.save(user);

        // 더티 체킹 @Transactional
        return user;
    }

//    @DeleteMapping("/dummy/user/{id}")
//    public User deleteUser(@PathVariable int id){
//        User user = userRepository.findById(id).orElseThrow(() -> {
//            return new IllegalArgumentException("삭제에 실패했습니다");
//        });
//        userRepository.delete(user);
//
//        return user;
//    }

    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패했습니다. 해당 id는 DB에 없습니다.";
        }
        return "삭제되었습니다. id: " + id;
    }
}
