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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
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

            resp = webClient.get()
                    .uri(url, userQuery)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            status = "GATEWAY_TIMEOUT";
            logger.error(" -- Exception occured while calling the url", e);
        }
        UserActivity userActivity = new UserActivity();
        userActivity.setId(UUID.randomUUID().toString());
        userActivity.setLocalDateTime(LocalDateTime.now());
        // need corrections for getting right IP address and not localhost
        try {
            userActivity.setHost(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            logger.error(" -- UnknownHost", e);
        }
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
}
