package com.example.demo.infrastructure.repository;

import com.example.demo.domain.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
