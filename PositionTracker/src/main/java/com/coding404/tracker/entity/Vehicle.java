package com.coding404.tracker.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "VEHICLE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
//    @Id
//    private String id;
//    @Column(columnDefinition = "varchar(20) not null")
    @Id
    private String name;
    @Column(columnDefinition = "decimal(15, 12)") //BigDecimal와 대응됨(전체자리수, 소수점자리수)
    private BigDecimal lat;
    @Column(columnDefinition = "decimal(15, 12)")
    private BigDecimal lng;
    @Column(columnDefinition = "timestamp")
    private LocalDateTime date;

}
