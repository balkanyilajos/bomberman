package model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.Timer;

import gui.GameWindow;
import model.sprite.Sprite;
import model.sprite.fixedelement.Box;
import model.sprite.fixedelement.Wall;
import model.sprite.weapon.Bomb;

public class GameModel {
    private final String[] MAPS_PATH = {
            "src/data/map/1.txt"
    };

    private final GameWindow window;
    private ArrayList<Sprite>[][] board;
    private HashSet<Sprite> sprites;
    private int boardCols;
    private int boardRows;

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
        sprites = new HashSet<>();
        fileReader(MAPS_PATH[0]);
        createTimer();
        timer.start();

        window.setVisible(true);
    }

    public void setBoardSize(int width, int height) {
        this.boardSize = new Dimension(width, height);
    }

    private void setCubeSize(int numberOfCols, int numberOfRows) {
        this.cubeSize = new Dimension((int)Math.ceil(boardSize.getWidth()/numberOfCols), (int)Math.ceil(boardSize.getHeight()/numberOfRows));
    }

    public Dimension getCubeSize() {
        return (Dimension)cubeSize.clone();
    }

    private void initboard(int numberOfCols, int numberOfRows) {
        boardCols = numberOfCols;
        boardRows = numberOfRows;
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfCols; j++) {
                board[i][j] = new ArrayList<Sprite>();
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
            board = new ArrayList[numberOfRows][numberOfCols];
            initboard(numberOfCols, numberOfRows);

            for(int i = 0; i < numberOfRows; i++) {
                line = br.readLine();
                tokens = line.split(" ");
                for(int j = 0; j < numberOfCols; j++) {
                    switch (tokens[j]) {
                        case "X":
                            Wall wall = new Wall(this, new Point2D.Double(j*cubeSize.getWidth(), i*cubeSize.getHeight()));
                            board[i][j].add(wall);
                            sprites.add(wall);
                            break;
                    
                        case "O":
                            Bomb bomb = new Bomb(this, new Point2D.Double(j*cubeSize.getWidth(), i*cubeSize.getHeight()), 2 * this.cubeSize.getWidth(), 2);
                            board[i][j].add(bomb);
                            sprites.add(bomb);
                            break;
                        
                        case "N":
                            Box box = new Box(this, new Point2D.Double(j*cubeSize.getWidth(), i*cubeSize.getHeight()));
                            board[i][j].add(box);
                            sprites.add(box);
                            break;
                        
                        default:
                            break;
                    }
                }
            }
            Bomb b = new Bomb(this, new Point2D.Double(5*cubeSize.getWidth(), 5*cubeSize.getHeight()), 2 * this.cubeSize.getWidth());
            board[5][5].add(b);
            sprites.add(b);
            b = new Bomb(this, new Point2D.Double(3*cubeSize.getWidth(), 5*cubeSize.getHeight()), 2 * this.cubeSize.getWidth());
            board[5][3].add(b);
            sprites.add(b);
            b = new Bomb(this, new Point2D.Double(1*cubeSize.getWidth(), 5*cubeSize.getHeight()), 2 * this.cubeSize.getWidth());
            board[5][1].add(b);
            sprites.add(b);
        } 
        catch (FileNotFoundException e) {
            System.out.println(e);
        } 
        catch (IOException e) {
            System.out.println(e);
        }
    }

    private void createTimer() {
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                window.repaint();
            }
        });
    }

    public Point getIndexFromCoords(Point2D coord) {
        return new Point((int)(coord.getX() / this.cubeSize.getWidth()), (int)(coord.getY() / this.cubeSize.getHeight()));
    }

    public Point2D getCoordsFromIndex(Point coord) {
        return new Point2D.Double(coord.getX() * this.cubeSize.getWidth(), coord.getY() * this.cubeSize.getHeight());
    }

    public HashSet<Sprite> getBoardSprites(Point2D coord, double pixelRadius) {
        HashSet<Sprite> returnSprites = new HashSet<>();

        Point pointIndex = getIndexFromCoords(coord);
        Point pixelRadiusIndex = getIndexFromCoords(new Point2D.Double(pixelRadius, pixelRadius));
        int startColIndex = (pointIndex.getX() - pixelRadiusIndex.getX() >= 0) ? (int)(pointIndex.getX() - pixelRadiusIndex.getX()) : 0;
        int endColIndex = (pointIndex.getX() + pixelRadiusIndex.getX() <  boardCols) ? (int)(pointIndex.getX() + pixelRadiusIndex.getX()) : boardCols-1;
        int startRowIndex = (pointIndex.getY() - pixelRadiusIndex.getY() >= 0) ? (int)(pointIndex.getY() - pixelRadiusIndex.getY()) : 0;
        int endRowIndex = (pointIndex.getY() + pixelRadiusIndex.getY() <  boardCols) ? (int)(pointIndex.getY() + pixelRadiusIndex.getY()) : boardRows-1;

        for(int i = startRowIndex; i <= endRowIndex; i++) {
            for(int j = startColIndex; j <= endColIndex; j++) {
                for(Sprite sprite : board[i][j]) {
                    returnSprites.add(sprite);
                }
            }
        }
        
        return returnSprites;
    }

    public HashSet<Sprite> getBoardSprites() {       
        return sprites;
    }

    public void addSpriteToBoard(Sprite sprite) {
        Point indexPoint = getIndexFromCoords(sprite.getImagePoint());
        this.board[(int)indexPoint.getY()][(int)indexPoint.getX()].add(sprite);
        sprites.add(sprite);
    }

    public void deleteSpriteFromBoard(Sprite sprite) {
        Point indexPoint = getIndexFromCoords(sprite.getImagePoint());
        this.board[(int)indexPoint.getY()][(int)indexPoint.getX()].remove(sprite);
        sprites.remove(sprite);
    }

}


