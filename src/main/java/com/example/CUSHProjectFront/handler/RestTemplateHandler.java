package com.example.CUSHProjectFront.handler;

import com.example.CUSHProjectFront.dto.TestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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

    public HashMap<String,Object> OldBoardCount() throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        //HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        String uri = "http://localhost:9090/api/board/getMyOldBoardCnt";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,entity,HashMap.class);

        return responseEntity.getBody();
    }





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

    public String restTemplateTest() throws Exception{
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity entity = new HttpEntity(httpHeaders);
        String uri = "http://localhost:9090/hello";

        String msg1 = "hello";
        int msg2 = 26;

        /*TestDto testDto = TestDto.builder()
                .id(msg2)
                .writer(msg1)
                .build();*/

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("msg1", msg1)
                .queryParam("msg2", msg2);

        System.out.println(builder.toUriString());

        //HttpEntity에 헤더 및 params 설정


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }
}
