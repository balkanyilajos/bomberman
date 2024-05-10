package hu.elte.fi.szofttech.bomberman.gui.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hu.elte.fi.szofttech.bomberman.gui.button.GameImageButton;
import hu.elte.fi.szofttech.bomberman.gui.button.GameTextButton;
import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class MapSettingPanel extends JPanel {
    private StartPanel parentPanel;
    private Integer playerNumber;
    private Integer wonRoundNumber;
    private String mapPath;

    public MapSettingPanel(StartPanel parentPanel) {
        setLayout(new GridBagLayout());
        this.parentPanel = parentPanel;
        setBackground(new Color(0, 0, 0, 0));

        int smallButtonWidth = 60;
        int bigButtonSize = 2 * smallButtonWidth;

        GameTextButton[] playerNumberGroup = {
                createPlayerRadioButton("2", smallButtonWidth, 2),
                createPlayerRadioButton("3", smallButtonWidth, 3)
        };

        GameTextButton[] roundGroup = {
                createRoundsRadioButton("1", smallButtonWidth, 1),
                createRoundsRadioButton("2", smallButtonWidth, 2),
                createRoundsRadioButton("5", smallButtonWidth, 5)
        };

        GameImageButton[] mapGroup = {
                createMapRadioButton(GeneralPath.getPath() + "/picture/wall.png", GameModel.MAPS_PATH[0], bigButtonSize,
                        bigButtonSize),
                createMapRadioButton(GeneralPath.getPath() + "/picture/wall.png", GameModel.MAPS_PATH[1], bigButtonSize,
                        bigButtonSize),
                createMapRadioButton(GeneralPath.getPath() + "/picture/wall.png", GameModel.MAPS_PATH[2], bigButtonSize,
                        bigButtonSize)
        };

        for (GameTextButton button : playerNumberGroup)
            button.setButtonGroup(playerNumberGroup);
        for (GameTextButton button : roundGroup)
            button.setButtonGroup(roundGroup);
        for (GameImageButton button : mapGroup)
            button.setButtonGroup(mapGroup);

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
        add(playerNumberGroup[0], gbc);

        gbc.gridx = 2;
        add(playerNumberGroup[1], gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("Number of won rounds"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(roundGroup[0], gbc);

        gbc.gridx = 2;
        add(roundGroup[1], gbc);

        gbc.gridx = 3;
        add(roundGroup[2], gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        add(mapGroup[0], gbc);

        gbc.gridx = 2;
        add(mapGroup[1], gbc);

        gbc.gridx = 3;
        add(mapGroup[2], gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(createBackButton("Back", bigButtonSize), gbc);

        gbc.gridx = 2;
        add(createStartButton("Start Game", bigButtonSize), gbc);
        gbc.gridx = 3;
    }

    private JLabel createTitle(String title) {
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        label.setForeground(Color.BLACK);

        return label;
    }

    private GameTextButton createPlayerRadioButton(String text, int width, int players) {
        return new GameTextButton(text, width, true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerNumber = players;
            }
        });
    }

    private GameTextButton createRoundsRadioButton(String text, int width, int wonRounds) {
        return new GameTextButton(text, width, true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wonRoundNumber = wonRounds;
            }
        });
    }

    private GameImageButton createMapRadioButton(String imagePath, String mapPath, int width, int height) {
        return new GameImageButton(imagePath, width, height, true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapSettingPanel.this.mapPath = mapPath;
            }
        });
    }

    private GameTextButton createBackButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.goToStartGamePanel();
            }
        });
    }

    private GameTextButton createStartButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerNumber != null && wonRoundNumber != null && mapPath != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(mapPath);
                    if (playerNumber == 2) {
                        sb.append(".txt");
                    } else {
                        sb.append("p3.txt");
                    }
                    parentPanel.getWindow().startGame(sb.toString(), playerNumber, wonRoundNumber);
                }
            }
        });
    }

}
