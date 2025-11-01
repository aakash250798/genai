package com.akash.genai.service;

import com.akash.genai.entity.UserActivity;
import com.akash.genai.repository.GenAIRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
public class GenAIService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GenAIRepository genAIRepository;

    @Transactional
    public ResponseEntity<String> askGenAi(String userQuery) {
        String url = "https://apifreellm.com/api/chat";
        Map <String,String> request = new HashMap<>();
        request.put("message",userQuery);
        ResponseEntity<String> response = restTemplate.postForEntity(url,request,String.class);
        UserActivity userActivity = new UserActivity();
        userActivity.setId(UUID.randomUUID().toString());
        userActivity.setRequest(userQuery);
        userActivity.setStatus(response.getStatusCode().toString());
        userActivity.setResponse(response.getBody());
        genAIRepository.save(userActivity);
        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }
}
