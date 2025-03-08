package model.enumuration;

public enum ExceptionMessage {
    EMPTY_DECK("Invalid Operation: Empty Deck"),
    NO_ENOUGH_CARDS("Invalid Operation: No Enough Cards in Deck"),
    EMPTY_HAND("Invalid Operation: Empty Hand"),
    INVALID_CARDS_COUNT("Invalid Operation: Invalid Card Count"),
    CARD_NOT_FOUND("Invalid Operation: Card Not Found"),
    NO_PLAYER("Invalid Operation: No Player"),
    HAND_RANK_NOT_FOUND("Invalid Operation: HandRank Not Found")
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
