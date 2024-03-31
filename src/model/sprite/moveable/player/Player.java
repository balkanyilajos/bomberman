package model.sprite.moveable.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;
import java.awt.Image;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.fixedelement.Barrier;
import model.sprite.moveable.MoveableSprite;
import model.sprite.weapon.Bomb;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class Player extends MoveableSprite {
    private int numberOfBombs = 3;
    private int numberOfBarriers = 1;
    private double secTime;
    private boolean hasDetonator = false;
    private boolean hasRollerSkater = false;
    private boolean hasInvulnarability = false;
    private boolean hasGhostForm = false;
    private boolean hasNoBomb = false;
    private boolean hasSlowMovement = false;
    private boolean hasAllBombsPlaceNow = false;
    private double sizeOfExplosion;
    private HashSet<Bomb> bombs;

    public Player(GameModel model, Area area, Point2D imagePoint, Point2D areaPoint, Dimension imageSize,
            String imagePath, int ExplosionSize) {

        super(model, area, imagePoint, areaPoint, imageSize, imagePath);
        sizeOfExplosion = ExplosionSize;
    }

    public void setInvulnerability(double secTime) {
        hasInvulnarability = true;
    }

    public void setGhostForm(double secTime) {
        hasGhostForm = true;
    }

    public void setDetonator() {
        hasDetonator = true;
    }

    public void setRollerSkater() {
        hasRollerSkater = true;
    }

    public void setNoBomb(double secTime) {
        hasNoBomb = true;
    }

    public void slowMovement() {
        hasSlowMovement = true;
    }

    public void setAllBombsPlaceNow() {
        hasAllBombsPlaceNow = true;
    }

    private void placeBomb() {
        numberOfBombs--;
        Bomb bomb = new Bomb(this.model, this.areaPoint, this.sizeOfExplosion, 1);
        bombs.add(bomb);
    }

    public void increaseNumberOfBombs(int numberOfNewBombs) {
        numberOfBombs += numberOfNewBombs;
    }

    public void increaseExplosion(int numberOfCubes) {
        sizeOfExplosion += numberOfCubes;
    }

    public void decreaseExposion(int numberOfCubes) {
        sizeOfExplosion -= numberOfCubes;
    }

    private void palceBarrier() {
        numberOfBarriers--;
        Barrier barrier = new Barrier(model, imagePoint);
    }

    @Override
    public void moveUp(double deltaTime) {

    }

    @Override
    public void moveDown(double deltaTime) {

    }

    @Override
    public void moveLeft(double deltaTime) {

    }

    @Override
    public void moveRight(double deltaTime) {

    }
}