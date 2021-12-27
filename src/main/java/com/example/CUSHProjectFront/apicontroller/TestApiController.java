package com.example.CUSHProjectFront.apicontroller;

import com.example.CUSHProjectFront.dto.TestDto;
import com.example.CUSHProjectFront.handler.RestTemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class TestApiController {
    private final RestTemplateHandler restTemplateHandler;

    @PostMapping("/api/test/hello")
    public HashMap<String,Object> testApi(TestDto testDto) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        map.put("result", restTemplateHandler.restTemplateTest(testDto));
        return map;
    }
}
