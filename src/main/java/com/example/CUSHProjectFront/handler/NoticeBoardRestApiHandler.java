package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.*;
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
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,HashMap.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }

    public void noticeBoardHitUpdate(Long id)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/notice/hitUpdate";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,void.class);
    }

    public NoticeBoardDto noticeBoardContent(Long id)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/notice/getContent";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NoticeBoardDto> responseEntity= restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,NoticeBoardDto.class);
        return responseEntity.getBody();
    }

    public HashMap<String, Object> noticeBoardWrite(NoticeBoardDto noticeBoardDto) throws Exception{
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/notice/write";


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("writer",noticeBoardDto.getWriter())
                .queryParam("title",noticeBoardDto.getTitle())
                .queryParam("content",noticeBoardDto.getContent());

        HttpEntity entity = new HttpEntity(noticeBoardDto,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> noticeBoardModify(NoticeBoardDto noticeBoardDto) throws Exception{
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/notice/modify";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",noticeBoardDto.getId())
                .queryParam("writer",noticeBoardDto.getWriter())
                .queryParam("createdDate",noticeBoardDto.getCreatedDate())
                .queryParam("title",noticeBoardDto.getTitle())
                .queryParam("content",noticeBoardDto.getContent())
                .queryParam("writeIp",noticeBoardDto.getWriteIp());

        HttpEntity entity = new HttpEntity(noticeBoardDto,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> noticeBoardDelete(Long id)  throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/notice/delete";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id",id);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity= restTemplate.exchange(builder.build().toUriString(), HttpMethod.DELETE,entity,HashMap.class);
        return responseEntity.getBody();
    }


}
