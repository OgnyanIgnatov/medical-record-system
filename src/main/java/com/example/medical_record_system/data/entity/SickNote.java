package com.example.medical_record_system.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SickNote {

    @DateTimeFormat
    private Date issuedDate;

    private long daysCount;

    @OneToOne
    private Visit visit;
}
