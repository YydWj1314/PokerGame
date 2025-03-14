package enumuration;

public enum CardSuit {
    SPADES("spades","♠" ),
    HEARTS("hearts","♥"),
    DIAMONDS("diamonds","♦"),
    CLUBS("clubs", "♣");

    private String name;
    private final String symbol;

    CardSuit(String name,String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

