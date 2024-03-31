package gui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameModel;

public class MenuPanel extends JPanel {
    private GameModel gameModel;
    private long startTime;
    private JLabel timerText;
    private DateTimeFormatter timeFormatter;

    public MenuPanel(GameModel gameModel) {
        setLayout(new GridBagLayout());
        this.gameModel = gameModel;
        this.timeFormatter = DateTimeFormatter.ofPattern("mm:ss");
        this.startTime = System.nanoTime();
        this.timerText = new JLabel();
        timerText.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        timerText.setForeground(Color.WHITE);
        updateTimerText();

        setBackground(Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 20, 5, 20);
        add(timerText, gbc);

        Dimension pixelSize = new Dimension(gameModel.getBoardPixelSize().width, getPreferredSize().height);
        setPreferredSize(pixelSize);
        setMinimumSize(pixelSize);
    }

    private void updateTimerText() {
        int seconds = (int)((System.nanoTime() - startTime) / 1_000_000_000.0);
        timerText.setText(LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC).format(timeFormatter));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        updateTimerText();
    }

}
