package com.mohamed.halim.goodreads.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("No Such User Found");
    }


}
