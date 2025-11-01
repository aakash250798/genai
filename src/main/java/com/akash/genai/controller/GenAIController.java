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
        return genAIService.askGenAiModel2(request.get("request"));
    }

    @PostMapping("/image")
    public ResponseEntity<String> generateImage(@RequestBody Map<String, String> request){
        return genAIService.generateImage(request.get("request"));
    }
}
