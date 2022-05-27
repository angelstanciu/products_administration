package com.example.springsecurity.product;

public class NoProductFoundException extends Throwable {
    public NoProductFoundException(String message) {
        super(message);
    }
}
