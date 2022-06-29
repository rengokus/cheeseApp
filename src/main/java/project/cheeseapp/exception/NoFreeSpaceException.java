package project.cheeseapp.exception;

public class NoFreeSpaceException extends RuntimeException {
    public NoFreeSpaceException(String message) {
        super(message);
    }
}
