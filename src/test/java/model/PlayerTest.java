package model;

import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import model.hands.StraightFlush;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player1;
    private Player player2;
    private Player player3;
    private List<Card> cards1;
    private Deck deck;


    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.initDeck();
        System.out.println("Original " + deck);

        cards1 = new ArrayList<>(List.of(
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN),
                new Card(CardSuit.CLUBS, CardRank.SEVEN),
                new Card(CardSuit.SPADES, CardRank.SEVEN),
                new Card(CardSuit.SPADES, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.JACK)

        ));

        player1 = new Player("player1");

        player2 = new Player("player2");

        player3 = new Player("player3");

    }

    @Test
    void setHand() {
        player1.setHand(cards1);
        System.out.println("player1:" + cards1);
        Assertions.assertEquals(cards1, player1.getHand());
    }

    @Test
    void setHandRank() {
        List<Card> cards = new ArrayList<>(List.of(
                new Card(CardSuit.HEARTS, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.QUEEN),
                new Card(CardSuit.HEARTS, CardRank.KING)
        ));
        StraightFlush straightFlush = new StraightFlush(cards);
        player1.setHandRank(straightFlush);
        Assertions.assertEquals(straightFlush, player1.getHandRank());
        //System.out.println(player1.getHandRank());
    }

    @Test
    void getCardsFromDeckTop() {
        List<Card> expected = new ArrayList<>(List.of(
                new Card(CardSuit.SPADES, CardRank.THREE),
                new Card(CardSuit.SPADES, CardRank.FOUR),
                new Card(CardSuit.SPADES, CardRank.FIVE)
        ));

        player2.getCardsFromDeckTop(deck, 3);
        Assertions.assertEquals(expected, player2.getHand());
        System.out.println(player2.getHand());
//        System.out.println(deck);
    }

    @Test
    void selectCards() {
        player3.setHand(cards1);
        System.out.println(player3.getHand());
        // simulation 1
        String simulatedInput1 = "0 1 2\n";
        Scanner scanner1 = new Scanner(
                new ByteArrayInputStream(simulatedInput1.getBytes()));
//        List<Card> selectedCards1 = player3.selectCards(3, scanner1);
        List<Card> expected1 = new ArrayList<>(List.of(
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN),
                new Card(CardSuit.CLUBS, CardRank.SEVEN),
                new Card(CardSuit.SPADES, CardRank.SEVEN)
        ));

        // simulation 2
        String simulatedInput2 = "0 3 4\n";
        Scanner scanner2 = new Scanner(
                new ByteArrayInputStream(simulatedInput2.getBytes()));
        List<Card> selectedCards2 = player3.selectCards(3, scanner2);
        List<Card> expected2 = new ArrayList<>(List.of(
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN),
                new Card(CardSuit.SPADES, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.JACK)
        ));

        System.out.println(selectedCards2);
        Assertions.assertEquals(expected2, selectedCards2);

    }

    @Test
    void playCards() {
    }
}