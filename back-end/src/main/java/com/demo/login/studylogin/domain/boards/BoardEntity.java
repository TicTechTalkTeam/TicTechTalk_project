package com.demo.login.studylogin.domain.boards;

import com.demo.login.studylogin.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "boardTable")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postNo;

    @Column
    private Long category;

    @Column(length=50, nullable = false)
    private String title;

    @Column(length=500, nullable = false)
    private String content;

    @Column
    private String author;

    @Column(updatable = false)
    private LocalDateTime postDate;

    @Column
    private int views;

    @Column
    private String link;

    @Column
    private int fileAttached;

    @Column
    private String originFileName;

    @Column
    private String storedFileName;

    @PrePersist
    public void prePersist() {
        postDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy="boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setAuthor(boardDTO.getAuthor());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setCategory(boardDTO.getCategory());
        boardEntity.setViews(0);
        boardEntity.setPostDate(boardDTO.getPostDate());
        boardEntity.setLink(boardDTO.getLink());

        boardEntity.setFileAttached(0); //파일이 없다.

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO, String storedFilename) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setAuthor(boardDTO.getAuthor());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setCategory(boardDTO.getCategory());
        boardEntity.setViews(0);
        boardEntity.setPostDate(boardDTO.getPostDate());
        boardEntity.setLink(boardDTO.getLink());

        boardEntity.setFileAttached(1); //파일이 있다.
        boardEntity.setOriginFileName(boardDTO.getBoardFile().getOriginalFilename());
        boardEntity.setStoredFileName(storedFilename);

        return boardEntity;
    }


    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setPostNo(boardDTO.getPostNo()); // id가 있어야만 update 쿼리 전달함
        boardEntity.setAuthor(boardDTO.getAuthor());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setCategory(boardDTO.getCategory());
        boardEntity.setViews(boardDTO.getViews());
        boardEntity.setPostDate(LocalDateTime.now());
        boardEntity.setLink(boardDTO.getLink());

        return boardEntity;
    }

    public static BoardEntity toUpdateFileEntity (BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setPostNo(boardDTO.getPostNo()); // id가 있어야만 update 쿼리 전달함
        boardEntity.setAuthor(boardDTO.getAuthor());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setCategory(boardDTO.getCategory());
        boardEntity.setViews(boardDTO.getViews());
        boardEntity.setPostDate(LocalDateTime.now());
        boardEntity.setLink(boardDTO.getLink());

        boardEntity.setFileAttached(1); //파일이 있다.
        boardEntity.setOriginFileName(boardDTO.getOriginalFileName());
        boardEntity.setStoredFileName(boardDTO.getStoredFileName());

        return boardEntity;
    }







}
