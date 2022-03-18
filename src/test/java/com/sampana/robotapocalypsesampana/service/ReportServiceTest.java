package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.RobotApocalypseSampanaApplication;
import com.sampana.robotapocalypsesampana.model.Report;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Robot;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.model.enums.ResponseCode;
import com.sampana.robotapocalypsesampana.repository.SurvivorRepository;
import com.sampana.robotapocalypsesampana.util.TestConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RobotApocalypseSampanaApplication.class)
@SpringBootTest
class ReportServiceTest {

    @Mock
    private SurvivorRepository survivorRepository;
    @Mock
    private RobotService robotService;
    @InjectMocks
    private ReportService reportService;
    private Report expectedReport;
    private List<Survivor> expectedSurvivors;
    private List<Robot> expectedRobots;

    @BeforeEach
    void setUp() {
        expectedReport = TestConstants.getReport();
        expectedSurvivors = expectedReport.getInfectedSurvivors();
        expectedSurvivors.addAll(expectedReport.getNonInfectedSurvivors());
        expectedRobots = expectedReport.getRobots();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrentStatus() {
        // arrange
        when(survivorRepository.getAll(0, 0)).thenReturn(new PageImpl<>(expectedSurvivors));
        when(robotService.getAllRobots()).thenReturn(expectedRobots);
        // act
        Response<Report> actualResponse = reportService.getCurrentStatus();
        // assert
        assertEquals(ResponseCode.Successful.code, actualResponse.getResponseCode());
        assertFalse(CollectionUtils.isEmpty(actualResponse.getModelList()));
        assertEquals(expectedReport.getInfectedSurvivorsPercentage(), actualResponse.getModelList().get(0).getInfectedSurvivorsPercentage());
        assertEquals(expectedReport.getNonInfectedSurvivorsPercentage(), actualResponse.getModelList().get(0).getNonInfectedSurvivorsPercentage());
        assertEquals(expectedReport.getRobots().size(), actualResponse.getModelList().get(0).getRobots().size());
    }
}