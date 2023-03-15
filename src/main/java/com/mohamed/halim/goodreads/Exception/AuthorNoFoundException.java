package com.mohamed.halim.goodreads.Exception;

public class AuthorNoFoundException extends RuntimeException {
    public AuthorNoFoundException() {
        super("No Such Author Found");
    }
}
