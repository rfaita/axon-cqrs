package com.cqrs.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author rfaita
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements Serializable {

    private String errorCode;
    private String errorMessage;

    public ErrorDTO() {
    }

    public ErrorDTO(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
