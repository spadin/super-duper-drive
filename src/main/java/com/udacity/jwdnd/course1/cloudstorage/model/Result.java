package com.udacity.jwdnd.course1.cloudstorage.model;

public class Result {

    String message;
    Type type;

    public Result(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public Result() {
        this.message = "";
        this.type = Type.EMPTY;
    }

    public String getMessage() {
        return this.message;
    }

    public String getClassName() {
        return this.type.getClassName();
    }

    public boolean isEmptyResult() {
        return this.type == Type.EMPTY;
    }

    public enum Type {
        SUCCESS("alert-success"),
        ERROR("alert-danger"),
        EMPTY;

        private final String className;

        Type(String className) {
            this.className = className;
        }

        Type() {
            this.className = "";
        }

        public String getClassName() {
            return this.className;
        }
    }
}
