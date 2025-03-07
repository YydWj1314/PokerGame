package model.enumuration;

public enum HandRank {
    STRAIGHT_FLUSH(6),    // 同花顺
    THREE_OF_A_KIND(5),   // 三条
    FLUSH(4),             // 同花
    STRAIGHT(3),          // 顺子
    PAIR(2),              // 一对
    HIGH_CARD(1);         // 高牌

    private final int rankValue;

    HandRank(int rankValue){
        this.rankValue = rankValue;
    }

    public int getRankValue(){
        return this.rankValue;
    }
}
