package com.example.CUSHProject.service;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.BoardCategoryDto;
import com.example.CUSHProject.dto.BoardCommentDto;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardCommentEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.repository.*;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final Paging paging;

    /*게시판 리스트 페이지*/
    public Page<BoardEntity> boardList(String searchType, String keyword, int curPageNum, int notice) {
        Page<BoardEntity> boardEntityPage = null;
        Pageable pageable = PageRequest.of(curPageNum - 1, paging.getRecordPerPage(), Sort.by(Sort.Direction.DESC, "id"));
        if(notice==0){
            if(searchType.equals("title")) {
                boardEntityPage = boardRepository.findByNoticeAndTitleContaining(notice,keyword, pageable);
            }else if (searchType.equals("content")){
                boardEntityPage = boardRepository.findByNoticeAndContentContaining(notice,keyword, pageable);
            }else if(searchType.equals("writer")){
                boardEntityPage = boardRepository.findByNoticeAndWriterContaining(notice,keyword, pageable);
            }
        }else if(notice == 1){
            if(searchType.equals("title")) {
                boardEntityPage = boardRepository.findByNoticeAndTitleContaining(notice,keyword, pageable);
            }else if (searchType.equals("content")){
                boardEntityPage = boardRepository.findByNoticeAndContentContaining(notice,keyword, pageable);
            }
        }
        return boardEntityPage;
    }


    /*게시판 리스트 수 */
    public double getBoardListCnt(String keyword, int notice) {
        return boardQueryRepository.findByKeyword(keyword, notice);
    }

    public List<BoardDto> getBoardList(String searchType, String keyword, int notice, Long categoryId){
        BoardCategoryEntity boardCategoryEntity = boardCategoryRepository.findById(categoryId).orElse(null);
        List<BoardEntity> boardEntityList = null;
        List<BoardDto> boardDtoList = new ArrayList<>();
        if(notice == 0){
            if(searchType.equals("title")) {
                boardEntityList = boardRepository.findByNoticeAndCategory(notice,boardCategoryEntity);
//                boardEntityList = boardRepository.findByNoticeAndCategoryAndTitleContaining(notice,category,keyword);
            }else if (searchType.equals("content")){
                boardEntityList = boardRepository.findByNoticeAndCategoryAndContentContaining(notice,boardCategoryEntity,keyword);
            }else if(searchType.equals("writer")){
                boardEntityList = boardRepository.findByNoticeAndCategoryAndWriterContaining(notice,boardCategoryEntity,keyword);
            }
        } else if(notice == 1){
            if(searchType.equals("title")) {
                boardEntityList = boardRepository.findByNoticeAndCategoryAndTitleContaining(notice,boardCategoryEntity,keyword);
            }else if (searchType.equals("content")){
                boardEntityList = boardRepository.findByNoticeAndCategoryAndContentContaining(notice,boardCategoryEntity,keyword);
            }
        }
        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(boardEntity.toDto());
        }

        return boardDtoList;
    }

    //보드 글 상세보기
    public BoardDto boardContent(Long id) {
        BoardEntity boardEntity;
        if (id == null) {
            boardEntity = new BoardEntity();
        } else {
            boardEntity = boardRepository.findById(id).orElse(null);
        }
        return boardEntity.toDto();
    }

    /*조회수 증가*/
    public void boardHitUpdate(Long id){
        boardQueryRepository.boardHitUpdate(id);
    }

    /*게시글 등록 후 전송*/
    public BoardDto boardWrite(BoardDto boardDto, String username){
        Optional<BoardCategoryEntity> boardCategoryEntity = boardCategoryRepository.findByName(boardDto.getCategoryName());
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        boardDto.setCreatedDate(LocalDateTime.now().format(formatter));
        boardDto.setUpdatedDate(LocalDateTime.now().format(formatter));
        BoardEntity boardEntity = boardDto.toEntity();

        boardEntity.setCategory(boardCategoryEntity.get());
        boardEntity.setWriter(memberEntity.get());

        return boardRepository.save(boardEntity).toDto();
    }

    /*보드 수정 후 전송*/
    public BoardDto boardModifySave(BoardDto boardDto, String username){
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardDto.getId());
        Optional<BoardCategoryEntity> boardCategoryEntity = boardCategoryRepository.findById(boardEntityOptional.get().getCategory().getId());
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        boardDto.setUpdatedDate(LocalDateTime.now().format(formatter));

        BoardEntity boardEntity = boardDto.toEntity();

        boardEntity.setCategory(boardCategoryEntity.get());
        boardEntity.setWriter(memberEntity.get());

        return boardRepository.save(boardEntity).toDto();
    }

    /*게시글 삭제*/
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    /*summernote 이미지 첨부*/
    public JsonObject boardImageUpload(MultipartFile multipartFile){
        JsonObject jsonObject = new JsonObject();

        String fileRoot = "D:\\summernote_image\\";

        String originalFileName = multipartFile.getOriginalFilename(); // 오리지널 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일확장자
        String savedFileName = UUID.randomUUID() + extension; //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }
        return jsonObject;
    }

}

