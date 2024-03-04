package com.sap.milestone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="swipe_event")
@Data
@Getter
@Setter
public class SwipeEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private LocalDateTime officeIn;
    private LocalDateTime officeOut;
    private Long stayInOffice;
    private String availablityInOffice;

}
