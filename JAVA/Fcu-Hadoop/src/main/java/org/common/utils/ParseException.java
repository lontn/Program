package org.common.utils;

public class ParseException extends Exception {

    static final long serialVersionUID = -2595907169969396059L;

    private final String json;

    public ParseException(String message, String json) {
        super(message);
        this.json = json;
    }

    public ParseException(String message, String json, Throwable cause) {
        super(message, cause);
        this.json = json;
    }

    public String getJson() {
        return json;
    }

}
