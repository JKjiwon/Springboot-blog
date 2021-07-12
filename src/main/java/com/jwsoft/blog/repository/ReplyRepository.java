package com.jwsoft.blog.repository;

import com.jwsoft.blog.dto.ReplySaveRequestDto;
import com.jwsoft.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Modifying
    @Query(value = "INSERT INTO Reply(userId, boardID, content, createDate) VALUES(?1, ?2, ?3, now(6))", nativeQuery = true)
    int mSave(int userId, int boardId, String content);

}
