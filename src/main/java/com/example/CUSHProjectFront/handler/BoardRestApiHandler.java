package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.BoardCategoryDto;
import com.example.CUSHProjectFront.dto.BoardListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
//        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        String uri = "http://localhost:9090/api/board/list/getCategoryList";
        RestTemplate restTemplate = new RestTemplate();
        List<BoardCategoryDto> responseEntity = restTemplate.getForObject(uri,List.class,entity);

        System.out.println(responseEntity);
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
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity,HashMap.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }

}
