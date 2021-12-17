package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.BoardCategoryDto;
import com.example.CUSHProjectFront.dto.BoardListDto;
import com.example.CUSHProjectFront.dto.NoticeBoardListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class NoticeBoardRestApiHandler {
    private final ObjectMapper objectMapper;

    public HashMap<String, Object> getNoticeBoardList(NoticeBoardListDto noticeBoardListDto) throws Exception{

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/notice/list/table";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("page",noticeBoardListDto.getPage())
                .queryParam("perPage",noticeBoardListDto.getPerPage())
                .queryParam("searchType",noticeBoardListDto.getSearchType())
                .queryParam("keyword",noticeBoardListDto.getKeyword());

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity,HashMap.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }

}
