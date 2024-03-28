package gui.start;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.button.GameImageButton;
import gui.button.GameTextButton;

public class MapSettingPanel extends JPanel {
    private StartPanel parentPanel;
    private int numberOfPlayers;
    private int numberOfWonRounds;
    private String pathOfMap;

    public MapSettingPanel(StartPanel parentPanel) {
        setLayout(new GridBagLayout());
        this.parentPanel = parentPanel;
        setBackground(new Color(0, 0, 0, 0));

        int smallButtonWidth = 60;
        int bigButtonSize = 2 * smallButtonWidth;
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel transparentPanel = new JPanel();
        transparentPanel.setBackground(new Color(0, 0, 0, 0));
        transparentPanel.setPreferredSize(new Dimension(1, 100));
        add(transparentPanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("Number of players"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(createPlayerButton("2", smallButtonWidth, 2), gbc);

        gbc.gridx = 2;
        add(createPlayerButton("3", smallButtonWidth, 3), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("Number of won rounds"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createRoundsButton("1", smallButtonWidth, 1), gbc);

        gbc.gridx = 2;
        add(createRoundsButton("2", smallButtonWidth, 2), gbc);

        gbc.gridx = 3;
        add(createRoundsButton("5", smallButtonWidth, 5), gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        add(createMapButton("src/data/picture/box.png", bigButtonSize, bigButtonSize), gbc);

        gbc.gridx = 2;
        add(createMapButton("src/data/picture/box.png", bigButtonSize, bigButtonSize), gbc);

        gbc.gridx = 3;
        add(createMapButton("src/data/picture/box.png", bigButtonSize, bigButtonSize), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(createBackButton("Back", bigButtonSize), gbc);

        gbc.gridx = 2;
        add(createStartButton("Start Game", bigButtonSize), gbc);
        

    }

    private JLabel createTitle(String title) {
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        label.setForeground(Color.BLACK);

        return label;
    }

    private JButton createPlayerButton(String text, int width, int players) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfPlayers = players;
            }
        });
    }

    private JButton createRoundsButton(String text, int width, int wonRounds) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfWonRounds = wonRounds;
            }
        });
    }

    private JButton createMapButton(String imagePath, int width, int height) {
        return new GameImageButton(imagePath, width, height, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathOfMap = imagePath;
            }
        });
    }

    private JButton createBackButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.goToStartGamePanel();
            }
        });
    }

    private JButton createStartButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.getWindow().startGame();
            }
        });
    }

}
