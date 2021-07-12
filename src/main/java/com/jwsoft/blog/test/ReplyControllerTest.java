package com.jwsoft.blog.test;

import com.jwsoft.blog.model.Board;
import com.jwsoft.blog.model.Reply;
import com.jwsoft.blog.repository.BoardRepository;
import com.jwsoft.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/dummy/board/{id}")
    public Board getBoards(@PathVariable int id) {
        return boardRepository.findById(id).get(); // jacson 라이브러리 (object를 json으로 리턴) => 모델의 getter 호출
    }

    @GetMapping("/dummy/reply")
    public List<Reply> getReplys() {
        return replyRepository.findAll();
    }
}
