package controller;

import model.CardVO;
import model.Player;

import java.util.List;

public interface ClientControllerListener {
    void onPlayerListUpdated(List<Player> playerList);
    void onCardVOUpdated(List<CardVO> cardVOList);
}
