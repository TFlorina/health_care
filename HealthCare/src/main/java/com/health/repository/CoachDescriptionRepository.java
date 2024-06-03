package com.health.repository;

import com.health.entity.CoachDescription;
import com.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachDescriptionRepository extends JpaRepository<CoachDescription, Long> {
}
