package com.coding404.gateway.service;

import com.coding404.gateway.command.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


//연결할 주소
@FeignClient(name = "position-tracker" ,url = "${position.tracker.url}")
public interface PositionTrackingApiCall {

    //main에 @EnableFeignClients추가
    
    //@GetMapping, @PostMapping 등: HTTP 메서드 매핑 어노테이션을 사용하여 REST 엔드포인트를 호출합니다.
    //@PathVariable, @RequestParam, @RequestBody 등: Spring MVC와 동일한 어노테이션을 사용하여 매개변수를 전달할 수 있습니다.
    @GetMapping("/getVehicle")
    List<VehicleDTO> getVehicle();

}
