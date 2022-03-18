package com.sampana.robotapocalypsesampana.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Demilade Oladugba on 3/18/2022
 **/

@Data
public class Report {
    private double infectedSurvivorsPercentage;
    private double nonInfectedSurvivorsPercentage;
    private List<Survivor> infectedSurvivors;
    private List<Survivor> nonInfectedSurvivors;
    private List<Robot> robots;
}
