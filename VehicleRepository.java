package com.numadic.vehicle_tracking.repository;

import com.numadic.vehicle_tracking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByVehicleNumber(String vehicleNumber);
    void deleteByVehicleNumber(String vehicleNumber);
}
