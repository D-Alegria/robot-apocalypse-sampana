package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.RobotApocalypseSampanaApplication;
import com.sampana.robotapocalypsesampana.exception.BadRequestException;
import com.sampana.robotapocalypsesampana.exception.RequestAlreadyPerformedException;
import com.sampana.robotapocalypsesampana.model.Location;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.model.request.InfectedReportRequest;
import com.sampana.robotapocalypsesampana.repository.SurvivorRepository;
import com.sampana.robotapocalypsesampana.util.TestConstants;
import com.sampana.robotapocalypsesampana.util.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RobotApocalypseSampanaApplication.class)
@SpringBootTest
class SurvivorServiceTest {

    Survivor expectedSurvivor;
    Response<Survivor> expectedResponse;
    @Mock
    private SurvivorRepository survivorRepository;
    @InjectMocks
    private SurvivorService survivorService;

    @BeforeEach
    void setUp() {
        expectedSurvivor = TestConstants.getTestSurvivor();
        expectedResponse = Utils.successfulResponse(List.of(expectedSurvivor));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addSurvivor() {
        // arrange
        when(survivorRepository.create(any(Survivor.class))).thenReturn(expectedSurvivor);
        // act
        Response<Survivor> actualResponse = survivorService.addSurvivor(expectedSurvivor);
        // assert
        assertEquals(expectedResponse.getResponseCode(), actualResponse.getResponseCode());
        assertFalse(actualResponse.getModelList().get(0).isInfected());
    }

    @Test
    void updateSurvivorLocation() {
        // arrange
        Location location = new Location();
        location.setLatitude(2190);
        location.setLongitude(9023);
        expectedSurvivor.setLocation(location);
        when(survivorRepository.getByUuid(anyString())).thenReturn(expectedSurvivor);
        when(survivorRepository.update(any(Survivor.class))).thenReturn(expectedSurvivor);
        // act
        Response<Survivor> actualResponse = survivorService.updateSurvivorLocation(expectedSurvivor.getUuid(), location);
        // assert
        assertEquals(expectedResponse.getResponseCode(), actualResponse.getResponseCode());
        assertEquals(expectedSurvivor.getLocation().getLatitude(), actualResponse.getModelList().get(0).getLocation().getLatitude());
    }

    @Test
    void reportInfected() {
        // arrange
        InfectedReportRequest request = new InfectedReportRequest();
        request.setInfectedUuid("infected-uuid");
        request.setInformantUuid("informant-uuid");
        when(survivorRepository.getByUuid(request.getInfectedUuid())).thenReturn(expectedSurvivor);
        when(survivorRepository.getByUuid(request.getInformantUuid())).thenReturn(expectedSurvivor);
        when(survivorRepository.update(any(Survivor.class))).thenReturn(expectedSurvivor);
        // act
        Response<Survivor> actualResponse = survivorService.reportInfected(request);
        // assert
        assertEquals(expectedResponse.getResponseCode(), actualResponse.getResponseCode());
    }

    @Test
    void reportInfectedAlreadyReported() {
        // arrange
        InfectedReportRequest request = new InfectedReportRequest();
        request.setInfectedUuid("infected-uuid");
        request.setInformantUuid("informant-uuid");
        expectedSurvivor.setInfected(true);
        when(survivorRepository.getByUuid(request.getInfectedUuid())).thenReturn(expectedSurvivor);
        when(survivorRepository.getByUuid(request.getInformantUuid())).thenReturn(expectedSurvivor);
        when(survivorRepository.update(any(Survivor.class))).thenReturn(expectedSurvivor);
        // assert
        assertThrows(RequestAlreadyPerformedException.class, () -> survivorService.reportInfected(request));
    }

    @Test
    void reportInfectedReportYourself() {
        // arrange
        InfectedReportRequest request = new InfectedReportRequest();
        request.setInfectedUuid("infected-uuid");
        request.setInformantUuid("infected-uuid");
        expectedSurvivor.setInfected(true);
        when(survivorRepository.getByUuid(request.getInfectedUuid())).thenReturn(expectedSurvivor);
        when(survivorRepository.getByUuid(request.getInformantUuid())).thenReturn(expectedSurvivor);
        when(survivorRepository.update(any(Survivor.class))).thenReturn(expectedSurvivor);
        // assert
        assertThrows(BadRequestException.class, () -> survivorService.reportInfected(request));
    }
}