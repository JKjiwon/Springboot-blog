package com.jwsoft.blog.controller.api;

import com.jwsoft.blog.config.auth.PrincipalDetail;
import com.jwsoft.blog.dto.ResponseDto;
import com.jwsoft.blog.model.Board;
import com.jwsoft.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.글쓰기(board, principal.getUser());
        System.out.println(principal.getUser().getUsername());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글삭제(id, principal);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글수정(id, board, principal);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
