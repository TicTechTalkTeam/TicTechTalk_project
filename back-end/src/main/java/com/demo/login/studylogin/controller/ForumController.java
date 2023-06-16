package com.demo.login.studylogin.controller;



import com.demo.login.studylogin.dto.BoardDTO;
import com.demo.login.studylogin.dto.CommentDTO;
import com.demo.login.studylogin.service.BoardService;
import com.demo.login.studylogin.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class ForumController {
    private final BoardService boardService;
    private final CommentService commentService;

    //게시글 목록/페이징X (리액트 완료)
    @GetMapping("/forum")
    public List<BoardDTO> findAllForum() {
        List<BoardDTO> boardDTOList = boardService.findAll();
        return boardDTOList;
    }

    //상세 보기(리액트 완료)
    @GetMapping("/{postNo}")
    public BoardDTO findById(@PathVariable Long postNo){
        // 조회수 처리
        boardService.updateViews(postNo);
        //게시글의 데이터를 가져와서 boardview.html에 출력
        BoardDTO boardDTO = boardService.findById(postNo);
        //댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(postNo);

        return boardDTO;
    }

    //글쓰기 Proc 첨부파일 X -> 상세보기로 (리액트 완료)
    @PostMapping(value = "/write")
    public ResponseEntity<?> write(@RequestBody BoardDTO boardDTO) throws IOException {
        Long postNo = boardService.save(boardDTO);
        return ResponseEntity.ok(postNo);
    }

    //수정 Form (리액트 완료)
    @GetMapping("/update/{postNo}")
    public ResponseEntity<BoardDTO> updateForm(@PathVariable Long postNo){
        log.info("진입");
        BoardDTO boardDTO = boardService.findById(postNo);
        System.out.println("update DTO"+boardDTO);

        return ResponseEntity.ok().body(boardDTO);
    }

    //수정 Proc (리액트 완료)
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody BoardDTO boardDTO) throws IOException {
        BoardDTO board = boardService.update(boardDTO);

        List<CommentDTO> commentDTOList = commentService.findAll(board.getPostNo());

        return ResponseEntity.ok(board.getPostNo());
    }

    //게시글 삭제 (리액트 완료)
    @GetMapping("/delete/{postNo}")
    public void delete(@PathVariable Long postNo){
        boardService.delete(postNo);
    }

    @GetMapping("/forum/paging")
    public String paging(String searchKeyword,
                         @PageableDefault(page = 1) Pageable pageable,
                         Model model ) {

        Page<BoardDTO> boardList;

        if (searchKeyword == null || searchKeyword.isBlank()) {
            boardList = boardService.paging(pageable);
        } else {
            boardList = boardService.forumSearch(searchKeyword, pageable);
        }

        int nowPage = boardList.getPageable().getPageNumber() + 1;
        int blockLimit = 3; // 보여지는 페이지 번호 갯수
        int startPage =(((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("forumList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("nowPage", nowPage);

        return "boardlist";
    }
}
