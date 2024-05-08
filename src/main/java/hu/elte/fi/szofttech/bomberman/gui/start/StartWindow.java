package hu.elte.fi.szofttech.bomberman.gui.start;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import hu.elte.fi.szofttech.bomberman.gui.game.GameWindow;

public class StartWindow extends JFrame {
    public StartWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        StartPanel startPanel = new StartPanel(this);
        add(startPanel);
        pack();
        setLocationRelativeTo(null);
        addKeyListener(createKeyChangerAdapter(startPanel));
    }

    public void startGame(String mapPath, int playerNumber, int wonRoundNumber) {
        GameWindow gameWindow = new GameWindow(mapPath, playerNumber, wonRoundNumber);
        gameWindow.setVisible(true);
        dispose();
    }

    private KeyAdapter createKeyChangerAdapter(StartPanel panel) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                panel.forwardToKeyboardSettingPanel(e.getKeyCode());
            }
        };
    }
}
