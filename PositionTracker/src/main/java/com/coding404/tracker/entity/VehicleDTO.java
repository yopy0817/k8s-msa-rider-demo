package com.coding404.tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDTO {

    private String id; //v2
    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
    private LocalDateTime date;

    //엔티티로 변경함
    public Vehicle convertEntity() {
        return new Vehicle(id, name, lat, lng, date);
    }

}
