package com.akash.genai.repository;

import com.akash.genai.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenAIRepository extends JpaRepository<UserActivity,String> {
}
