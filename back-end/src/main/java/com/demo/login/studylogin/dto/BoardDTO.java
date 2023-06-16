package com.demo.login.studylogin.dto;

import com.demo.login.studylogin.domain.boards.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor//기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long postNo;
    private Long category;
    private String title;
    private String content;
    private String author;
    private LocalDateTime postDate;
    private int views;
    private String link;

    //파일 첨부 관련
    private MultipartFile boardFile; //스프링에서 제공하는 interface. 실제 파일을 담아줄 수 있는 역할. save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부( 첨부1, 미첨부0)

    public BoardDTO(long postNo, String author, String title, int views, LocalDateTime postDate) {
        this.postNo = postNo;
        this.author = author;
        this.title = title;
        this.views = views;
        this.postDate = postDate;
    }


    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setPostNo(boardEntity.getPostNo());
        boardDTO.setCategory(boardEntity.getCategory());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContent(boardEntity.getContent());
        boardDTO.setAuthor(boardEntity.getAuthor());
        boardDTO.setPostDate(boardEntity.getPostDate());
        boardDTO.setViews(boardEntity.getViews());
        boardDTO.setLink(boardEntity.getLink());

        if(boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(0);
        } else {
            boardDTO.setFileAttached(1);
            boardDTO.setOriginalFileName(boardEntity.getOriginFileName());
            boardDTO.setStoredFileName(boardEntity.getStoredFileName());
        }

        return boardDTO;
    }

}
