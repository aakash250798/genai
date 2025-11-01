package com.akash.genai.service;

import com.akash.genai.entity.UserActivity;
import com.akash.genai.repository.GenAIRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
public class GenAIService {
    
    
    Logger logger = LoggerFactory.getLogger(GenAIService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GenAIRepository genAIRepository;

    @Transactional
    public ResponseEntity<String> askGenAi(String userQuery) {
        String url = "https://apifreellm.com/api/chat";
        Map <String,String> request = new HashMap<>();
        ResponseEntity<String> response = new ResponseEntity<>("Timeout", HttpStatus.GATEWAY_TIMEOUT);
                request.put("message",userQuery);
        try {
            response = restTemplate.postForEntity(url, request, String.class);
        }
        catch (Exception e){
            logger.error(" -- Exception occured while calling the url",e);
        }
        UserActivity userActivity = new UserActivity();
        userActivity.setId(UUID.randomUUID().toString());
        userActivity.setRequest(userQuery);
        userActivity.setStatus(response.getStatusCode().toString());
        userActivity.setResponse(response.getBody());
        try {
            genAIRepository.save(userActivity);
        }
        catch (Exception e){
            logger.error(" -- Exception occured while saving entity", e);
        }
        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }
}
