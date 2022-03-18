package com.sampana.robotapocalypsesampana.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

public enum Gender {
    Male,
    Female,
    Other;

    @JsonCreator
    public static Gender decode(String code) {
        return Stream.of(Gender.values())
                .filter(targetEnum -> targetEnum.name().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}
