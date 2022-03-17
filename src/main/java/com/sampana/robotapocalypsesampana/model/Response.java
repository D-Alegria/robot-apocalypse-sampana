package com.sampana.robotapocalypsesampana.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Demilade Oladugba on 3/17/2022
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {
    private String responseCode;
    private String responseMessage;
    private List<Error> errors;
    private List<T> modelList;
    private long count;
}
