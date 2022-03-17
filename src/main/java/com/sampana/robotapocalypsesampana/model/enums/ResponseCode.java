package com.sampana.robotapocalypsesampana.model.enums;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

public enum ResponseCode {

    Successful("00", "Successful"),
    BadRequest("S01", "Bad request"),
    DuplicateRequest("S02", "Entity already exist"),
    NotFound("S04", "Entity doesn't exist"),
    RequestFailed("S05", "Error occurred"),
    SystemError("S96", "Internal System Error, Please try again later.");

    public final String code;
    public final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
