package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
