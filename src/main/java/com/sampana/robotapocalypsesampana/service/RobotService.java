package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.exception.BadRequestException;
import com.sampana.robotapocalypsesampana.exception.SystemException;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Robot;
import com.sampana.robotapocalypsesampana.model.dto.RobotDto;
import com.sampana.robotapocalypsesampana.util.RequestManager;
import com.sampana.robotapocalypsesampana.util.Utils;
import org.apache.commons.lang3.StringUtils;
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

    public Response<Robot> getAllRobots(String query) {
        List<Robot> robots = getAllRobots();
        query = StringUtils.toRootLowerCase(query);

        switch (query) {
            case "model":
                robots.sort(Comparator.comparing(Robot::getModel));
                break;
            case "serialNumber":
            case "sn":
                robots.sort(Comparator.comparing(Robot::getSerialNumber));
                break;
            case "manufacturedDate":
            case "date":
                robots.sort(Comparator.comparing(Robot::getManufacturedDate));
                break;
            case "category":
                robots.sort(Comparator.comparing(Robot::getCategory));
                break;
            default:
                throw new BadRequestException("Invalid query parameter. Allowed parameters are [model, serialNumber|sn, manufacturedDate|date, category]");
        }
        return Utils.successfulResponse(robots);
    }

    public List<Robot> getAllRobots() {
        ResponseEntity<List> responseEntity = requestManager.get(robotLink, null, List.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK || !responseEntity.hasBody() || responseEntity.getBody() == null)
            throw new SystemException("An error occurred while getting all robot. Please try again later.");

        List<Robot> robots = new ArrayList<>();
        for (Object o : responseEntity.getBody()) {
            Robot robot = new Robot(Utils.jsonUnMarshall(Utils.jsonMarshal(o), RobotDto.class));
            robots.add(robot);
        }
        return robots;
    }
}
