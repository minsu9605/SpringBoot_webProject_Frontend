package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.BoardCategoryDto;
import com.example.CUSHProjectFront.dto.BoardDto;
import com.example.CUSHProjectFront.dto.BoardListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentRestApiHandler {
    private final ObjectMapper objectMapper;

    public HashMap<String, Object> getCommentList(Long bid) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/comment/list";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("bid",bid);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> commentPost(Long bid, String comment, String username) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/comment/post";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("bid",bid)
                .queryParam("comment",comment)
                .queryParam("username",username);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST,entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> getReCommentList(Long cid) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/comment/reComment/list";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("cid",cid);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,HashMap.class);

        return responseEntity.getBody();
    }
    public HashMap<String, Object> reCommentPost(Long bid, Long cid, String comment, String username) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/comment/reComment/post";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("bid",bid)
                .queryParam("cid",cid)
                .queryParam("comment",comment)
                .queryParam("username",username);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST,entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> deleteComment(Long cid, Object roleSession) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/comment/delete";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("cid",cid)
                .queryParam("roleSession",roleSession);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.DELETE,entity,HashMap.class);

        return responseEntity.getBody();
    }

    public HashMap<String, Object> modifyComment(Long cid, String comment) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        String uri = "http://localhost:9090/api/comment/modify";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("cid",cid)
                .queryParam("comment",comment);

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET,entity,HashMap.class);

        return responseEntity.getBody();
    }
}
