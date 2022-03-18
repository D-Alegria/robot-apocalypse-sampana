package com.sampana.robotapocalypsesampana.controller.api.v1;

import com.sampana.robotapocalypsesampana.model.Report;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.service.ReportService;
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
@RequestMapping(value = "/api/v1/report")
public class ReportApiController {

    private final ReportService robotService;

    public ReportApiController(ReportService robotService) {
        this.robotService = robotService;
    }

    @GetMapping()
    public Callable<ResponseEntity<Response<Report>>> getCurrentStatus() {
        return () -> {
            Response<Report> viewResponse = robotService.getCurrentStatus();
            return new ResponseEntity<>(viewResponse, HttpStatus.OK);
        };
    }
}
