package com.akash.genai.controller;


import com.akash.genai.service.GenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GenAIController {

    @Autowired
    GenAIService genAIService;

    @PostMapping("/genai")
    public ResponseEntity<String> askGenAi(@RequestBody Map<String,String> request){
        return genAIService.askGenAi(request.get("request"));
    }
}
