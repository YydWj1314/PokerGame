package util;

import model.CardVO;

import javax.swing.*;

public class ViewUtil {
    public static void move(CardVO cardVO, int x, int y) {
        cardVO.setLocation(x, y);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
