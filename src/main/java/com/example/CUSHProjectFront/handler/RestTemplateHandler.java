package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.TestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class RestTemplateHandler {

    private final ObjectMapper objectMapper;

    /*public String restTemplateTest() throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<>(httpHeaders);

        String uri = "http://localhost:9090/hello?msg1={msg1}&msg2={msg2}";

        String msg1 = "hello";
        int msg2 = 26;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET ,entity, String.class,msg1,msg2);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }*/

    public String restTemplateTest(TestDto testDto) throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE);
        String uri = "http://localhost:9090/hello";

        //HttpEntity에 헤더 및 params 설정
        HttpEntity httpEntity = new HttpEntity(testDto,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, httpEntity, String.class);

        return responseEntity.getBody();
    }
}
