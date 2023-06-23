package com.demo.login.studylogin.repository;

import com.demo.login.studylogin.domain.boards.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

    boolean existsByUserNoAndPostNo(Long userNo, Long postNo);
    List<Bookmark> findAllByUserNo(Long userNo);

    Bookmark findByPostNo(Long cancelPostNo);


//    @Query(value = "select b from Bookmark b where b.userNo = :userNo")
//    List<Bookmark> findByBookmark(@Param("userNo") Long userNo);
}
