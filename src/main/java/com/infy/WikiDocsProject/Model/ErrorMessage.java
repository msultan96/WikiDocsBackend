package com.infy.WikiDocsProject.Model;

import lombok.*;

@Data
@NoArgsConstructor
public class ErrorMessage {

    private int errorCode;
    private String errorMessage;

    public ErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}