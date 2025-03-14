package enumuration;

public enum CardRank {
    TWO(2, "2", "2"),
    THREE(3, "3","3" ),
    FOUR(4, "4", "4"),
    FIVE(5, "5", "5"),
    SIX(6, "6", "6"),
    SEVEN(7, "7", "7"),
    EIGHT(8, "8", "8"),
    NINE(9, "9", "9"),
    TEN(10, "10", "10"),
    JACK(11, "J", "jack"),
    QUEEN(12, "Q", "queen"),
    KING(13, "K", "king"),
    ACE(14, "A", "ace");

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


    @Override
    public String toString() {
        return label;
    }


}
