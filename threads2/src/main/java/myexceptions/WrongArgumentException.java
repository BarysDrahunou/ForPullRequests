package myexceptions;

public class WrongArgumentException extends IllegalArgumentException {

    private final String problemValue;

    public WrongArgumentException(String problemValue, String message) {
        super(message);
        this.problemValue = problemValue;
    }

    public WrongArgumentException(String problemValue, String message, Throwable cause) {
        super(message, cause);
        this.problemValue = problemValue;
    }

    public String getProblemValue() {
        return problemValue;
    }

    @Override
    public String getMessage() {
        return String.format("%s - %s", problemValue, super.getMessage());
    }

}