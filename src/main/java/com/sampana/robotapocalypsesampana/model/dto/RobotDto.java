package com.sampana.robotapocalypsesampana.model.dto;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Data
public class RobotDto {
    private String model;
    private String serialNumber;
    private ZonedDateTime manufacturedDate;
    private String category;
}
