package view;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private Image backgroundImage;

    public MyPanel() {
        backgroundImage = new ImageIcon(getClass().getResource("/bg1.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // check null
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            System.out.println("Image Unloaded, check the URL");
        }
    }
}
