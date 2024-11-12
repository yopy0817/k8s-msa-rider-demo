package com.coding404.tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDTO {

    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
    private LocalDateTime date;

    //엔티티로 변경함
    public Vehicle convertEntity() {
        return new Vehicle(name, lat, lng, date);
    }

}
