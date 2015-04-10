package com.example.simello.classiServer;

import java.io.Serializable;

/**
 * Created by Sunfury on 10/04/15.
 */
public class BaseOutput implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 7497916034877975872L;

    private Exception exception;
    private String message;
    private Result result;

    public Exception getException() {
        return exception;
    }
    public void setException(Exception exception) {
        this.exception = exception;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }


    public enum Result {
        OK("OK"), FAIL("FAIL");

        private String value;

        private Result(String result) {
            this.value = result;
        }

        @Override
        public String toString() {
            return getValue();
        }

        public String getValue() {
            return value;
        }

        public static final Result parse(String value) {
            for (Result result : values()) {
                if (result.value == value) {
                    return result;
                }
            }
            throw new RuntimeException("Invalid value for enum " + Result.class + ": " + value);
        }
    }


    @Override
    public String toString() {
        return "BaseOutput [exception=" + exception + ", message=" + message + ", result=" + result + "]";
    }


}