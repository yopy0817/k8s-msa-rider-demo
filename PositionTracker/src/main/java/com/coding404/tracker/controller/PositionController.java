package com.coding404.tracker.controller;

import com.coding404.tracker.entity.VehicleDTO;
import com.coding404.tracker.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;

    //현재 라이더 정보를 조회하는 인터페이스
    @GetMapping("/getVehicle")
    public ResponseEntity<List<VehicleDTO>> getVehicle() {
        List<VehicleDTO> list = positionRepository.getVehicle();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
