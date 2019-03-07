package com.nike.parkinglot.service;

import com.nike.parkinglot.domain.ParkingSpace;
import com.nike.parkinglot.domain.Parkinglot;
import com.nike.parkinglot.repository.ParkingSpaceRepository;
import com.nike.parkinglot.repository.ParkinglotRepository;
import model.ParkingSpaceModel;
import model.ParkinglotModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkinglotService {
    private final ParkinglotRepository parkinglotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    public ParkinglotService(ParkinglotRepository parkinglotRepository, ParkingSpaceRepository parkingSpaceRepository){
        this.parkinglotRepository = parkinglotRepository;
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public Parkinglot getParkinglotByName(String name){
        if (name.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid parking lot name provided");
        }
        return this.parkinglotRepository.getByName(name);
    }

    public Parkinglot createParkinglot(ParkinglotModel parkinglotModel) {
        if (parkinglotModel == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking lot model not provided");
        }
        if (parkinglotModel.getName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking lot name not provided");
        }
        if (this.parkinglotRepository.getByName(parkinglotModel.getName()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Use unique name for your parkinglot");
        }
        if (parkinglotModel.getSpaces() < 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided more than 0 spaces for the parking lot");
        }

        Parkinglot parkinglot = new Parkinglot();
        parkinglot.setName(parkinglotModel.getName());
        List<ParkingSpace> parkingSpaces = new ArrayList<>(parkinglotModel.getSpaces());
        parkinglot.setParkingSpaces(parkingSpaces);
        parkinglot.setParkingSpaceSize(parkinglotModel.getSpaces());
        Parkinglot savedParkingLot  = this.parkinglotRepository.save(parkinglot);

        for(int i = 0; i < parkinglotModel.getSpaces(); i++){
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpace.setParkinglot(savedParkingLot);
            this.parkingSpaceRepository.save(parkingSpace);
            parkingSpaces.add(parkingSpace);
        }

        return savedParkingLot;
    }

    public Boolean isParkingSpaceTaken(String name, int space) {
        Parkinglot parkinglot = checkParkingSpaceNameAndSpace(name,space);
        ParkingSpace parkingSpace = parkinglot.getParkingSpaces().get(space);

        return parkingSpace.isTaken();
    }


    public long amountOfFreeSpaces(String name) {
        if (name.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking lot name not provided");
        }
        Parkinglot parkinglot = this.parkinglotRepository.getByName(name);

        if (parkinglot == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalided parking lot name provided");
        }
        long availableSpots = parkinglot.getParkingSpaces().stream().filter(parkingSpace -> !parkingSpace.isTaken()).count();

        return availableSpots;
    }

    public ParkingSpace takeParkingSpace(ParkingSpaceModel parkingSpaceModel) {
        ParkingSpace parkingSpace = CheckParkingSpaceModel(parkingSpaceModel);
        parkingSpace.setTaken(true);
        parkingSpace.setParkingTime(LocalDateTime.now());
        return this.parkingSpaceRepository.save(parkingSpace);
    }

    public Integer exitParkinglot(ParkingSpaceModel parkingSpaceModel) {
        ParkingSpace parkingSpace = CheckParkingSpaceModel(parkingSpaceModel);
        if (!parkingSpace.isTaken()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking space not taken");
        }
        parkingSpace.setTaken(false);
        long seconds = Duration.between(LocalDateTime.now(), parkingSpace.getParkingTime()).getSeconds();
        long hours = seconds / 3600;
        if (hours < 2){
            return 5;
        }else if ( hours < 10){
            return 10;
        }else{
            return 15;
        }
    }

    private ParkingSpace CheckParkingSpaceModel(ParkingSpaceModel parkingSpaceModel){
        if (parkingSpaceModel == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking space model not provided");
        }
        Parkinglot parkinglot = checkParkingSpaceNameAndSpace(parkingSpaceModel.getParkinglotName(), parkingSpaceModel.getParkingSpot());

        return parkinglot.getParkingSpaces().get(parkingSpaceModel.getParkingSpot());
    }

    private Parkinglot checkParkingSpaceNameAndSpace(String name, int space){

        if (name.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking lot name not provided");
        }
        if (space< 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid parking space provided");
        }

        Parkinglot parkinglot = this.parkinglotRepository.getByName(name);

        if (parkinglot == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalided parking lot name provided");
        }
        if (space > parkinglot.getParkingSpaceSize()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalided parking space provided");
        }
        return parkinglot;
    }
}
