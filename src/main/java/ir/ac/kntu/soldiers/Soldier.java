package ir.ac.kntu.soldiers;

import ir.ac.kntu.Main;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ir.ac.kntu.Player;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.map.Tile;
import ir.ac.kntu.map.TypeOfTiles;
import ir.ac.kntu.towers.Tower;

import java.io.Serializable;
import java.util.ArrayList;

public class Soldier implements Runnable, Serializable {
    private static final int ONE_SECOND = 1000;
    // fields

    private final Player player;
    private final String name;
    private final double energy;
    private double health;
    private final double initHealth;
    private final int speed;
    private final double damage;
    private final int range;
    private Tile tile;
    private Tile last;
    private Map map;
    private boolean alive = true;
    private Circle circle ;
    private final Color color;
    private final ProgressBar healthBarTower = new ProgressBar();
    private final BorderPane soldierPane = new BorderPane();
    public static int num = 0;
    private final int id ;

    /**
     * This constructor get these items to make a soldier
     * @param player
     * @param name
     * @param energy
     * @param health
     * @param speed
     * @param damage
     * @param range
     * @param color
     */

    public Soldier(Player player, String name, double energy, double health, int speed, double damage, int range, Color color) {
        this.player = player;
        this.name = name;
        this.energy = energy;
        this.health = health;
        initHealth = health;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.color = color;
        id = num ++;
    }

    /**
     * This method is for setting damage
     * @param damage
     */


    public void takeDamage(double damage) {
        double newHealth = this.health - damage;
        if(newHealth <= 0) {
            dead();
            health = 0;
        }
        else {
            health = newHealth;
        }
        healthBarTower.setProgress(health / initHealth);
    }

    /**
     * This method is for increasing health
     * @param healthPlus
     */

    public void getPower(double healthPlus){
        double newHealth = this.health + healthPlus;
        health = Math.min(newHealth, initHealth);
        healthBarTower.setProgress(health / initHealth);
    }

    public void setXY(double x,double y){
        this.circle = new Circle(10);
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDamaged() {
        return initHealth != health;
    }


    public Tile getTile() {
        return tile;
    }

    public String getName() {
        return name;
    }

    public double getEnergy() {
        return energy;
    }

    public double getDamage() {
        return damage;
    }

    public double getHealth() {
        return health;
    }

    public int getRange() {
        return range;
    }

    public int getSpeed() {
        return speed;
    }

    public BorderPane getSoldierPane() {
        healthBarTower.setProgress(1);
        healthBarTower.setPrefWidth(tile.getHeight());
        soldierPane.setCenter(circle);
        circle.setFill(this.color);
        healthBarTower.setStyle("-fx-accent: green;");
        healthBarTower.setPrefHeight(tile.getHeight() / 5);
        soldierPane.setTop(healthBarTower);
        soldierPane.setLayoutX(tile.getX());
        soldierPane.setLayoutY(tile.getY());
        return soldierPane;
    }

    public void setTile(Tile tile,Map map) {
        this.setXY(tile.getX() + tile.getHeight() / 2,tile.getY() + tile.getHeight() / 2);
        this.tile = tile;
        this.map = map;
    }

    public void changeTile(Tile tile){
        this.tile = tile;
    }
    public void changeLast() {
        this.last = tile;
    }

    public Player getPlayer() {
        return player;
    }

    public void moveToAnotherTile(Tile tile) {
        this.tile.removeSoldier(this);
        tile.addSoldier(this);
        if(tile.getTileType().equals(TypeOfTiles.RED)) {
            Main.getPlayer(1 - player.getPlayerId()).hit();
        }
        changeLast();
        changeTile(tile);
        this.soldierPane.setLayoutX(tile.getX());
        this.soldierPane.setLayoutY(tile.getY());
    }

    public void dead() {
        alive = false;
        this.tile.removeSoldier(this);
        Platform.runLater(()-> {
            map.removeFromMap(this.soldierPane);
        });
    }


    public Tile getLast() {
        return last;
    }

    public void move(){
        try {
            Thread.sleep(ONE_SECOND / speed);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        Tower tower = tile.findTower(range,1 - player.getPlayerId());
        Soldier soldier = tile.soldierFinder(range,1 - player.getPlayerId());
        if(tower != null){
            try {
                Thread.sleep(ONE_SECOND);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(alive) {
                tower.takeDamage(damage);
            }
        }
        else if(soldier != null){
            try {
                Thread.sleep(ONE_SECOND);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(alive) {
                soldier.takeDamage(damage);
            }
        }
        else{
            ArrayList<Tile> neighbours = tile.nextTiles(player.getPlayerId());
            neighbours.remove(last);
            if(neighbours.size() == 0){
                dead();
            }
            else {
                moveToAnotherTile(neighbours.get(0));
            }
            neighbours.clear();
        }
    }

    @Override
    public void run() {
        while(alive) {
            Tower tower = tile.findTower(range,1 - player.getPlayerId());
            Soldier soldier = tile.soldierFinder(range,1 - player.getPlayerId());
            if(tower != null){
                try {
                    Thread.sleep(ONE_SECOND);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(alive) {
                    tower.takeDamage(damage);
                }
            }
            else if(soldier != null){
                try {
                    Thread.sleep(ONE_SECOND);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(alive) {
                    soldier.takeDamage(damage);
                }
            }
            else {
                move();
            }
        }
    }
}
