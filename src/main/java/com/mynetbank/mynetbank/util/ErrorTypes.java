package com.mynetbank.mynetbank.util;

/**
 * Created by Mohamed Maalej on 02/08/17.
 */

/**
 * This enumeration represents the error codes
 */
public enum ErrorTypes {

    SUCCESS("201"),
    OLD_TRANSACTION("204");

    private String errorCode;

    ErrorTypes(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
