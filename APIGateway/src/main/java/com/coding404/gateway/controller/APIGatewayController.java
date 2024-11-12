package com.coding404.gateway.controller;

import com.coding404.gateway.command.VehicleDTO;
import com.coding404.gateway.service.PositionTrackingApiCall;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
@CrossOrigin({"http://localhost:3000"})
public class APIGatewayController {

    //MSA에서 게이트웨이 역할로 외부로 부터 진입하는 진입점이 됩니다.
    //서비스 생략
    @Autowired
    private PositionTrackingApiCall positionTrackingApiCall;

    //APIGateway 테스트 URL
    @GetMapping("/test")
    public String apiTest() {
        return "<p>Hello world API Gateway</p>";
    }

    @GetMapping("/getVehicle")
    public ResponseEntity<List<VehicleDTO>> getVehicle() {
        try {
            List<VehicleDTO> list = positionTrackingApiCall.getVehicle();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
