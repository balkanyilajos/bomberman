package hu.elte.fi.szofttech.bomberman.gui.dialog;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hu.elte.fi.szofttech.bomberman.gui.button.GameMenuButton;
import hu.elte.fi.szofttech.bomberman.gui.start.StartWindow;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuDialog extends JPanel {
    private JFrame window;

    public MenuDialog(JFrame window) {
        setLayout(new GridBagLayout());
        this.window = window;

        int buttonWidth = 300;
        GameMenuButton back = createBackButton("Back to Game", buttonWidth);
        GameMenuButton quitGame = createQuitGameButton("Quit Game", buttonWidth);
        GameMenuButton quitToTitle = createQuitToTitle("Quit to Title", buttonWidth);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(back, gbc);

        gbc.gridy = 1;
        add(quitGame, gbc);

        gbc.gridy = 2;
        add(quitToTitle, gbc);

        setVisible(false);
        setBackground(new Color(128, 128, 128, 150));
    }

    private GameMenuButton createBackButton(String text, int width) {
        return new GameMenuButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private GameMenuButton createQuitGameButton(String text, int width) {
        return new GameMenuButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private GameMenuButton createQuitToTitle(String text, int width) {
        return new GameMenuButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                StartWindow startWindow = new StartWindow();
                startWindow.setVisible(true);
            }
        });
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    // super.paintComponent(g);
    // g.setColor(Color.ORANGE.darker());
    // g.drawRect(0, 0, 300, 300);
    // }
}