package model;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import model.sprite.Sprite;
import model.sprite.fixedelement.Barrier;
import model.sprite.fixedelement.Box;
import model.sprite.fixedelement.Wall;
import model.sprite.moveable.player.Player;
import model.sprite.weapon.Bomb;

public class MapReader {
    private String map;
    private Dimension boardIndexSize;
    private Dimension cubeSize;
    private GameModel model;
    private ArrayList<Sprite>[][] board;
    private HashSet<Sprite> sprites;

    public MapReader(String mapPath, GameModel model, Dimension cubeSize) throws FileNotFoundException, IOException {
        this.map = mapPath;
        this.model = model;
        this.cubeSize = cubeSize;
        this.sprites = new HashSet<>();
        fileReader();
    }

    public static Dimension getBoardIndexSize(String mapPath) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(mapPath))) {
            String line = br.readLine();
            String[] tokens = line.split(" ");
            int numberOfRows = Integer.parseInt(tokens[1]);
            int numberOfCols = Integer.parseInt(tokens[0]);
            return new Dimension(numberOfCols, numberOfRows);
        }
    }

    public ArrayList<Sprite>[][] getBoard() {
        return board;
    }

    public HashSet<Sprite> getSprites() {
        return sprites;
    }

    public String getMapPath() {
        return map;
    }

    public Dimension getBoardIndexSize() {
        return boardIndexSize;
    }

    public Dimension getCubeSize() {
        return cubeSize;
    }

    @SuppressWarnings("unchecked")
    private void initBoard() {
        board = new ArrayList[boardIndexSize.height][boardIndexSize.width];
        for (int i = 0; i < boardIndexSize.height; i++) {
            for (int j = 0; j < boardIndexSize.width; j++) {
                board[i][j] = new ArrayList<Sprite>();
            }
        }
    }

    private void fileReader() throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(map))) {
            // read boardIndexSize
            String line = br.readLine();
            String[] tokens = line.split(" ");
            int numberOfRows = Integer.parseInt(tokens[1]);
            int numberOfCols = Integer.parseInt(tokens[0]);
            boardIndexSize = new Dimension(numberOfCols, numberOfRows);
            initBoard();

            // read board elements
            for (int i = 0; i < boardIndexSize.height; i++) {
                line = br.readLine();
                tokens = line.split(" ");
                for (int j = 0; j < boardIndexSize.width; j++) {
                    Point2D point = new Point2D.Double(j * cubeSize.getWidth(), i * cubeSize.getHeight());
                    switch (tokens[j]) {
                        case "X":
                            Wall wall = new Wall(model, point);
                            board[i][j].add(wall);
                            sprites.add(wall);
                            break;

                        case "O":
                            Bomb bomb = new Bomb(model, point, 2 * cubeSize.getWidth(), 3);
                            board[i][j].add(bomb);
                            sprites.add(bomb);
                            break;

                        case "N":
                            Box box = new Box(model, point);
                            board[i][j].add(box);
                            sprites.add(box);
                            break;

                        case "B":
                            Barrier barrier = new Barrier(model, point);
                            board[i][j].add(barrier);
                            sprites.add(barrier);
                            break;

                        case "P":
                            Player player1 = new Player(model, point, "src/data/picture/player/gamer.png");
                            board[i][j].add(player1);
                            sprites.add(player1);
                            break;

                        default:
                            break;
                    }
                }
            }

            /*
             * Bomb b = new Bomb(model, new Point2D.Double(1 * cubeSize.getWidth(), 1 *
             * cubeSize.getHeight()),
             * 4 * this.cubeSize.getWidth());
             * board[1][1].add(b);
             * sprites.add(b);
             * b = new Bomb(model, new Point2D.Double(3 * cubeSize.getWidth(), 1 *
             * cubeSize.getHeight()),
             * 2 * this.cubeSize.getWidth());
             * board[1][3].add(b);
             * sprites.add(b);
             * b = new Bomb(model, new Point2D.Double(5 * cubeSize.getWidth(), 1 *
             * cubeSize.getHeight()),
             * 2 * this.cubeSize.getWidth());
             * board[1][5].add(b);
             * sprites.add(b);
             * // b = new Bomb(model, new Point2D.Double(7*cubeSize.getWidth(),
             * // 1*cubeSize.getHeight()), 2 * this.cubeSize.getWidth());
             * // board[5][7].add(b);
             * // sprites.add(b);
             */

        }
    }

}
