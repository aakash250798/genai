package com.akash.genai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenAIService {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<String> askGenAi(String userQuery) {
        String url = "https://apifreellm.com/api/chat";
        Map <String,String> request = new HashMap<>();
        request.put("message",userQuery);
        ResponseEntity<String> response = restTemplate.postForEntity(url,request,String.class);
        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }
}
