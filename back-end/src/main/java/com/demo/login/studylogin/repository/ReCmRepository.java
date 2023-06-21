package com.demo.login.studylogin.repository;


import com.demo.login.studylogin.domain.boards.CommentEntity;
import com.demo.login.studylogin.domain.boards.ReCmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReCmRepository extends JpaRepository<ReCmEntity, Long> {

    //대댓글 조회 기능 => CommentEntity(cmId)를 기준으로 cmId 내림차순으로 댓글 findAll
    List<ReCmEntity> findByCommentEntityOrderByRecmIdDesc(CommentEntity commentEntity);
}
