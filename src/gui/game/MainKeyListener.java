package gui.game;

import javax.swing.*;

import model.util.PlayerAction;
import model.sprite.moveable.player.*;
import java.awt.event.*;

public class MainKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        // Nem használt
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Billentyű lenyomásának eseménye
        if (e.getKeyChar() == 'w') {
            System.out.println("-----------------A 'w' gombot lenyomták.-----------------");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            // System.out.println("~~~~~~~~~~~~~~~~~A 'w' gombot
            // felengedték.~~~~~~~~~~~~~~~~~");
        }
    }
}
