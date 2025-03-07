package model.hands;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FlushTest {

    private Flush flushTest1;

    @BeforeEach
    void setUp() {
        // [♥A, ♥J, ♥9]
        List<Card> flush1 = List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.JACK),
                new Card(CardSuit.HEARTS, CardRank.NINE));
        System.out.println(flush1);
        this.flushTest1 = new Flush(flush1);
    }

    @Test
    void getHandName() {
        Assertions.assertEquals(HandRank.FLUSH, flushTest1.getHandName());
    }

    @Test
    void getHandValue() {
        Assertions.assertEquals(4, flushTest1.getHandValue());
    }

    @Test
    void getMainCards() {
        List<Card> expected1 =List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.JACK),
                new Card(CardSuit.HEARTS, CardRank.NINE));

        Assertions.assertEquals(expected1,flushTest1.getMainCards());
    }

    @Test
    void getFlushSuit() {
        Assertions.assertEquals(CardSuit.HEARTS, flushTest1.getFlushSuit());
    }
}