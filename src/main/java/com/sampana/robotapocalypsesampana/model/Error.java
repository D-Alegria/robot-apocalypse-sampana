package com.sampana.robotapocalypsesampana.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by Demilade Oladugba on 3/17/2022
 */

@Data
@RequiredArgsConstructor
public class Error {
    private final String fieldName;
    private final String message;
}
