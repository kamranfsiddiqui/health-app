package com.kfs.health_app.repositories;

public class WorkoutRepositoryException extends Exception {

    public WorkoutRepositoryException(Throwable cause) {
        super(cause);
    }

    public WorkoutRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkoutRepositoryException(String message) {
        super(message);
    }
}
