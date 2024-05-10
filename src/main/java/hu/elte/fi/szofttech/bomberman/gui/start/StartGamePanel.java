package hu.elte.fi.szofttech.bomberman.gui.start;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import hu.elte.fi.szofttech.bomberman.gui.button.GameTextButton;

public class StartGamePanel extends JPanel {
    private StartPanel parentPanel;

    public StartGamePanel(StartPanel parentPanel) {
        setLayout(new GridBagLayout());
        this.parentPanel = parentPanel;
        setBackground(new Color(0, 0, 0, 0));
        int buttonWidth = 120;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(createStartButton("Start game", buttonWidth), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(createSettingButton("Setting", buttonWidth), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(createExitButton("Exit", buttonWidth), gbc);
    }

    private JButton createStartButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.goToMapSettingPanel();
            }
        });
    }

    private JButton createExitButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private JButton createSettingButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.goToKeyboardSettingPanel();
            }
        });
    }
}
