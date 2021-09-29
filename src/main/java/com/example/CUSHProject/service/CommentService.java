package com.example.CUSHProject.service;

import com.example.CUSHProject.dto.BoardCommentDto;
import com.example.CUSHProject.entity.BoardCommentEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardCommentQueryRepository boardCommentQueryRepository;


    /*댓글 등록*/
    public void commentPost(BoardCommentDto boardCommentDto, String username) throws Exception {

        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);
        Optional<BoardEntity> boardEntity = boardRepository.findById(boardCommentDto.getBoardId());

        boardCommentDto.setCreateDate(LocalDateTime.now());
        boardCommentDto.setUpdateDate(LocalDateTime.now());

        BoardCommentEntity boardCommentEntity = boardCommentDto.toEntity();


        boardCommentEntity.setCommentBoardId(boardEntity.get());
        boardCommentEntity.setCommentWriter(memberEntity.get());

        boardCommentRepository.save(boardCommentEntity);
    }

    /*댓글 리스트*/
    public List<BoardCommentDto> getCommentList(Long bid) throws Exception {
        Optional<BoardEntity> boardEntity = boardRepository.findById(bid);
        //List<BoardCommentEntity> boardCommentEntityList = boardCommentRepository.findByBoardId(boardEntity.get());
        List<BoardCommentEntity> boardCommentEntityList = boardCommentQueryRepository.findByBoardId(boardEntity.get());
        List<BoardCommentDto> boardCommentDtoList = new ArrayList<>();

        for(int i = 0; i < boardCommentEntityList.size(); i++) {
            boardCommentDtoList.add(boardCommentEntityList.get(i).toDto());
        }
        return boardCommentDtoList;
    }

    /*댓글 삭제*/
    public Long deleteComment(Long cid) {
        Optional<BoardCommentEntity> boardCommentEntity = boardCommentRepository.findById(cid);
        int depth = boardCommentEntity.get().getCDepth();
        Long reCommentCnt = boardCommentQueryRepository.deleteCheck(cid);

        /*if (boardCommentEntity.get().getWriter().getRole().getValue().equals("ROLE_ADMIN")) {
            boardCommentRepository.deleteById(cid);
        } else*/
        if (depth == 0) {
            if (boardCommentQueryRepository.deleteCheck(cid) == 0) {
                boardCommentRepository.deleteById(cid);
            }
        } else if (depth == 1) {
            boardCommentRepository.deleteById(cid);
        }

        return reCommentCnt;
    }

    public void modifyComment(Long cid, String comment){
        boardCommentQueryRepository.updateComment(cid,comment);
    }

    /*대댓글 목록*/
    public List<BoardCommentDto> getReCommentList(Long cid) {
        List<BoardCommentEntity> boardCommentEntityList = boardCommentQueryRepository.findByCGroup(cid);
        List<BoardCommentDto> boardCommentDtoList = new ArrayList<>();

        for(int i = 0; i < boardCommentEntityList.size(); i++) {
            boardCommentDtoList.add(boardCommentEntityList.get(i).toDto());
        }
        return boardCommentDtoList;
    }
}
