package com.udacity.jwdnd.course1.cloudstorage.exception;

public class InternalException extends Exception {

    public InternalException(String msg) {
        super(msg);
    }

    public InternalException(String msg, Throwable t) {
        super(msg, t);
    }
}
