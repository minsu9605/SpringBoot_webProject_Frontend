package com.example.CUSHProject.service;

import com.example.CUSHProject.dto.BoardCommentDto;
import com.example.CUSHProject.entity.BoardCommentEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.enums.Role;
import com.example.CUSHProject.repository.BoardCommentQueryRepository;
import com.example.CUSHProject.repository.BoardCommentRepository;
import com.example.CUSHProject.repository.BoardRepository;
import com.example.CUSHProject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void commentPost(Long bid, String comment, int cDepth, Long cGroup, String username) throws Exception {

        BoardCommentDto boardCommentDto = new BoardCommentDto();

        boardCommentDto.setBoardId(bid);
        boardCommentDto.setComment(comment);
        boardCommentDto.setCDepth(cDepth);
        boardCommentDto.setCGroup(cGroup);
        boardCommentDto.setCreateDate(LocalDateTime.now());
        boardCommentDto.setUpdateDate(LocalDateTime.now());

        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);
        Optional<BoardEntity> boardEntity = boardRepository.findById(boardCommentDto.getBoardId());

        BoardCommentEntity boardCommentEntity = boardCommentDto.toEntity();

        boardCommentEntity.setCommentBoardId(boardEntity.get());
        boardCommentEntity.setCommentWriter(memberEntity.get());

        boardCommentRepository.save(boardCommentEntity);
    }

    public HashMap<String, Object> getCommentList(Long bid) {
        HashMap<String, Object> map = new HashMap<>();
        Optional<BoardEntity> boardEntity = boardRepository.findById(bid);
        List<BoardCommentEntity> boardCommentEntityList = boardCommentQueryRepository.findByBoardId(boardEntity.get());

        List<BoardCommentDto> boardCommentDtoList = new ArrayList<>(); // 댓글 리스트
        List<Long> ccCountList = new ArrayList<>(); // 대댓글 갯수 카운트

        for (int i = 0; i < boardCommentEntityList.size(); i++) {
            boardCommentDtoList.add(boardCommentEntityList.get(i).toDto()); //댓글 리스트
            ccCountList.add(boardCommentQueryRepository.findReCommentCnt(boardCommentEntityList.get(i).getId())); //대댓글 갯수 카운트
        }
        map.put("list", boardCommentDtoList);
        map.put("commentCnt", ccCountList);

        return map;
    }

    /*댓글 삭제*/
    public Long deleteComment(Long cid, Object roleSession) {
        Optional<BoardCommentEntity> boardCommentEntity = boardCommentRepository.findById(cid);
        int depth = boardCommentEntity.get().getCDepth();
        Long reCommentCnt = boardCommentQueryRepository.deleteCheck(cid);


        if (depth == 0) {
            if (roleSession.equals(Role.ROLE_ADMIN)) {
                /*모댓글의 대댓글 까지 전부 삭제*/
                boardCommentQueryRepository.deleteByCid(cid);
                return 0L;
            }
            if (boardCommentQueryRepository.deleteCheck(cid) == 0) {
                boardCommentRepository.deleteById(cid);
            }
        } else if (depth == 1) {
            boardCommentRepository.deleteById(cid);
        }

        return reCommentCnt;
    }

    public void modifyComment(Long cid, String comment) {
        boardCommentQueryRepository.updateComment(cid, comment);
    }

    /*대댓글 목록*/
    public List<BoardCommentDto> getReCommentList(Long cid) {
        List<BoardCommentEntity> boardCommentEntityList = boardCommentQueryRepository.findByCGroup(cid);
        List<BoardCommentDto> boardCommentDtoList = new ArrayList<>();

        for (int i = 0; i < boardCommentEntityList.size(); i++) {
            boardCommentDtoList.add(boardCommentEntityList.get(i).toDto());
        }
        return boardCommentDtoList;
    }
}
