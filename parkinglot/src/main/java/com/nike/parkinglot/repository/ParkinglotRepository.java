package com.nike.parkinglot.repository;

import com.nike.parkinglot.domain.Parkinglot;
import org.springframework.data.repository.CrudRepository;

public interface ParkinglotRepository extends CrudRepository<Parkinglot, Long> {
    Parkinglot getByName(String name);
}
