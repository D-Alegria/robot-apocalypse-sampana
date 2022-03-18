package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.model.Report;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.repository.SurvivorRepository;
import com.sampana.robotapocalypsesampana.util.Utils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demilade Oladugba on 3/18/2022
 **/

@Service
public class ReportService {
    private final RobotService robotService;
    private final SurvivorRepository survivorRepository;

    public ReportService(RobotService robotService, SurvivorRepository survivorRepository) {
        this.robotService = robotService;
        this.survivorRepository = survivorRepository;
    }

    public Response<Report> getCurrentStatus() {
        Report report = new Report();
        List<Survivor> infectedSurvivors = new ArrayList<>();
        List<Survivor> nonInfectedSurvivors = new ArrayList<>();
        long infectedCount = 0;

        Page<Survivor> survivors = survivorRepository.getAll(0, 0);
        for (Survivor survivor : survivors.getContent()) {
            if (survivor.isInfected()) {
                infectedSurvivors.add(survivor);
                infectedCount++;
            } else
                nonInfectedSurvivors.add(survivor);
        }

        if (survivors.getTotalElements() > 0) {
            report.setInfectedSurvivorsPercentage((infectedCount / (double) survivors.getTotalElements()) * 100);
            report.setNonInfectedSurvivorsPercentage(100 - report.getInfectedSurvivorsPercentage());
        }
        report.setInfectedSurvivors(infectedSurvivors);
        report.setNonInfectedSurvivors(nonInfectedSurvivors);
        report.setRobots(robotService.getAllRobots());

        return Utils.successfulResponse(List.of(report));
    }
}
