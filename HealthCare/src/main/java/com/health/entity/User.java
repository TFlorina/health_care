package com.health.entity;

import com.health.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> coachList;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> clientList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToOne(mappedBy = "coach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CoachDescription coachDescription;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Leave> leaveRequests;

    public void addScheduleToCoach(Schedule schedule) {
        this.scheduleList.add(schedule);
        schedule.setUser(this);
    }
}
