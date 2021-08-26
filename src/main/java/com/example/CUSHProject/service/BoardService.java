package com.example.CUSHProject.service;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.repository.BoardQueryRepository;
import com.example.CUSHProject.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final Paging paging;

    //리스트 페이지
    public Page<BoardEntity> boardList(String searchType, String keyword, int curPageNum) {
        Page<BoardEntity> boardEntityPage = null;
        if(searchType.equals("title")) {
            boardEntityPage = boardRepository.findByTitleContaining(keyword, PageRequest.of(curPageNum - 1, paging.getRecordPerPage(), Sort.by(Sort.Direction.ASC, "id")));
        }else if (searchType.equals("content")){
            boardEntityPage = boardRepository.findByContentContaining(keyword, PageRequest.of(curPageNum - 1, paging.getRecordPerPage(), Sort.by(Sort.Direction.ASC, "id")));
        }else if(searchType.equals("username")){
            boardEntityPage = boardRepository.findByUsernameContaining(keyword, PageRequest.of(curPageNum - 1, paging.getRecordPerPage(), Sort.by(Sort.Direction.ASC, "id")));
        }
        return boardEntityPage;
    }
    //검색된 리스트 갯수
    public double getBoardListCnt(String keyword) {
        return boardQueryRepository.findByKeyword(keyword);
    }

    //보드 글 작성 & 상세보기
    public BoardDto boardForm(Long id){
        BoardEntity boardEntity;
        if(id==null){
            boardEntity = new BoardEntity();
        } else{
            boardEntity = boardRepository.findById(id).orElse(null);
        }
        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .username(boardEntity.getUsername())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .date(boardEntity.getDate())
                .hit(boardEntity.getHit())
                .rating(boardEntity.getRating())
                .build();
        return boardDto;
    }


}

