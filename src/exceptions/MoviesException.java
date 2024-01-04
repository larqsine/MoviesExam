package exceptions;


import utility.ExceptionsMessages;

public class MoviesException extends Exception {
    private ExceptionsMessages exceptionsMessages;
    public MoviesException() {
    }

    public MoviesException(String message) {
        super(message);
    }
    public MoviesException(ExceptionsMessages message){
        super(message.getValue());
        this.exceptionsMessages=message;
    }

    public MoviesException(ExceptionsMessages message, Throwable cause){
        super(message.getValue(),cause);
        this.exceptionsMessages=message;
    }

    public MoviesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoviesException(Throwable cause) {
        super(cause);
    }

    public MoviesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExceptionsMessages getExceptionsMessages() {
        return exceptionsMessages;
    }
}
