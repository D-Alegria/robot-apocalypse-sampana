package com.sampana.robotapocalypsesampana.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Data
public class InfectedReportRequest {
    @NotBlank(message = "infectedUuid is required")
    private String infectedUuid;
    @NotBlank(message = "informantUuid is required")
    private String informantUuid;

}
