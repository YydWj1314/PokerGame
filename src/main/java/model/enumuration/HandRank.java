package model.enumuration;

public enum HandRank {
    HIGH_CARD(1),       // 高牌
    PAIR(2),              // 一对
    STRAIGHT(3),          // 顺子
    FLUSH(4),             // 同花
    THREE_OF_A_KIND(5),   // 三条
    STRAIGHT_FLUSH(6);    // 同花顺

    private final int rankValue;

    HandRank(int rankValue){
        this.rankValue = rankValue;
    }

    public int getRankValue(){
        return this.rankValue;
    }
}
