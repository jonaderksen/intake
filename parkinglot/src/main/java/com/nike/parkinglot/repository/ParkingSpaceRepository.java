package com.nike.parkinglot.repository;

import com.nike.parkinglot.domain.ParkingSpace;
import org.springframework.data.repository.CrudRepository;

public interface ParkingSpaceRepository extends CrudRepository<ParkingSpace,Long> {
}
