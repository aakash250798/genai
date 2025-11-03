package com.akash.genai.service;

import com.akash.genai.entity.UserActivity;
import com.akash.genai.repository.GenAIRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GenAIService {

    Logger logger = LoggerFactory.getLogger(GenAIService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GenAIRepository genAIRepository;

    @Transactional
    public ResponseEntity<String> askGenAiModel1(String userQuery) {
        String url = "https://apifreellm.com/api/chat";
        Map<String, String> request = new HashMap<>();
//        ResponseEntity<String> response = new ResponseEntity<>("Timeout", HttpStatus.GATEWAY_TIMEOUT);
        request.put("message", userQuery);
        String resp = null;
        String status = "OK";
        try {
            //response = restTemplate.postForEntity(url, request, String.class);

            WebClient webClient = WebClient.create(url);
            resp = webClient.post()
                    // .uri("/users")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            status = "GATEWAY_TIMEOUT";
            logger.error(" -- Exception occured while calling the url", e);
        }
        UserActivity userActivity = new UserActivity();
        userActivity.setId(UUID.randomUUID().toString());
        userActivity.setRequest(userQuery);
        userActivity.setStatus(status);
        userActivity.setResponse(resp);
        try {
            genAIRepository.save(userActivity);
        } catch (Exception e) {
            logger.error(" -- Exception occured while saving entity", e);
        }
        return new ResponseEntity<>(resp, HttpStatus.valueOf(status));
    }

    @Transactional
    public ResponseEntity<String> askGenAiModel2(String userQuery) {
        String url = "https://text.pollinations.ai/{query}";
        Map<String, String> request = new HashMap<>();
//        ResponseEntity<String> response = new ResponseEntity<>("Timeout", HttpStatus.GATEWAY_TIMEOUT);
        request.put("message", userQuery);
        String resp = null;
        String status = "OK";
        try {
            WebClient webClient = WebClient.create(url);
            long start = System.currentTimeMillis();
            resp = webClient.get()
                    .uri(url, userQuery)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            long end = System.currentTimeMillis();



        UserActivity userActivity = getUserActivityEntity();
        userActivity.setRequest(userQuery);
        userActivity.setStatus(status);
        userActivity.setResponse(resp);
        userActivity.setTimeConsumedInMillis(end-start);

        genAIRepository.save(userActivity);

        } catch (Exception e) {
            logger.error(" -- Exception occured ", e);
        }
        return new ResponseEntity<>(resp, HttpStatus.valueOf(status));
    }

    public UserActivity getUserActivityEntity(){
        UserActivity userActivity = new UserActivity();
        userActivity.setId(UUID.randomUUID().toString());
        userActivity.setLocalDateTime(LocalDateTime.now());
        try {
            userActivity.setHost(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            logger.error(" -- UnknownHost", e);
        }
        return userActivity;

    }


}
