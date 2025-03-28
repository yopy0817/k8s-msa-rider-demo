package com.coding404.tracker.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "VEHICLE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
//    v1
//    @Id
//    private String name;
//    @Column(columnDefinition = "decimal(15, 12)") //BigDecimal와 대응됨(전체자리수, 소수점자리수)
//    private BigDecimal lat;
//    @Column(columnDefinition = "decimal(15, 12)")
//    private BigDecimal lng;
//    @Column(columnDefinition = "timestamp")
//    private LocalDateTime date;

    @Id
    @GeneratedValue(generator = "uuid") //기본 키 값(Primary Key)을 자동으로 생성하도록 설정. (아래에 uuid라는 생성기 이용)
    @GenericGenerator(name = "uuid", strategy = "uuid2") //uuid2는 랜덤한 UUID 값을 자동 생성하는 전략입니다
    @Column(columnDefinition = "varchar(50)")
    private String id;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String name;
    @Column(columnDefinition = "decimal(15, 12)") //BigDecimal와 대응됨(전체자리수, 소수점자리수)
    private BigDecimal lat;
    @Column(columnDefinition = "decimal(15, 12)")
    private BigDecimal lng;
    @Column(columnDefinition = "timestamp")
    private LocalDateTime date;

}
