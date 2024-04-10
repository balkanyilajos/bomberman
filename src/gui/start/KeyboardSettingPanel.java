package gui.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.button.GameImageButton;
import gui.button.GameTextButton;
import model.GameModel;

public class KeyboardSettingPanel extends JPanel
{
    private StartPanel parentPanel;
    private int[] p1 = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_E};
    private int[] p2 = {KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_O};
    private int[] p3 = {KeyEvent.VK_8, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_9};
    private int selectedIndex;
    private GameTextButton[] keys;
    
    public KeyboardSettingPanel(StartPanel parentPanel) {
        setLayout(new GridBagLayout());
        this.parentPanel = parentPanel;
        this.selectedIndex = -1;
        setBackground(new Color(0, 0, 0, 0));

        int smallButtonWidth = 60;
        int bigButtonSize = 2 * smallButtonWidth;

        this.keys = new GameTextButton[15];
        for(int i = 0; i < keys.length; i++)
        {
            int[] array = i < 5 ? p1 : i < 10 ? p2 : p3;
            int index = i%5;
            String text = Character.toString(((char)array[index]));
            keys[i] = createRoundsRadioButton(text, smallButtonWidth, i);
        }

        for(GameTextButton button : keys) button.setButtonGroup(keys);

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
        add(keys[0], gbc);

        gbc.gridx = 2;
        add(keys[5], gbc);

         gbc.gridx = 3;
        add(keys[10], gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("LEFT"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(keys[1], gbc);

        gbc.gridx = 2;
        add(keys[6], gbc);

         gbc.gridx = 3;
        add(keys[11], gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("DOWN"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(keys[2], gbc);

        gbc.gridx = 2;
        add(keys[7], gbc);

         gbc.gridx = 3;
        add(keys[12], gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("RIGHT"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(keys[3], gbc);

        gbc.gridx = 2;
        add(keys[8], gbc);

         gbc.gridx = 3;
        add(keys[13], gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        add(createTitle("BOMB"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(keys[4], gbc);

        gbc.gridx = 2;
        add(keys[9], gbc);

         gbc.gridx = 3;
        add(keys[14], gbc);
        
    }

    private JLabel createTitle(String title) {
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        label.setForeground(Color.BLACK);

        return label;
    }

    private GameTextButton createRoundsRadioButton(String text, int width, int i) {
        return new GameTextButton(text, width, true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               selectedIndex = i;
            }});
        };
    public void modifyKeySetting(int keyCode)
    {
        if(selectedIndex >= 0 && (keyCode != 0 && keyCode != 16 && keyCode != 17 && keyCode != 18 && keyCode != 20 && keyCode != 524 && keyCode != 525))
        {
            int[] array = selectedIndex < 5 ? p1 : selectedIndex < 10 ? p2 : p3;
            int index = selectedIndex%array.length;
            char c = (char)keyCode;
            System.out.println(keyCode);
            boolean matching = false;
            for(int i = 0; i<p1.length && !matching; i++)
            {
                matching = p1[i] == keyCode;
            }
            for(int i = 0; i<p2.length && !matching; i++)
            {
                matching = p2[i] == keyCode;
            }
            for(int i = 0; i<p3.length && !matching; i++)
            {
                matching = p3[i] == keyCode;
            }
            if(!matching)
            {
                array[index]=keyCode;
                keys[selectedIndex].setText(Character.toString(c));
            }
        }
    }
}