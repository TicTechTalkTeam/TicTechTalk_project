package com.demo.login.studylogin.service;

import com.demo.login.studylogin.Utils.JwtTokenUtil;
import com.demo.login.studylogin.domain.boards.BoardEntity;
import com.demo.login.studylogin.domain.boards.Bookmark;
import com.demo.login.studylogin.domain.members.User;
import com.demo.login.studylogin.dto.BoardDTO;
import com.demo.login.studylogin.dto.BookmarkDto;
import com.demo.login.studylogin.dto.MyPageRequestDto;
import com.demo.login.studylogin.repository.BoardRepository;
import com.demo.login.studylogin.repository.BookmarkRepository;
import com.demo.login.studylogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;
    private final JwtTokenUtil jwtTokenUtil;

    //myPage 업데이트
    @Transactional
    public ResponseEntity<?> editMyPage(MyPageRequestDto myPageRequestDto) {
        User user = jwtTokenUtil.getUserFromAuthentication();
        Optional<User> correctUser = userRepository.findByUserEmail(user.getUserEmail());

        if(!correctUser.isPresent()) {
            ResponseEntity.ok("USER_NOT_FOUND");
        }
        user.toUpdateUser(myPageRequestDto);
        User saveUser = userRepository.save(user);
        return ResponseEntity.ok(saveUser);
    }

    //북마크 추가
    @Transactional
    public ResponseEntity<?> addBookmark(Map<String, Long> postNo) {
        User user = jwtTokenUtil.getUserFromAuthentication();
        Long findPostNo = postNo.get("postNo");
        BoardEntity boardEntity = boardRepository.findById(findPostNo).get();
        boolean bookmarkCheck = bookmarkRepository.existsByUserNoAndPostNo(user.getUserNo(), findPostNo);

        if(!bookmarkCheck) {
            Bookmark bookmark = Bookmark.builder()
                    .userNo(user.getUserNo())
                    .postNo(boardEntity.getPostNo())
                    .build();

            bookmarkRepository.save(bookmark);
            return ResponseEntity.ok(bookmark);
        }
        Long cancelPostNo = boardEntity.getPostNo();
        Bookmark bookmarkCancel = bookmarkRepository.findByPostNo(cancelPostNo);
        bookmarkRepository.delete(bookmarkCancel);
        return ResponseEntity.ok("북마크_취소");
    }

    //북마크 리스트
    @Transactional
    public ResponseEntity<?> bookmark() {
        User user = jwtTokenUtil.getUserFromAuthentication();
        List<Bookmark> bookmark = bookmarkRepository.findAllByUserNo(user.getUserNo());
        List<BookmarkDto> bookmarkDtoList = new ArrayList<>();

        for(Bookmark bookmarks : bookmark) {
            Long postNo = bookmarks.getPostNo();
            BoardEntity boardEntity = boardRepository.findById(postNo).get();

            BookmarkDto bookmarkDto = BookmarkDto.builder()
                    .bookmarkId(bookmarks.getBookmarkId())
                    .userNo(user.getUserNo())
                    .postNo(boardEntity.getPostNo())
                    .title(boardEntity.getTitle())
                    .build();

            bookmarkDtoList.add(bookmarkDto);
        }
        return ResponseEntity.ok(bookmarkDtoList);
    }
    //내 글보기
    @Transactional
    public ResponseEntity<?> myPost() {
        User user = jwtTokenUtil.getUserFromAuthentication();
        Long userNo = user.getUserNo();
        List<BoardEntity> boardEntityList = boardRepository.findByUserEntityUserNo(userNo);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .userNo(boardEntity.getUserEntity().getUserNo())
                    .postNo(boardEntity.getPostNo())
                    .title(boardEntity.getTitle())
                    .build();
            boardDTOList.add(boardDTO);
            }
        return ResponseEntity.ok(boardDTOList);

    }

}
