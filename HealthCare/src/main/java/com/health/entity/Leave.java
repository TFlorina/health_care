package com.health.entity;

import com.health.enums.LeaveType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "leave_request")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(value = EnumType.STRING)
    private LeaveType type;
    private Short noOfDays;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;
}
