package gui.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.button.GameImageButton;
import gui.button.GameTextButton;
import model.GameModel;

public class KeyboardSettingPanel extends JPanel
{
    private StartPanel parentPanel;
    
    public KeyboardSettingPanel(StartPanel parentPanel) {
        setLayout(new GridBagLayout());
        this.parentPanel = parentPanel;
        setBackground(new Color(0, 0, 0, 0));

        int smallButtonWidth = 60;
        int bigButtonSize = 2 * smallButtonWidth;

        GameTextButton[] ups = {
            createKeyButton("w", smallButtonWidth),
            createKeyButton("i", smallButtonWidth),
            createKeyButton("8", smallButtonWidth)
        };

        GameTextButton[] lefts = {
            createKeyButton("a", smallButtonWidth),
            createKeyButton("j", smallButtonWidth),
            createKeyButton("4", smallButtonWidth)
        };

        GameTextButton[] rights = {
            createKeyButton("d", smallButtonWidth),
            createKeyButton("l", smallButtonWidth),
            createKeyButton("6", smallButtonWidth)
        };

        GameTextButton[] downs = {
            createKeyButton("s", smallButtonWidth),
            createKeyButton("k", smallButtonWidth),
            createKeyButton("5", smallButtonWidth)
        };

        GameTextButton[] bomb = {
            createKeyButton("e", smallButtonWidth),
            createKeyButton("o", smallButtonWidth),
            createKeyButton("9", smallButtonWidth)
        };

        for(GameTextButton button : ups) button.setButtonGroup(ups);

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
        add(createTitle("UP"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(ups[0], gbc);

        gbc.gridx = 2;
        add(ups[1], gbc);

         gbc.gridx = 3;
        add(ups[2], gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("LEFT"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(lefts[0], gbc);

        gbc.gridx = 2;
        add(lefts[1], gbc);

         gbc.gridx = 3;
        add(lefts[2], gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("DOWN"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(downs[0], gbc);

        gbc.gridx = 2;
        add(downs[1], gbc);

         gbc.gridx = 3;
        add(downs[2], gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("RIGHT"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(rights[0], gbc);

        gbc.gridx = 2;
        add(rights[1], gbc);

         gbc.gridx = 3;
        add(rights[2], gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("BOMB"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(bomb[0], gbc);

        gbc.gridx = 2;
        add(bomb[1], gbc);

         gbc.gridx = 3;
        add(bomb[2], gbc);
    }

    private JLabel createTitle(String title) {
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        label.setForeground(Color.BLACK);

        return label;
    }

    private GameTextButton createKeyButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //parentPanel.goToStartGamePanel();
            }
        });
    }
}