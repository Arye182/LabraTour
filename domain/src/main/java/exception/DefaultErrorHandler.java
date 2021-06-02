package exception;

public class DefaultErrorHandler implements ErrorHandler{
    private final Exception exception;
    private static final String defaultErrorMessage = "Error";
    public DefaultErrorHandler(Exception exception) {
        this.exception = exception;
    }


    public Exception getException() {
        return exception;
    }


    public String getErrorMessage() {
        if (exception!=null)
            return this.exception.getMessage();
        else{
            return defaultErrorMessage;
        }
    }
}
