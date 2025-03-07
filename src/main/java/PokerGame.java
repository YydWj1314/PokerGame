import controller.GameController;
import model.Card;
import model.Deck;
import model.Player;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import utils.HandEvaluator;

import java.util.*;

public class PokerGame {
    public static void main(String[] args) {
        /*
        * 尝试执行：
        * 初始化：3*player + 1*deck
        * 洗牌
        * 发牌
        * 选牌
        * 打牌
        * map 收集
        * */

        // 1. Objects Initialization
        Deck deck = new Deck();
        deck.initDeck();

        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Player player3 = new Player("player3");

        List<Player> players = new ArrayList<>(List.of(
                player1, player2, player3
        ));

        GameController gameController = new GameController(deck, players, true);
        System.out.println("After initialization:");
        System.out.println(gameController);


        // 2. Init and shuffle the deck
        gameController.getDeck().shuffle();
        System.out.println("After shuffling:");
        System.out.println(gameController);


        // 3. Dealing 5 cards to every player
        gameController.dealCards(5);
        System.out.println("After Dealing 5 cards:");
        System.out.println(gameController);


        // 4. Players selecting and playing cards
        Map<Player, List<Card>> cardsMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        cardsMap = gameController.selectAndPlayCards(scanner);
        System.out.println("After playing cards:");
        System.out.println(cardsMap);
    }
}