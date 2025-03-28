package com.coding404.tracker.controller;

import com.coding404.tracker.entity.VehicleDTO;
import com.coding404.tracker.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    //현재 라이더 정보를 조회하는 인터페이스
    @GetMapping("/getVehicle")
    public ResponseEntity<List<VehicleDTO>> getVehicle() {
        //v1
        //List<VehicleDTO> list = positionRepository.getVehicle();

        //v2
        List<Object[]> result = positionRepository.getVehicle();
        List<VehicleDTO> list = result.stream()
                .map(row -> VehicleDTO
                        .builder()
                        .name( (String) row[0] )
                        .lat( (BigDecimal) row[1] )
                        .lng( (BigDecimal) row[2] )
                        .date( ((Timestamp) row[3]).toLocalDateTime()  )
                        .build()
                )
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getVehicle/{name}")
    public ResponseEntity<List<Object[]>> getVehicleOne(@PathVariable("name") String name) {
        List<Object[]> list = positionRepository.getVehicleOne(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
