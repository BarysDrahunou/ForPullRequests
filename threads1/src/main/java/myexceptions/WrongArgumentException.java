package myexceptions;

public class WrongArgumentException extends IllegalArgumentException {

    private final IllegalArgumentException exception;
    private final String problemValue;

    public WrongArgumentException(String message, String problemValue) {
        this.exception = new IllegalArgumentException(message);
        this.problemValue = problemValue;
    }

    public IllegalArgumentException getException() {
        return exception;
    }

    public String getProblemValue() {
        return problemValue;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", problemValue, exception.getMessage());
    }
}