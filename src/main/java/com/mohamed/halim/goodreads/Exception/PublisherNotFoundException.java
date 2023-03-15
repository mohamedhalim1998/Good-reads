package com.mohamed.halim.goodreads.Exception;

public class PublisherNotFoundException extends RuntimeException{
    public PublisherNotFoundException() {
        super("No Such Publisher Found");
    }
}
