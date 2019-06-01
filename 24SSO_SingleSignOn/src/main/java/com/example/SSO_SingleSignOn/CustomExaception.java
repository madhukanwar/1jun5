package com.example.SSO_SingleSignOn;

public class CustomExaception extends Exception{

    public CustomExaception(String message) {
        super("hello"+message);
    }
}
