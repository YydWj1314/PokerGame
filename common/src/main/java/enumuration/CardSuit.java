package enumuration;

public enum CardSuit {
    SPADES("spades","♠" ),
    HEARTS("hearts","♥"),
    DIAMONDS("diamonds","♦"),
    CLUBS("clubs", "♣");

    private String alias;
    private final String symbol;

    CardSuit(String alias,String symbol) {
        this.alias = alias;
        this.symbol = symbol;
    }

    public String getAlias() {
        return alias;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

