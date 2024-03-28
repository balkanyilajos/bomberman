package gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameTextButton extends JButton {

    public GameTextButton(String text, int width, int height, ActionListener actionListener) {
        this(text, actionListener);
        setPreferredSize(new Dimension(width, height));
    }

    public GameTextButton(String text, int width, ActionListener actionListener) {
        this(text, actionListener);
        setPreferredSize(new Dimension(width, getPreferredSize().height));
    }

    public GameTextButton(String text, ActionListener actionListener) {
        addActionListener(actionListener);
        setText(text);
        setFocusPainted(false);
        setFocusable(false);
        setBackground(Color.WHITE);
        setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        setForeground(Color.BLACK);
        setMargin(new Insets(5, 5, 5, 5));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createLineBorder(Color.WHITE, 10)));
    }
}
