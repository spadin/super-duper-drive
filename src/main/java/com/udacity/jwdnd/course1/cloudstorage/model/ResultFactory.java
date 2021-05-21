package com.udacity.jwdnd.course1.cloudstorage.model;

public class ResultFactory {

    public static Result createErrorResult(String message) {
        return new Result(message, Result.Type.ERROR);
    }

    public static Result createSuccessResult(String message) {
        return new Result(message, Result.Type.SUCCESS);
    }

    public static Result createEmptyResult() {
        return new Result();
    }
}
