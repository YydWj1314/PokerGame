package model.hands;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class StraightTest {
    private Straight straightTest1;

    @BeforeEach
    void setUp() {
        // [♥A, ♠Q, ♦K]
        List<Card> straight1 = new ArrayList<>(List.of(
                new Card(CardSuit.HEARTS, CardRank.TWO),
                new Card(CardSuit.SPADES, CardRank.ACE),
                new Card(CardSuit.DIAMONDS, CardRank.THREE)
        ));
        System.out.println(straight1);

        straightTest1 = new Straight(straight1);
    }

    @Test
    void getHandName() {
        Assertions.assertEquals(HandRank.STRAIGHT, straightTest1.getHandName());
    }

    @Test
    void getHandValue() {
        Assertions.assertEquals(3, straightTest1.getHandValue());
    }

    @Test
    void getMainCards() {
        List<Card> expected1 = List.of( new Card(CardSuit.HEARTS, CardRank.TWO),
                new Card(CardSuit.SPADES, CardRank.ACE),
                new Card(CardSuit.DIAMONDS, CardRank.THREE));
        Assertions.assertTrue(straightTest1.getCards().containsAll(expected1));
    }

    @Test
    void getHighestCard() {
        Card expectedCard = new Card(CardSuit.HEARTS, CardRank.TWO);
        System.out.println(straightTest1.getHighestCard());
        Assertions.assertEquals(expectedCard, straightTest1.getHighestCard());
    }
}