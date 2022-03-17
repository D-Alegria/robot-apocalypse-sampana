package com.sampana.robotapocalypsesampana.exception;

/**
 * Created by Demilade Oladugba on 3/17/2022
 */
public class RequestAlreadyPerformedException extends RuntimeException {

    public RequestAlreadyPerformedException(String msg) {
        super(msg);
    }
}
