package com.coding404.tracker.service;

import com.coding404.tracker.entity.Vehicle;
import com.coding404.tracker.entity.VehicleDTO;
import com.coding404.tracker.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void updatePosition(VehicleDTO dto) {
        //엔티티로 변경하여 insert
        //positionRepository.save(dto.convertEntity());
        //리스트<엔티티> 모형으로 insertAll
        //List<Vehicle> result = list.stream().map( item -> item.convertEntity() ).collect(Collectors.toList());
        //positionRepository.saveAll(result);

        //서버 재시작시 데이터가 지워지므로 data.sql 생성
        positionRepository.updatePosition(dto.convertEntity());

    }

    @Override
    public List<VehicleDTO> getVehicle() {
        return positionRepository.getVehicle();
    }
}
