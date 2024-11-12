package com.coding404.tracker.repository;

import com.coding404.tracker.entity.Vehicle;
import com.coding404.tracker.entity.VehicleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PositionRepository extends JpaRepository<Vehicle, String> {

    @Transactional
    @Modifying
    @Query("update Vehicle v set v.lat = :#{#x.lat }, v.lng = :#{#x.lng}, v.date = :#{#x.date} where v.name = :#{#x.name}")
    void updatePosition(@Param("x") Vehicle entity);

    //DTO로 반환받기
    @Query("select new com.coding404.tracker.entity.VehicleDTO" +
            "(v.name, v.lat, v.lng, v.date) " +
            "from Vehicle v")
    List<VehicleDTO> getVehicle();

}
