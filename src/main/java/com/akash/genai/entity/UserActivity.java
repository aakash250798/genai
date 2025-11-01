package com.akash.genai.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;

import java.net.*;


@Entity
public class UserActivity {

    @Id
    private String id;

    private String request;

    private LocalDateTime localDateTime;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    private String host;

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host=host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String status;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String response;


}
