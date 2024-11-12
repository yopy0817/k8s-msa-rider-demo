package com.coding404.tracker.service;

import com.coding404.tracker.entity.VehicleDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.List;

public interface PositionService {

    void updatePosition(VehicleDTO dto);
    List<VehicleDTO> getVehicle();
}
