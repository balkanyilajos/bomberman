package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JFrame;
import gui.GameWindow;
import model.sprite.Sprite;
import model.sprite.fixedelement.Wall;

public class GameModel {
    private final String[] MAPS_PATH = {
            "src/data/map/1.txt"
    };

    private final JFrame window;
    private ArrayList<Sprite>[][] gameTable;
    private int gameTableCols;
    private int gameTableRows;

    // private int rounds;
    // private int roundsCounter;

    // private int playerCounter;
    // private int[] playerPoints;

    private Dimension cubeSize;
    private Dimension boardSize;

    private Timer timer;
    // private long previousTime;
    // private int gameOverCooldownSeconds;

    public GameModel() {
        window = new GameWindow(this);
        fileReader(MAPS_PATH[0]);
        createTimer();

        window.setVisible(true);
    }

    public void setBoardSize(int width, int height) {
        this.boardSize = new Dimension(width, height);
    }

    private void setCubeSize(int numberOfCols, int numberOfRows) {
        this.cubeSize = new Dimension((int)Math.ceil(boardSize.getWidth()/numberOfCols), (int)Math.ceil(boardSize.getHeight()/numberOfRows));
    }

    private void initGameTable(int numberOfCols, int numberOfRows) {
        gameTableCols = numberOfCols;
        gameTableRows = numberOfRows;
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfCols; j++) {
                gameTable[i][j] = new ArrayList<Sprite>();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void fileReader(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] tokens = line.split(" ");
            int numberOfRows = Integer.parseInt(tokens[1]);
            int numberOfCols = Integer.parseInt(tokens[0]);
            setCubeSize(numberOfCols, numberOfRows);
            gameTable = new ArrayList[numberOfRows][numberOfCols];
            initGameTable(numberOfCols, numberOfRows);

            for(int i = 0; i < numberOfRows; i++) {
                line = br.readLine();
                tokens = line.split(" ");
                for(int j = 0; j < numberOfCols; j++) {
                    switch (tokens[j]) {
                        case "X":
                            gameTable[i][j].add(new Wall(this, new Point2D.Double(j*cubeSize.getWidth(), i*cubeSize.getHeight()), (Dimension)cubeSize.clone()));
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

    private void createTimer() {
        timer = new Timer();
    }

    public Point getIndexFromCoords(Point2D coord) {
        return new Point((int)(coord.getX() / this.cubeSize.getWidth()), (int)(coord.getY() / this.cubeSize.getHeight()));
    }

    public ArrayList<Sprite> getBoardSprites(Point2D point, double pixelRadius) {
        ArrayList<Sprite> returnSprites = new ArrayList<>();

        Point pointIndex = getIndexFromCoords(point);
        Point pixelRadiusIndex = getIndexFromCoords(new Point2D.Double(pixelRadius, pixelRadius));
        int startColIndex = (pointIndex.getX() - pixelRadiusIndex.getX() >= 0) ? (int)(pointIndex.getX() - pixelRadiusIndex.getX()) : 0;
        int endColIndex = (pointIndex.getX() - pixelRadiusIndex.getX() <  gameTableCols) ? (int)(pointIndex.getX() - pixelRadiusIndex.getX()) : gameTableCols;
        int startRowIndex = (pointIndex.getY() - pixelRadiusIndex.getY() >= 0) ? (int)(pointIndex.getY() - pixelRadiusIndex.getY()) : 0;
        int endRowIndex = (pointIndex.getY() - pixelRadiusIndex.getY() <  gameTableCols) ? (int)(pointIndex.getY() - pixelRadiusIndex.getY()) : gameTableRows;

        for(int i = startRowIndex; i < endRowIndex; i++) {
            for(int j = startColIndex; j < endColIndex; j++) {
                for(Sprite sprite : gameTable[i][j]) {
                    returnSprites.add(sprite);
                }
            }
        }
        
        return returnSprites;
    }

    public ArrayList<Sprite> getBoardSprites() {
        ArrayList<Sprite> returnSprites = new ArrayList<>();

        for(ArrayList<Sprite>[] row : gameTable) {
            for(ArrayList<Sprite> sprites : row) {
                for(Sprite sprite : sprites) {
                    returnSprites.add(sprite);
                }
            }
        }
        
        return returnSprites;
    }

}


