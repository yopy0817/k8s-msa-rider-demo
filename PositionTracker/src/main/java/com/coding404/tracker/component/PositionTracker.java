package com.coding404.tracker.component;

import com.coding404.tracker.entity.VehicleDTO;
import com.coding404.tracker.service.PositionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class PositionTracker {

    private List<VehicleDTO> list = new ArrayList<>();

    @Autowired
    private PositionService positionService;

    //레빗큐의 변화가 있다면 리스너가 동작합니다.
    //객체역질렬화에 RabbitMQConfig 설정 필요
    @RabbitListener(queues = "${position.queue}")
    public void receiveMessage(Map<String, String> map) {
        log.info(map.toString());
        
        //레빗큐에서 넘어온 데이터 DTO로 받음
        VehicleDTO dto = VehicleDTO.builder()
                .name( map.get("vehicle"))
                .lat( new BigDecimal(map.get("lat") ))
                .lng( new BigDecimal(map.get("lng") ))
                .date(LocalDateTime.parse(map.get("date") ))
                .build();

        positionService.updatePosition(dto);
    }
}
