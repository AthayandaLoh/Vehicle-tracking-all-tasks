package com.numadic.vehicle_tracking.controller;

import com.numadic.vehicle_tracking.model.Vehicle;
import com.numadic.vehicle_tracking.service.VehicleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080") 
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.registerVehicle(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping("/track/{vehicleNumber}")
    public ResponseEntity<Optional<Vehicle>> trackVehicle(@PathVariable String vehicleNumber) {
        return ResponseEntity.ok(vehicleService.trackVehicle(vehicleNumber));
    }

    @PutMapping("/update/{vehicleNumber}")
    public ResponseEntity<Vehicle> updateVehicleLocation(
            @PathVariable String vehicleNumber, @RequestParam String newLocation) {
        Vehicle updatedVehicle = vehicleService.updateVehicleLocation(vehicleNumber, newLocation);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/delete/{vehicleNumber}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String vehicleNumber) {
        vehicleService.deleteVehicle(vehicleNumber);
        return ResponseEntity.ok("✅ Vehicle deleted successfully");
    }
}
