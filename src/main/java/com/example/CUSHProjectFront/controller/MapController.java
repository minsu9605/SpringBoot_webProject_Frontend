package com.example.CUSHProjectFront.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MapController {

    /*private final BoardService boardService;*/
    @GetMapping("/map")
    public String map(){
        return "mapMenu/mapMenu";
    }

   /* @GetMapping("/api/mapMenu/list")
    @ResponseBody
    public HashMap<String, Object> markerList(@RequestParam double startLat,
                                              @RequestParam double startLng,
                                              @RequestParam double endLat,
                                              @RequestParam double endLng){

        HashMap<String, Object> map = new HashMap<>();
        List<BoardDto> boardDtoList = boardService.mapList(startLat,startLng,endLat,endLng);
        if(boardDtoList.size()!=0) {
            map.put("data",boardDtoList);
        }else{
            map.put("empty",1);
        }
        return map;
    }*/

}
