package model.exception;

public class InvalidCardsCountException extends RuntimeException {
    public InvalidCardsCountException(String message) {
        super(message);
    }
}
