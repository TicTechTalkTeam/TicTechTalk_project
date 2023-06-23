package com.demo.login.studylogin.repository;

import com.demo.login.studylogin.domain.boards.BoardEntity;
import com.demo.login.studylogin.domain.members.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {


     /*
      조회수 처리
      update board_table set board_views = board_views+1 where post_no=?
      */
    @Modifying
    @Query(value="update BoardEntity  b set b.views=b.views+1 where b.postNo=:postNo")
    void updateViews(@Param("postNo") Long postNo);

    //검색 기능
    Page<BoardEntity> findByTitleContaining(String searchKeyword, Pageable pageable);

    //해당 회원 글의 수정
    Optional<BoardEntity> findByPostNoAndUserEntity(Long postNo, User userEntity);

    //UserNo로 UserEntity 안의 내용 불러오기
    List<BoardEntity> findByUserEntityUserNo(Long userNo);
}
