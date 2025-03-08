package utils;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Revered order logic
 * handValue2 > handValue1（o2 比 o1 大）→ compareTo() 返回 正数
 * handValue2 < handValue1（o1 比 o2 大）→ compareTo() 返回 负数
 *
 */
class RankComparatorTest {
    private RankComparator rankComparator;

    @BeforeEach
    void setUp() {
        rankComparator = new RankComparator();
    }


    @Test
    void testDifferentHandRank() {
        // 牌型不同：Straight Flush vs High Card
        //[♠K, ♠Q, ♠J]
        List<Card> straightFlushCards = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.SPADES, CardRank.QUEEN),
                new Card(CardSuit.SPADES, CardRank.JACK)
        );
        //[♥A, ♠9, ♦3]
        List<Card> highCardCards = Arrays.asList(
                new Card(CardSuit.HEARTS, CardRank.ACE),
                new Card(CardSuit.SPADES, CardRank.NINE),
                new Card(CardSuit.DIAMONDS, CardRank.THREE)
        );
        int result = rankComparator.compare(straightFlushCards, highCardCards);
        assertTrue(result < 0, "o2 < o1, return -1");
    }

    @Test
    void testSameHandRank_PairComparison() {
        // 相同的牌型：对子 vs 对子
        // [♠J, ♦J, ♣9]
        List<Card> pair1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.CLUBS, CardRank.NINE)
        );

        // [♠Q, ♦Q, ♣8]
        List<Card> pair2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.QUEEN),
                new Card(CardSuit.DIAMONDS, CardRank.QUEEN),
                new Card(CardSuit.CLUBS, CardRank.EIGHT)
        );

        int result = rankComparator.compare(pair1, pair2);
        assertTrue(result > 0, "o2 > o1 return 1");
    }

    @Test
    void testAllSameHandRank_PairComparison(){
        // 点数完全相同的牌型：对子 vs 对子
        // [♣Q, ♥Q, ♥9]
        List<Card> pair1 = Arrays.asList(
                new Card(CardSuit.CLUBS, CardRank.QUEEN),
                new Card(CardSuit.HEARTS, CardRank.QUEEN),
                new Card(CardSuit.HEARTS, CardRank.NINE)
        );
        System.out.println(pair1);

        // [♠Q, ♦Q, ♣9]
        List<Card> pair2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.QUEEN),
                new Card(CardSuit.DIAMONDS, CardRank.QUEEN),
                new Card(CardSuit.CLUBS, CardRank.NINE)
        );
        System.out.println(pair2);
        int result = rankComparator.compare(pair1, pair2);
        assertEquals(0, result);
    }

    @Test
    void testSameHandRank_HighCardComparison() {
        // 相同的牌型：High Card vs High Card
        // [♠A, ♦10, ♣8]
        List<Card> highCard1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.ACE),
                new Card(CardSuit.DIAMONDS, CardRank.TEN),
                new Card(CardSuit.CLUBS, CardRank.EIGHT)
        );
        System.out.println( highCard1);
        // [♠K, ♦9, ♣7]
        List<Card> highCard2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.DIAMONDS, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.SEVEN)
        );
        System.out.println( highCard2);
        int result = rankComparator.compare(highCard1, highCard2);
        assertTrue(result < 0, "o2 < o1 return -1");
    }

    @Test
    void testSameHandRank_ExactSameCards() {
        // 完全相同的手牌
        List<Card> hand1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.SEVEN)
        );

        List<Card> hand2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.SEVEN)
        );

        int result = rankComparator.compare(hand1, hand2);
        assertEquals(0, result, "return 0");
    }


}