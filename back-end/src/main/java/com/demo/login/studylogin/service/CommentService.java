package com.demo.login.studylogin.service;

import com.demo.login.studylogin.domain.boards.BoardEntity;
import com.demo.login.studylogin.domain.boards.CommentEntity;
import com.demo.login.studylogin.domain.members.User;
import com.demo.login.studylogin.dto.CommentDTO;
import com.demo.login.studylogin.repository.BoardRepository;
import com.demo.login.studylogin.repository.CommentRepository;
import com.demo.login.studylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //댓글 쓰기
    @Transactional
    public Long save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getPostNo());
        if (optionalBoardEntity.isPresent()) {
            Optional<User> optionalUserEntity = userRepository.findById(commentDTO.getUserNo());
            User userEntity = optionalUserEntity.get();
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, userEntity);
            return commentRepository.save(commentEntity).getCmId();
        } else {
            return null;
        }
    }

    //댓글 조회
    @Transactional
    public List<CommentDTO> findAll(Long postNo) {

        BoardEntity boardEntity = boardRepository.findById(postNo).get();

        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByCmIdDesc(boardEntity);

        List<CommentDTO> commentDTOList = new ArrayList<>();

        for(CommentEntity commentEntity : commentEntityList){
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity, postNo));
        }

        return commentDTOList;
    }


    //댓글 삭제
    public void delete(Long cmId) {
        commentRepository.deleteById(cmId);
    }

    public CommentDTO findById(Long cmId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(cmId);
        if (optionalCommentEntity.isPresent()) {
            CommentEntity commentEntity = optionalCommentEntity.get();
            Long postNo = commentEntity.getBoardEntity().getPostNo();
            return CommentDTO.toCommentDTO(commentEntity, postNo);
        } else {
            return null;
        }
    }

}
