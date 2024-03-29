package gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameImageButton extends JButton {
    private Image background;
    private int width;
    private int height;

    public GameImageButton(String imagePath, int width, int height, ActionListener actionListener) {
        try {
            this.background = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e);
        }

        this.width = width;
        this.height = height;
        addActionListener(actionListener);
        setPreferredSize(new Dimension(width, height));
        setFocusPainted(false);
        setFocusable(false);
        setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        setMargin(new Insets(5, 5, 5, 5));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, width, height, null);
    }
}
