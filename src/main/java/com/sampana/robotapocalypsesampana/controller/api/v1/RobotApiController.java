package com.sampana.robotapocalypsesampana.controller.api.v1;

import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Robot;
import com.sampana.robotapocalypsesampana.service.RobotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@RestController
@RequestMapping(value = "/api/v1/robot")
public class RobotApiController {

    private final RobotService robotService;

    public RobotApiController(RobotService robotService) {
        this.robotService = robotService;
    }

    @GetMapping()
    public Callable<ResponseEntity<Response<Robot>>> getAll() {
        return () -> {
            Response<Robot> viewResponse = robotService.getAllRobots();
            return new ResponseEntity<>(viewResponse, HttpStatus.OK);
        };
    }
}
