package com.jwsoft.blog.service;

import com.jwsoft.blog.config.auth.PrincipalDetail;
import com.jwsoft.blog.model.Board;
import com.jwsoft.blog.model.User;
import com.jwsoft.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC를 해준다.
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(Board board, User user) { // title, content
        board.setCount(0);
        board.setUser(user);
        System.out.println(board.getTitle() + "\n" + board.getContent());
        boardRepository.save(board);
    }

    //성능을 최적화하기 위해 사용할 수도 있고 특정 트랜잭션 작업 안에서 쓰기 작업이 일어나는 것을 의도적으로 방지하기 위해 사용할 수도 있다.
    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패 : 해당 글을 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void 글삭제(int id, PrincipalDetail principal) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패 : 해당 글을 찾을 수 없습니다.");
                });

        if (board.getUser().getId() == principal.getUser().getId()) {
            boardRepository.deleteById(id);
        }
    }

    @Transactional
    public void 글수정(int id, Board requestBoard, PrincipalDetail principal) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 수정 실패 : 해당 글을 찾을 수 없습니다.");
                }); // 영속화 완료

        if (board.getUser().getId() == principal.getUser().getId()) {
            board.setTitle(requestBoard.getTitle());
            board.setContent(requestBoard.getContent());
        } // 해당 함수 종료시(Service가 종료될 때) 트랜잭션이 종료됨. 이때 더티 채킹 - 자동 업데이트 됨 (db flush)
    }
}
