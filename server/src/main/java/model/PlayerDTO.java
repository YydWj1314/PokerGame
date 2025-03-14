package model;

import java.util.List;

public class PlayerDTO {
    private final int id;
    private final String name;
    private List<Card> hand;
    private Integer score;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Integer getScore() {
        return score;
    }

    public PlayerDTO(int id, String name, List<Card> hand, Integer score) {
        this.id = id;
        this.name = name;
        this.hand = hand;
        this.score = score;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hand=" + hand +
                ", score=" + score +
                '}';
    }
}
