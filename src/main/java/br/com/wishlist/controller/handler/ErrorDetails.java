package br.com.wishlist.controller.handler;

public class ErrorDetails {
    String field;
    String message;

    public ErrorDetails(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
