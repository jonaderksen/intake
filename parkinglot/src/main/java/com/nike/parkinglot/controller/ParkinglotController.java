package com.nike.parkinglot.controller;

import com.nike.parkinglot.domain.ParkingSpace;
import com.nike.parkinglot.domain.Parkinglot;
import com.nike.parkinglot.service.ParkinglotService;
import model.ParkingSpaceModel;
import model.ParkinglotModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parking")
public class ParkinglotController {
    private final ParkinglotService parkinglotService;

    @Autowired
    public ParkinglotController(ParkinglotService parkinglotService){
        this.parkinglotService = parkinglotService;
    }

    @PostMapping
    public ResponseEntity<Parkinglot> createParkinglotByName(@RequestBody ParkinglotModel parkinglotModel){
        Parkinglot parkinglot = this.parkinglotService.createParkinglot(parkinglotModel);
        if (parkinglot == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(parkinglot,HttpStatus.OK);
    }

    @GetMapping( value = "getparkinglot/{name}")
    public ResponseEntity<Parkinglot> getParkinglotByName(@PathVariable("name") String name){
        Parkinglot parkinglot = this.parkinglotService.getParkinglotByName(name);
        if (parkinglot == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(parkinglot,HttpStatus.OK);
    }

    @GetMapping(value = "/{name}/{space}")
    public ResponseEntity<Boolean> getParkingSpace(@PathVariable("name") String name, @PathVariable("space") int space){
        Boolean taken = this.parkinglotService.isParkingSpaceTaken(name, space);
        if (taken == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(taken,HttpStatus.OK);
    }

    @GetMapping(value = "/getallfree/{name}")
    public ResponseEntity<Long> getFreeParkingSpaces(@PathVariable("name") String name){
        long spacesFree = this.parkinglotService.amountOfFreeSpaces(name);
        return new ResponseEntity<>(spacesFree,HttpStatus.OK);
    }

    @PostMapping( value="/park")
    public ResponseEntity<ParkingSpace> getParkingSpace(@RequestBody ParkingSpaceModel parkingSpaceModel){
        ParkingSpace parkingSpace = this.parkinglotService.takeParkingSpace(parkingSpaceModel);
        if (parkingSpace == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(parkingSpace,HttpStatus.OK);
    }

    @PostMapping(value="/exitParkinglot")
    public ResponseEntity<String> exitParkinglot(@RequestBody ParkingSpaceModel parkingSpaceModel){
        Integer fee = this.parkinglotService.exitParkinglot(parkingSpaceModel);
        return new ResponseEntity<>("your fee is: $"+fee,HttpStatus.OK);
    }

}
