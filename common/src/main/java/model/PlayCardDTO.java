package model;

import enumuration.CardRank;
import enumuration.CardSuit;

import java.util.List;

public class PlayCardDTO {
    private int playerId;
    private CardSuit suit;
    private CardRank rank;

    public PlayCardDTO(int playerId, CardSuit suit, CardRank rank) {
        this.playerId = playerId;
        this.suit = suit;
        this.rank = rank;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "PlayCardDTO{" +
                "playerId=" + playerId +
                ", suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}

