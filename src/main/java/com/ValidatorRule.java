package com;

public class ValidatorRule {
    private boolean invalid;
    private String message;

    public ValidatorRule(boolean invalid, String message) {
        this.invalid = invalid;
        this.message = message;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public String getMessage() {
        return message;
    }
}
