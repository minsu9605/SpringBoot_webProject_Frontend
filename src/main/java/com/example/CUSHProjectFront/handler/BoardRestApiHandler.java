package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.BoardCategoryDto;
import com.example.CUSHProjectFront.dto.BoardDto;
import com.example.CUSHProjectFront.dto.BoardListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BoardRestApiHandler {
    private final ObjectMapper objectMapper;

    public List<BoardCategoryDto> getCategoryList(){
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        String uri = "http://localhost:9090/api/board/list/getCategoryList";
        RestTemplate restTemplate = new RestTemplate();
        List<BoardCategoryDto> responseEntity = restTemplate.getForObject(uri,List.class,entity);

        return responseEntity;

    }

    public HashMap<String, Object> getBoardList(BoardListDto boardListDto) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/list/table";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("categoryId",boardListDto.getCategoryId())
                .queryParam("page",boardListDto.getPage())
                .queryParam("perPage",boardListDto.getPerPage())
                .queryParam("searchType",boardListDto.getSearchType())
                .queryParam("keyword",boardListDto.getKeyword());

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> boardWrite(BoardDto boardDto) throws Exception{
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/write";


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("writer",boardDto.getWriter())
                .queryParam("categoryName",boardDto.getCategoryName())
                .queryParam("rating",boardDto.getRating())
                .queryParam("price",boardDto.getPrice())
                .queryParam("myLat",boardDto.getMyLat())
                .queryParam("myLng",boardDto.getMyLng())
                .queryParam("title",boardDto.getTitle())
                .queryParam("content",boardDto.getContent());

        HttpEntity entity = new HttpEntity(boardDto,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, entity,HashMap.class);

        return responseEntity.getBody();
    }



    public int getMyOldBoardAlertCnt(String username) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/getMyOldBoardCnt";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("username",username);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,Integer.class);

        return responseEntity.getBody();
    }

    public List<BoardDto> getMyOldBoardAlertList(String username, int startIndex, int searchStep)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/getMyOldBoardList";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("username",username)
                .queryParam("startIndex",startIndex)
                .queryParam("searchStep",searchStep);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,List.class);

        return responseEntity.getBody();
    }

    public void boardHitUpdate(Long id)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/hitUpdate";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,void.class);
    }

    public void setSoldOut(Long id) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/setSoldOut";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,void.class);
    }

    public BoardDto boardContent(Long id)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/getBoardContent";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BoardDto> responseEntity= restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,BoardDto.class);
        return responseEntity.getBody();
    }

    public HashMap<String, Object> boardModify(BoardDto boardDto) throws Exception{
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/modify";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",boardDto.getId())
                .queryParam("status",boardDto.getStatus())
                .queryParam("writer",boardDto.getWriter())
                .queryParam("createdDate",boardDto.getCreatedDate())
                .queryParam("categoryName",boardDto.getCategoryName())
                .queryParam("rating",boardDto.getRating())
                .queryParam("price",boardDto.getPrice())
                .queryParam("myLat",boardDto.getMyLat())
                .queryParam("myLng",boardDto.getMyLng())
                .queryParam("title",boardDto.getTitle())
                .queryParam("content",boardDto.getContent());

        HttpEntity entity = new HttpEntity(boardDto,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> boardDelete(Long id)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/board/delete";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity= restTemplate.exchange(builder.build().toUriString(), HttpMethod.DELETE,entity,HashMap.class);
        return responseEntity.getBody();
    }
}
