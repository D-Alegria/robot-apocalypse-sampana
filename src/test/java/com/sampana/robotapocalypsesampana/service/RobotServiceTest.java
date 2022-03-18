package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.RobotApocalypseSampanaApplication;
import com.sampana.robotapocalypsesampana.exception.SystemException;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Robot;
import com.sampana.robotapocalypsesampana.model.enums.ResponseCode;
import com.sampana.robotapocalypsesampana.util.RequestManager;
import com.sampana.robotapocalypsesampana.util.Utils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RobotApocalypseSampanaApplication.class)
@SpringBootTest
class RobotServiceTest {

    @Mock
    private RequestManager requestManager;

    @InjectMocks
    private RobotService robotService;

    private ResponseEntity<List> responseEntity;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        FieldUtils.writeField(robotService, "robotLink", "test-link", true);
        List<?> test = new ArrayList<>(
                List.of(
                        Utils.jsonUnMarshall("{\"model\":\"053JO\",\"serialNumber\":\"TUV4KVR4YQ1CPJ1\",\"manufacturedDate\":\"2022-03-23T07:00:46.6079176+00:00\",\"category\":\"Land\"}", Object.class),
                        Utils.jsonUnMarshall("{\"model\":\"0NJVB\",\"serialNumber\":\"0GVUY7H83AILZ4G\",\"manufacturedDate\":\"2022-04-16T07:00:46.6079377+00:00\",\"category\":\"Land\"}", Object.class),
                        Utils.jsonUnMarshall("{\"model\":\"0PONX\",\"serialNumber\":\"CDN5K2E6J63PD7W\",\"manufacturedDate\":\"2022-04-19T07:00:46.6079402+00:00\",\"category\":\"Land\"}", Object.class)));
        responseEntity = new ResponseEntity<>(test, HttpStatus.OK);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllRobots() {
        // arrange
        when(requestManager.get(anyString(), isNull(), eq(List.class))).thenReturn(responseEntity);
        // act
        Response<Robot> actualResponse = robotService.getAllRobots();
        // assert
        assertEquals(ResponseCode.Successful.code, actualResponse.getResponseCode());
        assertFalse(CollectionUtils.isEmpty(actualResponse.getModelList()));
        assertEquals(3, actualResponse.getModelList().size());
    }

    @Test
    void getAllRobotsError() {
        // arrange
        responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(requestManager.get(anyString(), isNull(), eq(List.class))).thenReturn(responseEntity);
        // assert
        assertThrows(SystemException.class, () -> robotService.getAllRobots());
    }
}