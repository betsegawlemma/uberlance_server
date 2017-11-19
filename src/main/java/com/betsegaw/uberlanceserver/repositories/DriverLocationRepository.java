package com.betsegaw.uberlanceserver.repositories;

import com.betsegaw.uberlanceserver.models.DriverLocation;
import org.springframework.data.repository.CrudRepository;

public interface DriverLocationRepository extends CrudRepository<DriverLocation,String> {
    DriverLocation findByCarPlateNumber(String carPlateNumber);
    void deleteDriverLocationByCarPlateNumber(String carPlateNumber);
    //void deleteByCarPlateNumber(String carPlateNumber);
}
