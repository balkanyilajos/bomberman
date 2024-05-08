package hu.elte.fi.szofttech.bomberman.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class GameImageButton extends JButton implements RadioButton {
    private Border standardBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
    private Border activeBorder = BorderFactory.createLineBorder(Color.ORANGE, 3);
    private Border selectedBorder = BorderFactory.createLineBorder(Color.ORANGE.darker(), 3);
    private boolean isSelected = false;
    private RadioButton[] buttons;
    private Image background;
    private int width;
    private int height;

    public GameImageButton(String imagePath, int width, int height, boolean isRadioButton,
            ActionListener actionListener) {
        this(imagePath, width, height, actionListener);
        if (isRadioButton) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder((isSelected) ? selectedBorder : activeBorder);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder((isSelected) ? selectedBorder : standardBorder);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    update(true);
                }
            });
        }
    }

    public GameImageButton(String imagePath, int width, int height, ActionListener actionListener) {
        try {
            this.background = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e);
        }

        this.width = width;
        this.height = height;
        addActionListener(actionListener);
        setPreferredSize(new Dimension(width, height));
        setFocusPainted(false);
        setFocusable(false);
        setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        setMargin(new Insets(5, 5, 5, 5));
        setBorder(standardBorder);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder((isSelected) ? selectedBorder : activeBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder((isSelected) ? selectedBorder : standardBorder);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, width, height, null);
    }

    @Override
    public void update(boolean isSelected) {
        this.isSelected = isSelected;
        if (!isSelected) {
            setBorder(standardBorder);
            return;
        }

        setBorder(selectedBorder);
        for (RadioButton button : buttons) {
            if (!button.equals(this)) {
                button.update(false);
            }
        }
    }

    @Override
    public void setButtonGroup(RadioButton[] buttons) {
        this.buttons = buttons;
    }
}
