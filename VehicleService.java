package com.numadic.vehicle_tracking.service;

import com.numadic.vehicle_tracking.model.Vehicle;
import com.numadic.vehicle_tracking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle registerVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> trackVehicle(String vehicleNumber) {
        return Optional.ofNullable(vehicleRepository.findByVehicleNumber(vehicleNumber));
    }

    public Vehicle updateVehicleLocation(String vehicleNumber, String newLocation) {
        Vehicle vehicle = vehicleRepository.findByVehicleNumber(vehicleNumber);
        if (vehicle == null) {
            throw new RuntimeException("❌ Vehicle not found!");
        }
        vehicle.setLocation(newLocation);
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(String vehicleNumber) {
        vehicleRepository.deleteByVehicleNumber(vehicleNumber);
    }
}
