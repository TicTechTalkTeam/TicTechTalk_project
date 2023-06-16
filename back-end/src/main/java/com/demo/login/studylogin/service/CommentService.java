package com.demo.login.studylogin.service;

import com.demo.login.studylogin.domain.boards.BoardEntity;
import com.demo.login.studylogin.domain.boards.CommentEntity;
import com.demo.login.studylogin.dto.CommentDTO;
import com.demo.login.studylogin.repository.BoardRepository;
import com.demo.login.studylogin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    //댓글 쓰기
    public Long save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getPostNo());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getCmId();
        } else {
            return null;
        }
    }

    //댓글 조회
    public List<CommentDTO> findAll(Long postNo) {
        BoardEntity boardEntity = boardRepository.findById(postNo).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByCmIdDesc(boardEntity);

        List<CommentDTO> commentDTOList = new ArrayList<>();

        for(CommentEntity commentEntity : commentEntityList){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, postNo);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }


    //댓글 삭제
    public void delete(Long cmId) {
        commentRepository.deleteById(cmId);
    }

    public CommentDTO findById(Long cmId) {
        commentRepository.findById(cmId);

        return null;
    }

}
