package com.betsegaw.uberlanceserver.services;

import com.betsegaw.uberlanceserver.models.DriverLocation;
import com.betsegaw.uberlanceserver.repositories.DriverLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverLocationService {

    @Autowired
    DriverLocationRepository driverLocationRepository;

  public Iterable<DriverLocation>  findAll(){
       return driverLocationRepository.findAll();
   }
  public DriverLocation findByCarPlateNumber(String carPlateNumber){
       return driverLocationRepository.findByCarPlateNumber(carPlateNumber);
   }
  public void create(DriverLocation driverLocation){
       driverLocationRepository.save(driverLocation);
   }
  public void edit(DriverLocation driverLocation){
       if(isDriverLocationExist(driverLocation)){
           driverLocationRepository.save(driverLocation);
       }

   }

   public void delete(DriverLocation driverLocation){
      if(isDriverLocationExist(driverLocation)){
          driverLocationRepository.deleteDriverLocationByCarPlateNumber(driverLocation.getCarPlateNumber());
      }

  }
   public boolean isDriverLocationExist(DriverLocation driverLocation){
      if(driverLocationRepository.exists(driverLocation.getCarPlateNumber())){
          return true;
      }
      return false;
   }


}
