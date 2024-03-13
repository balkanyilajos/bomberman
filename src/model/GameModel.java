package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import gui.GameWindow;
import model.sprite.Sprite;
import model.sprite.fixedelement.Wall;

public class GameModel {
    private final String[] MAPS_PATH = {
            "src/data/map/1.txt"
    };

    private final JFrame window;
    private Sprite[][] gameTable;
    // private int rounds;
    // private int roundsCounter;

    // private int playerCounter;
    // private int[] playerPoints;

    private Dimension cubeSize;
    // private Dimension dimension;

    // private long previousTime;
    // private int gameOverCooldownSeconds;

    public GameModel() {
        cubeSize = new Dimension(50,50);
        fileReader(MAPS_PATH[0]);
        window = new GameWindow();
        window.setVisible(true);
    }

    private void fileReader(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] tokens = line.split(" ");
            int numberOfRows = Integer.parseInt(tokens[1]);
            int numberOfCols = Integer.parseInt(tokens[0]);
            gameTable = new Sprite[numberOfRows][numberOfCols];

            for(int i = 0; i < numberOfRows; i++) {
                line = br.readLine();
                tokens = line.split(" ");
                for(int j = 0; j < numberOfCols; j++) {
                    switch (tokens[j]) {
                        case "X":
                            gameTable[i][j] = new Wall(this, new Point2D.Double(j*cubeSize.getWidth(), i*cubeSize.getHeight()), (Dimension)cubeSize.clone());
                            break;
                    
                        default:
                            break;
                    }
                }
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println(e);
        } 
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
