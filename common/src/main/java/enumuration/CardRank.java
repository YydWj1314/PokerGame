package enumuration;

public enum CardRank {
    TWO(2, "2", "TWO"),
    THREE(3, "3","THREE" ),
    FOUR(4, "4", "FOUR"),
    FIVE(5, "5", "FIVE"),
    SIX(6, "6", "SIX"),
    SEVEN(7, "7", "SEVEN"),
    EIGHT(8, "8", "EIGHT"),
    NINE(9, "9", "NINE"),
    TEN(10, "10", "TEN"),
    JACK(11, "jack", "JACK"),
    QUEEN(12, "queen", "QUEEN"),
    KING(13, "king", "KING"),
    ACE(14, "ace", "ACE");

//    JOKER_BLACK("Joker-Black"),
//    JOKER_RED("Joker-Red");

    private final int value;
    private final String label;
    private final String name;

    CardRank(int value, String label, String name) {
        this.value = value;
        this.label = label;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName(){ return this.name; }

    public String getLabel() {
        return label;
    }

    /**
     * @param name
     * @return
     */
    public static CardRank fromName(String name){
        for(CardRank rank: values()){
            if(rank.getName().equals(name)){
                return rank;
            }
        }
        throw new IllegalArgumentException("No suit find with name:" + name);
    }

    @Override
    public String toString() {
        return label;
    }


}
