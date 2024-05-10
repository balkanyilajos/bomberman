package hu.elte.fi.szofttech.bomberman.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameTextButton extends JButton implements RadioButton {
    private Color activeColor;
    private Color selectedColor;
    private Color standardColor;
    private boolean isSelected;
    private RadioButton[] buttons;

    public GameTextButton(String text, int width, int height, ActionListener actionListener) {
        this(text, actionListener);
        setPreferredSize(new Dimension(width, height));
    }

    public GameTextButton(String text, int width, ActionListener actionListener) {
        this(text, actionListener);
        setPreferredSize(new Dimension(width, getPreferredSize().height));
    }

    public GameTextButton(String text, int width, boolean isRadioButton, ActionListener actionListener) {
        this(text, isRadioButton, actionListener);
        setPreferredSize(new Dimension(width, getPreferredSize().height));
    }

    public GameTextButton(String text, boolean isRadioButton, ActionListener actionListener) {
        this(text, actionListener);
        if (isRadioButton) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground((isSelected) ? selectedColor : activeColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground((isSelected) ? selectedColor : standardColor);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    update(true);
                }
            });
        }
    }

    public GameTextButton(String text, ActionListener actionListener) {
        activeColor = Color.ORANGE;
        selectedColor = Color.ORANGE.darker();
        standardColor = Color.WHITE;
        isSelected = false;

        setText(text);
        setFocusPainted(false);
        setFocusable(false);
        setBackground(standardColor);
        setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        addActionListener(actionListener);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground((isSelected) ? selectedColor : activeColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground((isSelected) ? selectedColor : standardColor);
            }
        });
    }

    @Override
    public void setButtonGroup(RadioButton[] buttons) {
        this.buttons = buttons;
    }

    @Override
    public void update(boolean isSelected) {
        this.isSelected = isSelected;
        if (!isSelected) {
            setBackground(standardColor);
            return;
        }

        setBackground(selectedColor);
        for (RadioButton button : buttons) {
            if (!button.equals(this)) {
                button.update(false);
            }
        }
    }

}
