package utils.exceptions;

public class ErrorInOperationException{

    private String errorMessage;

    public ErrorInOperationException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
