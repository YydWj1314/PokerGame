package model.exception;

public class NoEnoughCardsException extends RuntimeException {
    public NoEnoughCardsException(String message) {
        super(message);
    }
}
