package exceptions;


import utility.ExceptionsMessages;

public class MyTunesException extends Exception {
    private ExceptionsMessages exceptionsMessages;
    public MyTunesException() {
    }

    public MyTunesException(String message) {
        super(message);
    }
    public MyTunesException(ExceptionsMessages message){
        super(message.getValue());
        this.exceptionsMessages=message;
    }

    public MyTunesException(ExceptionsMessages message, Throwable cause){
        super(message.getValue(),cause);
        this.exceptionsMessages=message;
    }

    public MyTunesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyTunesException(Throwable cause) {
        super(cause);
    }

    public MyTunesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExceptionsMessages getExceptionsMessages() {
        return exceptionsMessages;
    }
}
