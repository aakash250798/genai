package com.akash.genai.entity;

import jakarta.persistence.*;

@Entity
public class UserActivity {

    @Id
    private String id;

    private String request;

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
