package com.topjava.kirill.restaurantvoting.util.exception;

public class VoteDeadlineReachedException extends RuntimeException {
    public VoteDeadlineReachedException(String message) {
        super(message);
    }
}
