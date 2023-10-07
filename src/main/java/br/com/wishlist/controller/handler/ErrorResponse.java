package br.com.wishlist.controller.handler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    String timestamp = Instant.now().toString();
    String errorMessage;
    List<ErrorDetails> errorDetails = new ArrayList<>();

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    public void addError(String field, String defaultMessage) {
        ErrorDetails error = new ErrorDetails(field, defaultMessage);
        this.errorDetails.add(error);
    }
}

