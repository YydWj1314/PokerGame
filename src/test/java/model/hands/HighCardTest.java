package model.hands;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighCardTest {
    private HighCard highCardTest1;

    @BeforeEach
    void setUp() {
        // [♥8, ♠Q, ♦K]
        ArrayList<Card> highCard1 = new ArrayList<>(List.of(
                new Card(CardSuit.HEARTS, CardRank.EIGHT),
                new Card(CardSuit.SPADES, CardRank.QUEEN),
                new Card(CardSuit.DIAMONDS, CardRank.KING)
        ));
        System.out.println(highCard1);
        highCardTest1 = new HighCard(highCard1);
    }

    @Test
    void getHandName() {
        Assertions.assertEquals(HandRank.HIGH_CARD, highCardTest1.getHandName());
    }

    @Test
    void getHandValue() {
        Assertions.assertEquals(1, highCardTest1.getHandValue());
    }

    @Test
    void getMainCards() {
        List<Card> expected1 = List.of( new Card(CardSuit.DIAMONDS, CardRank.KING));
        Assertions.assertEquals(expected1, highCardTest1.getMainCards());
    }
}