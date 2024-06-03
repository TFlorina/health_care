package com.health.repository;

import com.health.entity.Booking;
import com.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByClient(User client);
    List<Booking> findByCoach(User coach);
}
