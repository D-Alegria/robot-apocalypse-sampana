package com.sampana.robotapocalypsesampana.controller.api.v1;

import com.sampana.robotapocalypsesampana.model.Location;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.model.request.InfectedReportRequest;
import com.sampana.robotapocalypsesampana.service.SurvivorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.Callable;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@RestController
@RequestMapping(value = "/api/v1/survivor")
public class SurvivorApiController {

    private final SurvivorService survivorService;

    public SurvivorApiController(SurvivorService survivorService) {
        this.survivorService = survivorService;
    }

    @PostMapping()
    public Callable<ResponseEntity<Response<Survivor>>> create(@Valid @RequestBody Survivor request) {
        return () -> {
            Response<Survivor> viewResponse = survivorService.addSurvivor(request);
            return new ResponseEntity<>(viewResponse, HttpStatus.OK);
        };
    }

    @PutMapping("/location/{uuid}")
    public Callable<ResponseEntity<Response<Survivor>>> updateLocation(@PathVariable String uuid, @Valid @RequestBody Location location) {
        return () -> {
            Response<Survivor> viewResponse = survivorService.updateSurvivorLocation(uuid, location);
            return new ResponseEntity<>(viewResponse, HttpStatus.OK);
        };
    }

    @PostMapping("/report/infected")
    public Callable<ResponseEntity<Response<Survivor>>> reportInfected(@Valid @RequestBody InfectedReportRequest request) {
        return () -> {
            Response<Survivor> viewResponse = survivorService.reportInfected(request);
            return new ResponseEntity<>(viewResponse, HttpStatus.OK);
        };
    }
}
