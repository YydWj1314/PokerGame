package model.enumuration;

public enum CardRank {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14, "A");

//    JOKER_BLACK("Joker-Black"),
//    JOKER_RED("Joker-Red");

    private final int value;
    private final String label;

    CardRank(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return label;
    }

}
