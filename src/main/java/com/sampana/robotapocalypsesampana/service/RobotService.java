package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.exception.SystemException;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Robot;
import com.sampana.robotapocalypsesampana.model.RobotDto;
import com.sampana.robotapocalypsesampana.util.RequestManager;
import com.sampana.robotapocalypsesampana.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Service
public class RobotService {

    private final RequestManager requestManager;
    private final String robotLink;

    public RobotService(RequestManager requestManager, @Value("${robot.link}") String robotLink) {
        this.requestManager = requestManager;
        this.robotLink = robotLink;
    }

    public Response<Robot> getAllRobots() {
        ResponseEntity<List> responseEntity = requestManager.get(robotLink, null, List.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK || !responseEntity.hasBody() || responseEntity.getBody() == null)
            throw new SystemException("An error occurred while getting all robot. Please try again later.");

        List<Robot> robots = new ArrayList<>();
        for (Object o : responseEntity.getBody()) {
            Robot robot = new Robot(Utils.jsonUnMarshall(Utils.jsonMarshal(o), RobotDto.class));
            robots.add(robot);
        }

        robots.sort(Comparator.comparing(Robot::getManufacturedDate));
        return Utils.successfulResponse(robots);
    }
}
