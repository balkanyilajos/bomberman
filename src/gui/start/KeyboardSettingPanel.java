package gui.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.HashMap;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.button.GameImageButton;
import gui.button.GameTextButton;
import model.GameModel;

public class KeyboardSettingPanel extends JPanel
{
    private StartPanel parentPanel;
    private int[] p1;
    private int[] p2;
    private int[] p3;
    private int selectedIndex;
    private int playerNumber;
    private GridBagConstraints gbc;
    private GameTextButton[] keys;
    private boolean def;
    
    public KeyboardSettingPanel(StartPanel parentPanel) {
        setLayout(new GridBagLayout());
        this.parentPanel = parentPanel;
        this.selectedIndex = -1;
        this.playerNumber = 3;
        setBackground(new Color(0, 0, 0, 0));

        int smallButtonWidth = 150;
        int bigButtonSize = 2 * smallButtonWidth;
        
        this.keys = new GameTextButton[15];
        init(smallButtonWidth, bigButtonSize);

        for(GameTextButton button : keys) button.setButtonGroup(keys);

        this.gbc = new GridBagConstraints();
        this.def = false;

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

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(createBackButton("Back", smallButtonWidth), gbc);

        gbc.gridx = 2;
        add(createResetButton("Reset", smallButtonWidth), gbc);
        
    }

    private void init(int small, int big)
    {
        reset();
        for(int i = 0; i < keys.length; i++)
        {
            int[] array = i < 5 ? p1 : i < 10 ? p2 : p3;
            int index = i%5;
            String text = Character.toString(((char)array[index]));
            keys[i] = createRoundsRadioButton(text, small, i);
        }
    }

    private void init(boolean enable)
    {
        System.out.println(enable);
        reset();
        for(int i = 0; i < keys.length; i++)
        {
            int[] array = i < 5 ? p1 : i < 10 ? p2 : p3;
            int index = i%5;
            String text = Character.toString(((char)array[index]));
            keys[i].setText(text);
            keys[i].setEnabled(enable);
        }
    }

    private void reset()
    {
        this.p1 = new int[5];
        p1[0] = KeyEvent.VK_W;
        p1[1] = KeyEvent.VK_A;
        p1[2] = KeyEvent.VK_S;
        p1[3] = KeyEvent.VK_D;
        p1[4] = KeyEvent.VK_E;
        this.p2 = new int[5];
        p2[0] = KeyEvent.VK_I;
        p2[1] = KeyEvent.VK_J;
        p2[2] = KeyEvent.VK_K;
        p2[3] = KeyEvent.VK_L;
        p2[4] = KeyEvent.VK_O;
        this.p3 = new int[5];
        p3[0] = KeyEvent.VK_8;
        p3[1] = KeyEvent.VK_4;
        p3[2] = KeyEvent.VK_5;
        p3[3] = KeyEvent.VK_6;
        p3[4] = KeyEvent.VK_9;
    }

    public void setPlayerNumber(int playerNumber, boolean def)
    {
        System.out.println(def);
        if(this.playerNumber < playerNumber && !def)
        {
            boolean matching = false;
            ArrayList<Integer> used = new ArrayList<Integer>();
            int[][] players = new int[3][5];
            players[0] = p1;
            players[1] = p2;
            players[2] = p3;
            for(int p = 0; p<players.length && !matching; p++)
            {
                int[] P = players[p];
                for(int i = 0; i<P.length && !matching; i++)
                {
                    if(!used.contains(P[i])) { used.add(P[i]); }
                    else{ matching = !matching; }
                }
            }
            if(matching || !def)
            {init(!def);}
            if(this.def != def)
            {
                this.def = def;
            }
        }
        else if(this.def != def)
        {
            this.def = def;
            init(!def);
        }
        this.playerNumber = playerNumber;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 3;
        gbc.gridy = 1;
        for (int i = 10; i<keys.length; i++)
        {
            if(playerNumber == 2)
            { remove(keys[i]); }
            else
            { add(keys[i],gbc); }
            gbc.gridy++;
        }
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
    }

    private GameTextButton createBackButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.goToMapSettingPanel();
            }
        });
    }

    private GameTextButton createResetButton(String text, int width) {
        return new GameTextButton(text, width, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init(!def);
            }
        });
    }
        
    public void modifyKeySetting(int keyCode)
    {
        if(selectedIndex >= 0 && (keyCode != 0 && keyCode != 16 && keyCode != 17 && keyCode != 18 && keyCode != 20 && keyCode != 144 && keyCode != 524 && keyCode != 525))
        {
            int[] array = selectedIndex < 5 ? p1 : selectedIndex < 10 ? p2 : p3;
            int index = selectedIndex%array.length;
            HashMap<Integer, String> arrows = new HashMap<Integer,String>();
            arrows.put(38, "up arrow");
            arrows.put(39, "right arrow");
            arrows.put(40, "down arrow");
            arrows.put(37, "left arrow");
            arrows.put(10, "enter");
            String text = (keyCode > 36 && keyCode < 41) ? arrows.get(keyCode).toUpperCase() : Character.toString((char)keyCode);
            boolean matching = false;
            int[][] players = new int[3][5];
            players[0] = p1;
            players[1] = p2;
            players[2] = p3;
            for(int p = 0; p<playerNumber && !matching; p++)
            {
                int[] P = players[p];
                for(int i = 0; i<P.length && !matching; i++)
                {
                    matching = P[i] == keyCode;
                }
            }
            if(!matching)
            {
                array[index]=keyCode;
                keys[selectedIndex].setText(text);
            }
        }
    }
}