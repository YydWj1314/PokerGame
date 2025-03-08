package controller;

import model.Card;
import model.Deck;
import model.Player;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameController;
    private Deck deck;
    private Player player1;
    private Player player2;
    private Player player3;


    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.initDeck();
        player1 = new Player("player1");
        player2 = new Player("player2");
        player3 = new Player("player3");
        List<Player> players = new ArrayList<>(List.of(
                player1, player2, player3
        ));

        gameController = new GameController(deck, players, true);
    }

    @Test
    void evaluateDiffRank1(){
        // 1. StraightFlush, ThreeAKind, Flush
        // [♠Q, ♠K, ♠A]
        List<Card> straightFlush1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.QUEEN),
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.SPADES, CardRank.ACE)
        );
        // [♠J, ♦J, ♣J]
        List<Card> threeOfAKind1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.CLUBS, CardRank.JACK)
        );
        // [♦5, ♦J, ♦7]
        List<Card> flush1 = Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        );

        Map<Player, List<Card>> testMap1 = new HashMap<>();
        testMap1.put(player3, flush1);
        testMap1.put(player2, threeOfAKind1);
        testMap1.put(player1, straightFlush1);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testMap1);

        System.out.println("After Evaluating --> LinkedHashMap");
        LinkedHashMap<Player, List<Card>> result1 = gameController.evaluatePlayedCards(testMap1);
        System.out.println(result1);
    }

    @Test
    void evaluateDiffRank2(){
        // 2. ThreeKind, FLush, Straight
        // [♠J, ♦J, ♣3]
        List<Card> threeOfAKind1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.CLUBS, CardRank.JACK)
        );

        // [♦5, ♦J, ♦7]
        List<Card> flush1 = Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        );

        // [♦5, ♦J, ♦7]
        List<Card> straight1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.SIX),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        );

        Map<Player, List<Card>> testMap3 = new HashMap<>();
        testMap3.put(player3, straight1);
        testMap3.put(player2, flush1);
        testMap3.put(player1, threeOfAKind1);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testMap3);

        System.out.println("After Evaluating --> LinkedHashMap");
        LinkedHashMap<Player, List<Card>> result2 = gameController.evaluatePlayedCards(testMap3);
        System.out.println(result2);
    }

    @Test
    void evaluateDiffRank3(){
        // 2. FLush, Straight，Pair
        // [♦5, ♦J, ♦7]
        List<Card> flush1 = Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        );
        // [♠6, ♦5, ♦7]
        List<Card> straight1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.SIX),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        );
        // [♠J, ♦J, ♣3]
        List<Card> pair1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.CLUBS, CardRank.THREE)
        );

        Map<Player, List<Card>> testMap3 = new HashMap<>();
        testMap3.put(player3, pair1);
        testMap3.put(player1, flush1);
        testMap3.put(player2, straight1);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testMap3);

        System.out.println("After Evaluating --> LinkedHashMap");
        LinkedHashMap<Player, List<Card>> result2 = gameController.evaluatePlayedCards(testMap3);
        System.out.println(result2);
    }

    @Test
    void evaluateDiffRank4(){
        // 2. Straight, Pair, HighCard
        // [♠6, ♦5, ♦7]
        List<Card> straight1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.SIX),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN)
        );
        // [♠J, ♦J, ♣3]
        List<Card> pair1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.DIAMONDS, CardRank.JACK),
                new Card(CardSuit.CLUBS, CardRank.THREE)
        );

        // [♦5, ♦J, ♦7]
        List<Card> highCard1 = Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.SPADES, CardRank.JACK),
                new Card(CardSuit.HEARTS, CardRank.SEVEN)
        );

        Map<Player, List<Card>> testMap4 = new HashMap<>();
        testMap4.put(player3, highCard1);
        testMap4.put(player2, pair1);
        testMap4.put(player1, straight1);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testMap4);

        System.out.println("After Evaluating --> LinkedHashMap");
        LinkedHashMap<Player, List<Card>> result2 = gameController.evaluatePlayedCards(testMap4);
        System.out.println(result2);
    }

    @Test
    void evaluateSameRankSF(){
        // 1. Straight flush
        // [♠2, ♠3, ♠4]
        List<Card> straightFlush2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.TWO),
                new Card(CardSuit.SPADES, CardRank.THREE),
                new Card(CardSuit.SPADES, CardRank.FOUR)
        );
        // [♠Q, ♠K, ♠A]
        List<Card> straightFlush3 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.QUEEN),
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.SPADES, CardRank.ACE)
        );
        // [♠7, ♠8, ♠9]
        List<Card> straightFlush4= Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.SEVEN),
                new Card(CardSuit.SPADES, CardRank.EIGHT),
                new Card(CardSuit.SPADES, CardRank.NINE)
        );

        Map<Player, List<Card>> testSameMap1 = new HashMap<>();
        testSameMap1.put(player3, straightFlush2);
        testSameMap1.put(player2, straightFlush3);
        testSameMap1.put(player1, straightFlush4);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSameMap1);

        System.out.println("After Evaluating --> LinkedHashMap");
        LinkedHashMap<Player, List<Card>> result1 = gameController.evaluatePlayedCards(testSameMap1);
        System.out.println(result1);
    }

    @Test
    void evaluateSameRankThreeOfAKind() {
        // 1. Three of a Kind（三条）
        // [♠5, ♦5, ♣5] - Player 1
        List<Card> threeOfAKind1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.CLUBS, CardRank.FIVE)
        );

        // [♠9, ♦9, ♣9] - Player 2
        List<Card> threeOfAKind2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.NINE),
                new Card(CardSuit.DIAMONDS, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.NINE)
        );

        // [♥K, ♠K, ♦K] - Player 3
        List<Card> threeOfAKind3 = Arrays.asList(
                new Card(CardSuit.HEARTS, CardRank.KING),
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.DIAMONDS, CardRank.KING)
        );

        Map<Player, List<Card>> testSameThreeOfAKindMap = new HashMap<>();
        testSameThreeOfAKindMap.put(player3, threeOfAKind1);
        testSameThreeOfAKindMap.put(player2, threeOfAKind2);
        testSameThreeOfAKindMap.put(player1, threeOfAKind3);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSameThreeOfAKindMap);

        System.out.println("After Evaluating --> LinkedHashMap:");
        LinkedHashMap<Player, List<Card>> result = gameController.evaluatePlayedCards(testSameThreeOfAKindMap);
        System.out.println(result);
    }

    @Test
    void evaluateSameRankFlush() {
        // 1. Flush
        // [♠2, ♠5, ♠9] - Player 1
        List<Card> flush1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.TWO),
                new Card(CardSuit.SPADES, CardRank.FIVE),
                new Card(CardSuit.SPADES, CardRank.NINE)
        );

        // [♦4, ♦7, ♦K] - Player 2
        List<Card> flush2 = Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardRank.FOUR),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN),
                new Card(CardSuit.DIAMONDS, CardRank.KING)
        );

        // [♥3, ♥8, ♥J] - Player 3
        List<Card> flush3 = Arrays.asList(
                new Card(CardSuit.HEARTS, CardRank.THREE),
                new Card(CardSuit.HEARTS, CardRank.EIGHT),
                new Card(CardSuit.HEARTS, CardRank.JACK)
        );

        Map<Player, List<Card>> testSameFlushMap = new HashMap<>();
        testSameFlushMap.put(player3, flush1);
        testSameFlushMap.put(player2, flush2);
        testSameFlushMap.put(player1, flush3);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSameFlushMap);

        System.out.println("After Evaluating --> LinkedHashMap:");
        LinkedHashMap<Player, List<Card>> result = gameController.evaluatePlayedCards(testSameFlushMap);
        System.out.println(result);
    }

    @Test
    void evaluateSameRankStraight() {
        // 1. Straight（顺子）
        // [♠2, ♦3, ♣4] - Player 1
        List<Card> straight1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.TWO),
                new Card(CardSuit.DIAMONDS, CardRank.THREE),
                new Card(CardSuit.CLUBS, CardRank.FOUR)
        );

        // [♠7, ♦8, ♣9] - Player 2
        List<Card> straight2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.SEVEN),
                new Card(CardSuit.DIAMONDS, CardRank.EIGHT),
                new Card(CardSuit.CLUBS, CardRank.NINE)
        );

        // [♥Q, ♠K, ♦A] - Player 3
        List<Card> straight3 = Arrays.asList(
                new Card(CardSuit.HEARTS, CardRank.QUEEN),
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.DIAMONDS, CardRank.ACE)
        );

        Map<Player, List<Card>> testSameStraightMap = new HashMap<>();
        testSameStraightMap.put(player3, straight1);
        testSameStraightMap.put(player2, straight2);
        testSameStraightMap.put(player1, straight3);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSameStraightMap);

        System.out.println("After Evaluating --> LinkedHashMap:");
        LinkedHashMap<Player, List<Card>> result = gameController.evaluatePlayedCards(testSameStraightMap);
        System.out.println(result);
    }

    @Test
    void evaluateSameRankPair() {
        // 1. Pair（对子）
        // [♠5, ♦5, ♣9] - Player 1
        List<Card> pair1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.CLUBS, CardRank.NINE) // Kickers 9
        );

        // [♠9, ♦9, ♣3] - Player 2
        List<Card> pair2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.NINE),
                new Card(CardSuit.DIAMONDS, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.THREE) // Kickers 3
        );

        // [♥K, ♠K, ♦A] - Player 3
        List<Card> pair3 = Arrays.asList(
                new Card(CardSuit.HEARTS, CardRank.KING),
                new Card(CardSuit.SPADES, CardRank.KING),
                new Card(CardSuit.DIAMONDS, CardRank.ACE) // Kickers A
        );

        Map<Player, List<Card>> testSamePairMap = new HashMap<>();
        testSamePairMap.put(player3, pair1);
        testSamePairMap.put(player2, pair2);
        testSamePairMap.put(player1, pair3);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSamePairMap);

        System.out.println("After Evaluating --> LinkedHashMap:");
        LinkedHashMap<Player, List<Card>> result = gameController.evaluatePlayedCards(testSamePairMap);
        System.out.println(result);
    }

    @Test
    void evaluateSameRankPair_NonPair() {
        // 1. Pair（对子）
        // [♠5, ♦5, ♣9] - Player 1
        List<Card> pair1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.CLUBS, CardRank.NINE) // Kickers 9
        );

        // [♠2, ♦2, ♣3] - Player 2
        List<Card> pair2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.TWO),
                new Card(CardSuit.DIAMONDS, CardRank.TWO),
                new Card(CardSuit.CLUBS, CardRank.THREE) // Kickers 3
        );

        // [♠5, ♦5, ♣A] - Player 3
        List<Card> pair3 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.ACE) // Kickers A
        );

        Map<Player, List<Card>> testSamePairMap = new HashMap<>();
        testSamePairMap.put(player3, pair1);
        testSamePairMap.put(player2, pair2);
        testSamePairMap.put(player1, pair3);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSamePairMap);

        System.out.println("After Evaluating --> LinkedHashMap:");
        LinkedHashMap<Player, List<Card>> result = gameController.evaluatePlayedCards(testSamePairMap);
        System.out.println(result);
    }

    @Test
    void evaluateSameRankHighCard() {
        // 1. High Card（高牌）
        // [♠5, ♦9, ♣J] - Player 1 (最高牌 J)
        List<Card> highCard1 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.FIVE),
                new Card(CardSuit.DIAMONDS, CardRank.NINE),
                new Card(CardSuit.CLUBS, CardRank.JACK) // 最高 J
        );

        // [♠3, ♦7, ♣Q] - Player 2 (最高牌 Q)
        List<Card> highCard2 = Arrays.asList(
                new Card(CardSuit.SPADES, CardRank.THREE),
                new Card(CardSuit.DIAMONDS, CardRank.SEVEN),
                new Card(CardSuit.CLUBS, CardRank.KING) // 最高 k
        );

        // [♥6, ♠10, ♦K] - Player 3 (最高牌 K)
        List<Card> highCard3 = Arrays.asList(
                new Card(CardSuit.HEARTS, CardRank.SIX),
                new Card(CardSuit.SPADES, CardRank.TEN),
                new Card(CardSuit.DIAMONDS, CardRank.KING) // 最高 K
        );

        Map<Player, List<Card>> testSameHighCardMap = new HashMap<>();
        testSameHighCardMap.put(player3, highCard1);
        testSameHighCardMap.put(player2, highCard2);
        testSameHighCardMap.put(player1, highCard3);

        System.out.println("Before Evaluating --> HashMap:");
        System.out.println(testSameHighCardMap);

        System.out.println("After Evaluating --> LinkedHashMap:");
        LinkedHashMap<Player, List<Card>> result = gameController.evaluatePlayedCards(testSameHighCardMap);
        System.out.println(result);
    }

}