package hu.elte.fi.szofttech.bomberman.model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.Timer;

import hu.elte.fi.szofttech.bomberman.gui.game.GameWindow;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;
import hu.elte.fi.szofttech.bomberman.model.util.PlayerAction;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class GameModel {
    public static final String[] MAPS_PATH = {
            GeneralPath.getPath() + "/map/1",
            GeneralPath.getPath() + "/map/2",
            GeneralPath.getPath() + "/map/3"
    };

    private static final String Player = null;

    private final GameWindow window;

    private String MapPath;
    private int playerNumber;
    private int wonRoundNumber;
    private double waitSec;
    private Player winner;

    private ArrayList<Sprite>[][] board;
    private HashSet<Sprite> sprites;
    private HashSet<Sprite> deletedSprites;

    // private int rounds;
    // private int roundsCounter;

    // private int playerCounter;
    // private int[] playerPoints;

    private Dimension cubeSize;
    private Dimension boardIndexSize;
    private Dimension boardPixelSize;

    private Timer timer;
    private long previousTime;
    private ArrayList<PlayerAction> playerActions;
    private ArrayList<Player> players  = new ArrayList<Player>();
    // private int gameOverCooldownSeconds;

    public GameModel(GameWindow window, String mapPath, int wonRoundNumber,
            ArrayList<PlayerAction> playerActions) {
        this.playerActions = playerActions;
        this.window = window;
        players.add(null);
        players.add(null);
        players.add(null);
        createTimer();
        init(mapPath, playerActions.size(), wonRoundNumber);
    }

    public void init(String mapPath, int playerNumber, int wonRoundNumber) {
        this.MapPath = mapPath;
        this.playerNumber = playerNumber;
        this.wonRoundNumber = wonRoundNumber;
        this.previousTime = System.nanoTime();
        this.sprites = new HashSet<>();
        this.deletedSprites = new HashSet<>();
        this.winner = null;
        this.waitSec = 15;

        try {
            this.boardIndexSize = MapReader.getBoardIndexSize(mapPath);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void start(Dimension cubeSize) {
        this.cubeSize = cubeSize;
        this.boardPixelSize = new Dimension(cubeSize.width * boardIndexSize.width,
                cubeSize.height * boardIndexSize.height);
        try {
            MapReader mr = new MapReader(MapPath, this, cubeSize, playerActions);
            board = mr.getBoard();
            sprites = mr.getSprites();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        updatePreviousTime();
        timer.start();
    }

    public void setPlayer(int index, Player player)
    {
        if(index<0 || index >= players.size()){ return; }
        players.set(index, player);
    }

    public Player getPlayer(int index)
    {
        if(index<0 || index >= players.size()){ return null; }
        return players.get(index);
    }

    public void setCubeSize(Dimension cubeSize) {
        this.cubeSize = cubeSize;
    }

    public Dimension getCubeSize() {
        return (Dimension) cubeSize.clone();
    }

    public Dimension getBoardIndexSize() {
        return (Dimension) boardIndexSize.clone();
    }

    public Dimension getBoardPixelSize() {
        return (Dimension) boardPixelSize.clone();
    }

    private void createTimer() {
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                double deltaTime = getDeltaTime(updatePreviousTime(), previousTime);

                updateSprites(deltaTime);

                if (window != null) {
                    window.repaint();
                    if(isEnd())
                    {
                        waitSec = waitSec - deltaTime;
                        if(waitSec <= 0)
                        {
                            timer.stop();
                            wonRoundNumber = wonRoundNumber-1;
                            if(winner != null)
                            { winner.won(); System.out.println("Round win*"+winner);}
                            System.out.println(winner == null ? "No winner" : winner);
                            //System.out.println(wonRoundNumber);
                            if(wonRoundNumber == 0)
                            {
                                int p1 = getPlayer(0) == null ? 0 : getPlayer(0).getWonRoundNumber();
                                int p2 = getPlayer(1) == null ? 0 : getPlayer(1).getWonRoundNumber();
                                int p3 = getPlayer(2) == null ? 0 : getPlayer(2).getWonRoundNumber();
                                Player win = null;
                                if( p1 < p2) { win = getPlayer(1); }
                                else if(p1 > p2) { win = getPlayer(0); }
                                if( (win == null ? 0 : win.getWonRoundNumber()) < p3) { win = getPlayer(2); }
                                else if((win == null ? 0 : win.getWonRoundNumber()) == p3) { win = null; }
                                System.out.println("Game win*" + ((win == null) ? "No winner" : win));
                                return;
                            }
                            init(MapPath, playerActions.size(), wonRoundNumber);
                            start(cubeSize);
                        }
                    }
                }
            }
        });
    }

    private long updatePreviousTime() {
        long temp = this.previousTime;
        this.previousTime = System.nanoTime();
        return temp;
    }

    private double getDeltaTime(long previousTime, long currentTime) {
        return (currentTime - previousTime) / 1_000_000_000.0;
    }

    private void updateSprites(double deltaTime) {
        for (Sprite sprite : getBoardSprites()) {
            sprite.update(deltaTime);
        }
    }

    public Point getIndexFromCoords(Point2D coord) {
        return new Point((int) (coord.getX() / this.cubeSize.getWidth()),
                (int) (coord.getY() / this.cubeSize.getHeight()));
    }

    public Point2D getCoordsFromIndex(Point coord) {
        return new Point2D.Double(coord.getX() * this.cubeSize.getWidth(), coord.getY() * this.cubeSize.getHeight());
    }

    public HashSet<Sprite> getBoardSprites(Point2D coord, double pixelRadius) {
        HashSet<Sprite> returnSprites = new HashSet<>();
        Point pointIndex = getIndexFromCoords(coord);
        Point pixelRadiusIndex = getIndexFromCoords(new Point2D.Double(pixelRadius, pixelRadius));
        int startColIndex = (pointIndex.x - pixelRadiusIndex.x >= 0) ? (int) (pointIndex.x - pixelRadiusIndex.x) : 0;
        int endColIndex = (pointIndex.x + pixelRadiusIndex.x < board[0].length)
                ? (int) (pointIndex.x + pixelRadiusIndex.x)
                : board[0].length - 1;
        int startRowIndex = (pointIndex.y - pixelRadiusIndex.y >= 0) ? (int) (pointIndex.y - pixelRadiusIndex.y) : 0;
        int endRowIndex = (pointIndex.y + pixelRadiusIndex.y < board.length) ? (int) (pointIndex.y + pixelRadiusIndex.y)
                : board.length - 1;

        for (int i = startRowIndex; i <= endRowIndex; i++) {
            for (int j = startColIndex; j <= endColIndex; j++) {
                for (Sprite sprite : board[i][j]) {
                    returnSprites.add(sprite);
                }
            }
        }

        return returnSprites;
    }

    public boolean isEnd() {
        int numberOfActivePlayers = 0;
        winner = null;
        for (Sprite sprite : sprites) {
            if (sprite instanceof Player) {
                numberOfActivePlayers++;
                winner = (Player) sprite;
            }
        }
        return numberOfActivePlayers < 2;
    }

    @SuppressWarnings("unchecked")
    public HashSet<Sprite> getBoardSprites() {
        return (HashSet<Sprite>) sprites.clone();
    }

    public ArrayList<Sprite> getSpriteFromMatrix(Point point) {
        ArrayList<Sprite> result = board[point.x][point.y];
        return result;
    }

    public void addSpriteToBoard(Sprite sprite) {
        if( sprite instanceof Player)
        {
            Player p = (Player) sprite;
            players.add(p);
        }
        if (deletedSprites.contains(sprite)) {
            System.out.println("Already deleted!");
            return;
        }
        Point indexPoint = getIndexFromCoords(sprite.getAreaPoint());
        this.board[(int) indexPoint.getY()][(int) indexPoint.getX()].add(sprite);
        sprites.add(sprite);
    }

    public void deleteSpriteFromBoard(Sprite sprite) {
        deletedSprites.add(sprite);
        Point indexPoint = getIndexFromCoords(sprite.getAreaPoint());
        this.board[(int) indexPoint.getY()][(int) indexPoint.getX()].remove(sprite);
        sprites.remove(sprite);
    }

    public void changeSpriteMovementOnBoard(Sprite sprite, Point2D previousAreaPoint) {
        addSpriteToBoard(sprite);
        Point indexPoint = getIndexFromCoords(previousAreaPoint);
        this.board[(int) indexPoint.getY()][(int) indexPoint.getX()].remove(sprite);
    }

    public void gameStop(boolean value) {
        if (value) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].size() == 0) {
                    sb.append("_ ");
                } else {
                    boolean p = false;
                    for (Sprite s : board[i][j]) {
                        if (s instanceof Player) {
                            sb.append("O ");
                            p = true;
                        }
                    }
                    if (!p) {
                        sb.append("X ");
                    }
                }
            }
            sb.append("\n");
        }

        sb.append("\n");
        System.out.println(sb.toString());
    }

}
