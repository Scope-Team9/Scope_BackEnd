package com.studycollaboproject.scope.webSocket;

import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findAllByReceivedUserOrderByCreatedAtDesc(User user);
    List<Alert> findAllByReceivedUserAndAlertCheckedFalseOrderByCreatedAtDesc(User user);
    List<Alert> findAllByReceivedUserAndAlertCheckedFalse(User user);
}
