package com.betsegaw.uberlanceserver.controllers;

import com.betsegaw.uberlanceserver.models.DriverLocation;
import com.betsegaw.uberlanceserver.services.DriverLocationService;
import com.betsegaw.uberlanceserver.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class DriverLocationController{

    @Autowired
    DriverLocationService driverLocationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DriverLocation>> findAll() {
        List<DriverLocation> driverLocationList = new ArrayList<>();

        for(DriverLocation driverLocation: driverLocationService.findAll()){
            driverLocationList.add(driverLocation);
        }
        return new ResponseEntity<List<DriverLocation>>(driverLocationList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{carPlateNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> findByCarPlateNumber(@PathVariable("carPlateNumber") String carPlateNumber) {

        DriverLocation driverLocation = driverLocationService.findByCarPlateNumber(carPlateNumber);
        if (driverLocation == null) {
            return new ResponseEntity(new CustomErrorType("DriverLocation with car plate number " + carPlateNumber
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DriverLocation>(driverLocation, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody DriverLocation driverLocation, UriComponentsBuilder ucBuilder) {

        if (driverLocationService.isDriverLocationExist(driverLocation)) {

            return new ResponseEntity(new CustomErrorType("Unable to create. A driver location with car plate number " +
                    driverLocation.getCarPlateNumber() + " already exist."),HttpStatus.CONFLICT);
        }
        driverLocationService.create(driverLocation);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/locations/{carPlateNumber}").buildAndExpand(driverLocation.getCarPlateNumber()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{carPlateNumber}", method = RequestMethod.PUT)
    public ResponseEntity<?> edit(@PathVariable("carPlateNumber") String carPlateNumber, @RequestBody DriverLocation driverLocation) {

        DriverLocation currentDriverLocation = driverLocationService.findByCarPlateNumber(carPlateNumber);

        if (currentDriverLocation == null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Driver location with plate number " + carPlateNumber + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentDriverLocation.setDriverName(driverLocation.getDriverName());
        currentDriverLocation.setDriverPhoneNumber(driverLocation.getDriverPhoneNumber());
        currentDriverLocation.setLatitude(driverLocation.getLatitude());
        currentDriverLocation.setLongitude(driverLocation.getLongitude());

        driverLocationService.edit(currentDriverLocation);
        return new ResponseEntity<DriverLocation>(currentDriverLocation, HttpStatus.OK);
    }

    @RequestMapping(value = "/{carPlateNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("carPlateNumber") String carPlateNumber, @RequestBody DriverLocation driverLocation) {

        DriverLocation currentDriverLocation = driverLocationService.findByCarPlateNumber(carPlateNumber);

        if (currentDriverLocation == null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Driver location with plate number " + carPlateNumber + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentDriverLocation.setDriverName(driverLocation.getDriverName());
        currentDriverLocation.setDriverPhoneNumber(driverLocation.getDriverPhoneNumber());
        currentDriverLocation.setLatitude(driverLocation.getLatitude());
        currentDriverLocation.setLongitude(driverLocation.getLongitude());

        driverLocationService.delete(currentDriverLocation);
        return new ResponseEntity<DriverLocation>(currentDriverLocation, HttpStatus.OK);
    }



}
