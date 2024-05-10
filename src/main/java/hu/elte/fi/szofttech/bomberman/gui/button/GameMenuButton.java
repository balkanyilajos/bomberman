package hu.elte.fi.szofttech.bomberman.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameMenuButton extends JButton {
    private Color activeColor = new Color(33, 33, 33);
    private Color standardColor = new Color(87, 87, 87);

    public GameMenuButton(String text, int width, ActionListener actionListener) {
        this(text, actionListener);
        setPreferredSize(new Dimension(width, getPreferredSize().height));
    }

    public GameMenuButton(String text, ActionListener actionListener) {
        setText(text);
        setFocusPainted(false);
        setFocusable(false);
        setBackground(standardColor);
        setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        setForeground(new Color(232, 232, 232));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        addActionListener(actionListener);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(activeColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(standardColor);
            }
        });
    }

}
