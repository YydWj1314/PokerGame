package controller;

import model.Card;
import model.Deck;
import model.Hand;
import model.Player;
import model.enumuration.ExceptionMessage;
import model.enumuration.HandRank;
import model.exception.EmptyDeckException;
import model.exception.InvalidCardsCountException;
import model.exception.NoPlayerException;
import utils.HandEvaluator;
import utils.RankComparator;

import java.util.*;
import java.util.stream.Collectors;

public class GameController {
    private Deck deck;

    private final List<Player> players;

    private boolean isGameActive;

    /**
     * Constructor of game controller
     *
     * @param deck
     * @param players
     * @param isGameActive
     */
    public GameController(Deck deck, List<Player> players, boolean isGameActive) {
        this.deck = deck;
        this.players = players;
        this.isGameActive = isGameActive;
    }

    public void startGame(Deck deck, List<Player> players) {
        Scanner scanner = new Scanner(System.in);
        this.deck.shuffle();
        dealCards(5);
        Map<Player, List<Card>> playedCardsMap = selectAndPlayCards(scanner);
        evaluatePlayedCards(playedCardsMap);
//        determinePlayersRank(players);
//        settleScores();
    }


    /**
     * Dealing n cards to each player
     *
     * @param n deal n cards
     */
    public void dealCards(int n) {
        if (this.deck == null || this.deck.countCards() == 0) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_DECK.getMessage());
        }
        // deal n cards to each player
        for (Player player : this.players) {
            player.getCardsFromDeckTop(deck, n);
        }
    }

    /**
     * Players select and play cards
     */
    public Map<Player, List<Card>> selectAndPlayCards(Scanner scanner) {
        // Using a map to collect all cards played by each player: {player1: cards1, player2: cards2 ...}
        Map<Player, List<Card>> playedCards = new HashMap<>();

        // Each Player plays cards
        for (Player player : this.players) {
            System.out.println(player.getUid() + " Input 3 indices, split by space:");
            List<Card> selectedCards = player.selectCards(3, scanner);
            if (selectedCards == null || selectedCards.isEmpty()) {
                throw new InvalidCardsCountException(ExceptionMessage.INVALID_CARDS_COUNT.getMessage());
            }
            player.playCards(selectedCards);
            playedCards.put(player, selectedCards);
        }

        // return the map of cards played
        return playedCards;
    }


    /**
     * Evaluating and setting the rank of player's hand
     *
     * @param palyedCardsMap
     */
    public LinkedHashMap<Player, List<Card>> evaluatePlayedCards(Map<Player, List<Card>> palyedCardsMap) {
        //{uid:player2, hand:[♥3, ♠4], handRank:null} = [♦Q, ♣A, ♠K],
        //{uid:player3, hand:[♦K, ♦2], handRank:null} = [♦3, ♥Q, ♠6],
        //{uid:player1, hand:[♣7, ♠A], handRank:null} = [♣3, ♦5, ♥10]}

        // 1. 将 playedCardsMap --> 根据 value 的 rank 将 map 进行排序，rank 高的排在前面
        // 默认情况下，hashMap 是无序的，所以需要转换为Linked hashMap

        /*
        * palyedCardsMap.entrySet() --> Set<Map.Entry<Player, List<Card>>>
        * o1 / o2 --> Map.Entry<Player, List<Card>>
        * o1.getValue --> List<Card>
        *
        * */
        LinkedHashMap<Player, List<Card>> playerRankMap = palyedCardsMap.entrySet()
                .stream()
                .sorted((e1, e2) -> new RankComparator().compare(e1.getValue(), e2.getValue()))  // reserved order
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        return playerRankMap;
    }

    private void determinePlayersRank(List<Player> players) {
        // TODO
    }


    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "GameController:{" +
                " deck=" + deck + "\n" +
                players +
                "}";
    }
}
