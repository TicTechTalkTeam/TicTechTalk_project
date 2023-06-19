package com.demo.login.studylogin.service;

import com.demo.login.studylogin.domain.boards.BoardEntity;
import com.demo.login.studylogin.domain.members.User;
import com.demo.login.studylogin.dto.BoardDTO;
import com.demo.login.studylogin.repository.BoardRepository;
import com.demo.login.studylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    //게시글 조회
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        List<BoardDTO> boardDTOList = new ArrayList<>();

        // entity -> DTO
        for(BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    //게시글 상세 보기
    @Transactional
    public BoardDTO findById(Long postNo) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(postNo);
        BoardEntity boardEntity = optionalBoardEntity.get();
        BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);

        return boardDTO;
    }

    //게시글 쓰기
    public Long save(BoardDTO boardDTO) throws IOException {
        Optional<User> optionalUserEntity = userRepository.findById(boardDTO.getUserNo());
        if(boardDTO.getBoardFile() == null){ //파일이 없을 때
            User userEntity = optionalUserEntity.get();
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, userEntity);
            BoardEntity savedEntity = boardRepository.save(boardEntity);

            return savedEntity.getPostNo();
        }else { //첨부 파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만듦
                4. 저장경로에 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터  save처리
             */
            MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); //2. 실제 사용자가 올린 파일 이름
            String storedFilename = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "C:/projectdemo2_img/"+storedFilename; // 4.
            boardFile.transferTo(new File(savePath)); // 5.

            User userEntity = optionalUserEntity.get();
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, storedFilename, userEntity); //6.
            BoardEntity savedEntity = boardRepository.save(boardEntity);

            return savedEntity.getPostNo();
        }
    }

    //게시글 수정
    public BoardDTO update(BoardDTO boardDTO) throws IOException {
        System.out.println("진입했니?");
        System.out.println(boardDTO.getUserNo());
        Optional<User> optionalUserEntity = userRepository.findById(boardDTO.getUserNo());
        log.info("optionalUserEntity: "+optionalUserEntity);
        if(boardDTO.getBoardFile() != null){ //파일 있을 때
            BoardEntity boardEntity = BoardEntity.toUpdateFileEntity(boardDTO); // 6
            boardRepository.save(boardEntity);

            return findById(boardDTO.getPostNo());
        }else {
            User userEntity = optionalUserEntity.get();
            BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO, userEntity);
            boardRepository.save(boardEntity); //update쿼리 수행

            return findById(boardDTO.getPostNo());
        }

    }

    //게시글 삭제
    public void delete(Long postNo) {
        boardRepository.deleteById(postNo);
    }

    //게시글 조회수
    @Transactional
    public void updateViews(Long postNo) {
        boardRepository.updateViews(postNo);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() -1 ;
        int pageLimit = 5; // 한 페이지에 보여줄 글 갯수
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"postNo")));

        Page<BoardDTO> boardDTOS = boardEntities.map(board-> new BoardDTO(board.getPostNo(), board.getTitle(), board.getViews(), board.getPostDate()));

        return boardDTOS;
    }


    //검색 기능
    public Page<BoardDTO> forumSearch(String searchKeyword, Pageable pageable) {
        log.info("진입 확인");
        int page = pageable.getPageNumber() -1 ;
        int pageLimit = 5; // 한 페이지에 보여줄 글 갯수
        Page<BoardEntity> boardEntities = boardRepository.findByTitleContaining(searchKeyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"postNo")));

        log.info("잘 뽑아냈나?");

        Page<BoardDTO> boardDTOS = boardEntities.map(board-> new BoardDTO(board.getPostNo(), board.getTitle(), board.getViews(), board.getPostDate()));

        return boardDTOS;
    }

    public BoardDTO findByIdAndUserEntity(Long postNo, User userEntity) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findByPostNoAndUserEntity(postNo, userEntity);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else {
            return null;
        }
    }
}
