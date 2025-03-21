package enumuration;

public enum CardSuit {
    SPADES("SPADES","♠" ),
    HEARTS("HEARTS","♥"),
    DIAMONDS("DIAMONDS","♦"),
    CLUBS("CLUBS", "♣");

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

    /**
     * @param name
     * @return
     */
    public static CardSuit fromName(String name){
        for(CardSuit suit: values()){
            if(suit.getName().equals(name)){
                return suit;
            }
        }
        throw new IllegalArgumentException("No suit find with name:" + name);
    }

    @Override
    public String toString() {
        return symbol;
    }
}

