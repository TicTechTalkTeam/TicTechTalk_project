package com.demo.login.studylogin.controller;

import com.demo.login.studylogin.dto.CommentDTO;
import com.demo.login.studylogin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        commentService.save(commentDTO);
        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getPostNo());

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cmId}")
    public ResponseEntity delete(@PathVariable Long cmId, @ModelAttribute CommentDTO commentDTO) {
        commentService.delete(cmId);

        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getPostNo());
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

}
