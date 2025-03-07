package model.hands;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThreeOfOneKindTest {
    private ThreeOfOneKind tookTest1;

    @BeforeEach
    void setUp() {
        // [♥6, ♦6, ♠6]
        List<Card> took1 = List.of(new Card(CardSuit.HEARTS, CardRank.SIX),
                new Card(CardSuit.DIAMONDS, CardRank.SIX),
                new Card(CardSuit.SPADES, CardRank.SIX)
        );
        System.out.println(took1);
        tookTest1 = new ThreeOfOneKind(took1);
    }

    @Test
    void getHandName() {
        Assertions.assertEquals(HandRank.THREE_OF_A_KIND, tookTest1.getHandName());
    }

    @Test
    void getHandValue() {
        Assertions.assertEquals(5, tookTest1.getHandValue());
    }

    @Test
    void getMainCards() {
        List<Card> expected1 =List.of(new Card(CardSuit.HEARTS, CardRank.SIX),
                new Card(CardSuit.DIAMONDS, CardRank.SIX),
                new Card(CardSuit.SPADES, CardRank.SIX));
        Assertions.assertEquals(expected1, tookTest1.getMainCards());
    }

    @Test
    void getThreeOfOneKindRank() {
        Assertions.assertEquals(CardRank.SIX, tookTest1.getThreeOfOneKindRank());
    }
}