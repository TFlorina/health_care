package com.health.repository;

import com.health.entity.Leave;
import com.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByCoach(User coach);
}
