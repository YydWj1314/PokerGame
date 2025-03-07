import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.HandEvaluator;

import java.util.ArrayList;

class handEvaluatorTest {

    @Test
    void sortCards() {
    }

    @Test
    void handEvaluator() {
    }

    @Test
    void isStraight() {
        ArrayList<Card> testStraight1 = new ArrayList<>();
        ArrayList<Card> testStraight2 = new ArrayList<>();
        ArrayList<Card> testStraight3 = new ArrayList<>();

        // [5, 3, 8]
        testStraight1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testStraight1.add(new Card(CardSuit.CLUBS, CardRank.THREE));
        testStraight1.add(new Card(CardSuit.DIAMONDS, CardRank.EIGHT));
        System.out.println(testStraight1);
        Assertions.assertFalse(HandEvaluator.isStraight(testStraight1));

        // [A, 2, 3]
        testStraight2.add(new Card(CardSuit.CLUBS, CardRank.ACE));
        testStraight2.add(new Card(CardSuit.CLUBS, CardRank.TWO));
        testStraight2.add(new Card(CardSuit.HEARTS, CardRank.THREE));
        System.out.println(testStraight2);
        Assertions.assertTrue(HandEvaluator.isStraight(testStraight2));

        // [A, 2, 3]
        testStraight3.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testStraight3.add(new Card(CardSuit.CLUBS, CardRank.SIX));
        testStraight3.add(new Card(CardSuit.HEARTS, CardRank.SEVEN));
        System.out.println(testStraight3);
        Assertions.assertTrue(HandEvaluator.isStraight(testStraight3));
    }

    @Test
    void isFlush() {
        ArrayList<Card> testFlush1 = new ArrayList<>();
        ArrayList<Card> testFlush2 = new ArrayList<>();
        // [♣5, ♣6, ♣J]
        testFlush1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testFlush1.add(new Card(CardSuit.CLUBS, CardRank.SIX));
        testFlush1.add(new Card(CardSuit.CLUBS, CardRank.JACK));
        System.out.println(testFlush1);
        Assertions.assertTrue(HandEvaluator.isFlush(testFlush1));
        // [♦5, ♣6, ♣J]
        testFlush2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testFlush2.add(new Card(CardSuit.CLUBS, CardRank.SIX));
        testFlush2.add(new Card(CardSuit.CLUBS, CardRank.JACK));
        System.out.println(testFlush2);
        Assertions.assertFalse(HandEvaluator.isFlush(testFlush2));
    }

    @Test
    void isStraightFlush() {
        // [♣5, ♣6, ♣J]
        ArrayList<Card> testStraightFlush1 = new ArrayList<>();
        testStraightFlush1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testStraightFlush1.add(new Card(CardSuit.CLUBS, CardRank.SIX));
        testStraightFlush1.add(new Card(CardSuit.CLUBS, CardRank.JACK));
        System.out.println(testStraightFlush1);
        Assertions.assertFalse(HandEvaluator.isStraightFlush(testStraightFlush1));

        // [♣5, ♣6, ♣7]
        ArrayList<Card> testStraightFlush2 = new ArrayList<>();
        testStraightFlush2.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testStraightFlush2.add(new Card(CardSuit.CLUBS, CardRank.SIX));
        testStraightFlush2.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
        System.out.println(testStraightFlush2);
        Assertions.assertTrue(HandEvaluator.isStraightFlush(testStraightFlush2));
    }

    @Test
    void isThreeOfOneKind() {
        // [♣5, ♦5, ♠5]
        ArrayList<Card> testThreeOfOneKind1 = new ArrayList<>();
        testThreeOfOneKind1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testThreeOfOneKind1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testThreeOfOneKind1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
        System.out.println(testThreeOfOneKind1);
        Assertions.assertTrue(HandEvaluator.isThreeOfOneKind(testThreeOfOneKind1));

        // [♣7, ♦5, ♠5]
        ArrayList<Card> testThreeOfOneKind2 = new ArrayList<>();
        testThreeOfOneKind2.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
        testThreeOfOneKind2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testThreeOfOneKind2.add(new Card(CardSuit.SPADES, CardRank.FIVE));
        System.out.println(testThreeOfOneKind2);
        Assertions.assertFalse(HandEvaluator.isThreeOfOneKind(testThreeOfOneKind2));
    }

    @Test
    void isPair() {
        // [♣7, ♦5, ♠5]
        ArrayList<Card> testPair1 = new ArrayList<>();
        testPair1.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
        testPair1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testPair1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
        System.out.println(testPair1);
        Assertions.assertTrue(HandEvaluator.isPair(testPair1));

        // [♣5, ♦5, ♠5]
        ArrayList<Card> testPair2 = new ArrayList<>();
        testPair2.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testPair2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testPair2.add(new Card(CardSuit.SPADES, CardRank.FIVE));
        System.out.println(testPair2);
        Assertions.assertFalse(HandEvaluator.isPair(testPair2));
    }

    @Test
    void isHighCard() {
        // [♣5, ♦5, ♠5]
        ArrayList<Card> testHighCard1 = new ArrayList<>();
        testHighCard1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testHighCard1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testHighCard1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
        System.out.println(testHighCard1);
        Assertions.assertFalse(HandEvaluator.isHighCard(testHighCard1));

        //[♣4, ♦5, ♠6]
        ArrayList<Card> testHighCard2 = new ArrayList<>();
        testHighCard2.add(new Card(CardSuit.CLUBS, CardRank.FOUR));
        testHighCard2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testHighCard2.add(new Card(CardSuit.SPADES, CardRank.SIX));
        System.out.println(testHighCard2);
        Assertions.assertFalse(HandEvaluator.isHighCard(testHighCard2));

        // [♣5, ♦5, ♠6]
        ArrayList<Card> testHighCard3 = new ArrayList<>();
        testHighCard3.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
        testHighCard3.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testHighCard3.add(new Card(CardSuit.SPADES, CardRank.SIX));
        System.out.println(testHighCard3);
        Assertions.assertFalse(HandEvaluator.isHighCard(testHighCard3));

        // [♣J, ♦5, ♠6]
        ArrayList<Card> testHighCard4 = new ArrayList<>();
        testHighCard4.add(new Card(CardSuit.CLUBS, CardRank.JACK));
        testHighCard4.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        testHighCard4.add(new Card(CardSuit.SPADES, CardRank.SIX));
        System.out.println(testHighCard4);
        Assertions.assertTrue(HandEvaluator.isHighCard(testHighCard4));
    }
}