package com.nike.parkinglot.service;

import com.nike.parkinglot.domain.ParkingSpace;
import com.nike.parkinglot.domain.Parkinglot;
import com.nike.parkinglot.repository.ParkingSpaceRepository;
import com.nike.parkinglot.repository.ParkinglotRepository;
import model.ParkingSpaceModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ParkinglotServiceTest {

    private static final String NAME = "test";

    @Mock
    ParkinglotRepository parkinglotRepository;

    @Mock
    ParkingSpaceRepository parkingSpaceRepository;

    private ParkinglotService parkinglotService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
         parkinglotService= new ParkinglotService(parkinglotRepository, parkingSpaceRepository);
    }

    @Test
    public void testGetParkinglotByName() {
        Parkinglot mockParkinglot = new Parkinglot();
        mockParkinglot.setName(NAME);

        when(parkinglotRepository.getByName(anyString())).thenReturn(mockParkinglot);
        Parkinglot result = parkinglotService.getParkinglotByName(NAME);

        assertNotNull(result);
        assertEquals(NAME, result.getName());
    }

    @Test
    public void exitParkinglotFee5(){


        ParkingSpaceModel mockParkingSpaceModel = new ParkingSpaceModel();
        mockParkingSpaceModel.setParkinglotName(NAME);
        mockParkingSpaceModel.setParkingSpot(0);

        ParkingSpace mockParkingSpace = new ParkingSpace();
        mockParkingSpace.setTaken(true);
        mockParkingSpace.setParkingTime(LocalDateTime.now().plusHours((long)1));

        Parkinglot mockParkinglot = new Parkinglot();
        mockParkinglot.setName(NAME);
        mockParkinglot.setParkingSpaceSize(5);
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        parkingSpaces.add(mockParkingSpace);
        mockParkinglot.setParkingSpaces(parkingSpaces);

        when(parkinglotRepository.getByName(anyString())).thenReturn(mockParkinglot);
        int fee5 = parkinglotService.exitParkinglot(mockParkingSpaceModel);

        assertNotNull(fee5);
        assertEquals(5, fee5);
    }

    @Test
    public void exitParkinglotFee10(){


        ParkingSpaceModel mockParkingSpaceModel = new ParkingSpaceModel();
        mockParkingSpaceModel.setParkinglotName(NAME);
        mockParkingSpaceModel.setParkingSpot(0);

        ParkingSpace mockParkingSpace = new ParkingSpace();
        mockParkingSpace.setTaken(true);
        mockParkingSpace.setParkingTime(LocalDateTime.now().plusHours((long)2).plusSeconds(60));

        Parkinglot mockParkinglot = new Parkinglot();
        mockParkinglot.setName(NAME);
        mockParkinglot.setParkingSpaceSize(5);
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        parkingSpaces.add(mockParkingSpace);
        mockParkinglot.setParkingSpaces(parkingSpaces);

        when(parkinglotRepository.getByName(anyString())).thenReturn(mockParkinglot);
        int fee10 = parkinglotService.exitParkinglot(mockParkingSpaceModel);

        assertNotNull(fee10);
        assertEquals(10, fee10);
    }

    @Test
    public void exitParkinglotFee15(){


        ParkingSpaceModel mockParkingSpaceModel = new ParkingSpaceModel();
        mockParkingSpaceModel.setParkinglotName(NAME);
        mockParkingSpaceModel.setParkingSpot(0);

        ParkingSpace mockParkingSpace = new ParkingSpace();
        mockParkingSpace.setTaken(true);
        mockParkingSpace.setParkingTime(LocalDateTime.now().plusHours((long)15));

        Parkinglot mockParkinglot = new Parkinglot();
        mockParkinglot.setName(NAME);
        mockParkinglot.setParkingSpaceSize(5);
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        parkingSpaces.add(mockParkingSpace);
        mockParkinglot.setParkingSpaces(parkingSpaces);

        when(parkinglotRepository.getByName(anyString())).thenReturn(mockParkinglot);
        int fee15 = parkinglotService.exitParkinglot(mockParkingSpaceModel);

        assertNotNull(fee15);
        assertEquals(10, fee15);
    }
}
