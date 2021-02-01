package ir.ac.kntu.towers;

import ir.ac.kntu.soldiers.Soldier;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.map.Tile;

import java.io.FileInputStream;
import java.io.Serializable;

public class Tower implements Runnable, Serializable {
    public static final int IMAGE_HEIGHT = 64;

    private String name;
    private final int player;
    private final double initHealth;
    private double health;
    private double damage;
    private int energy;
    private int range;
    private Map map;
    private TypeOfTowers type;
    private Tile tile;
    private Image image;
    private ImageView imageView;
    private BorderPane towerPane = new BorderPane();
    private final ProgressBar healthBar = new ProgressBar();
    private boolean alive = true;

    public Tower(int player, TypeOfTowers type, String name, double health, int energy, int range, double damage, String image){
        healthBar.setStyle("-fx-accent: green;");
        this.player = player;
        initHealth = health;
        this.type = type;
        this.name = name;
        this.damage = damage;
        this.energy = energy;
        this.health = health;
        this.range = range;
        try {
            FileInputStream fis = new FileInputStream(image);
            this.image = new Image(fis);
            this.imageView = new ImageView(this.image);
            imageView.setFitHeight(IMAGE_HEIGHT);
            imageView.setFitWidth(IMAGE_HEIGHT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Tower(int player, TypeOfTowers type, double health, String image) {
        this.player = player;
        this.type = type;
        try {
            FileInputStream fileInputStream = new FileInputStream(image);
            this.image = new Image(fileInputStream);
            this.imageView = new ImageView(this.image);
            imageView.setFitHeight(64);
            imageView.setFitWidth(64);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.health = health;
        initHealth = health;
    }

    public void takeDamage(double damage) {
        double plusHealth = this.health - damage;
        if(plusHealth <= 0){
            dead();
            health = 0;
        }
        else {
            health = plusHealth;
        }
        healthBar.setProgress(health / initHealth);
    }

    public void getPower(double extraHealth){
        double newHealth = this.health + extraHealth;
        health = Math.min(newHealth, initHealth);
        healthBar.setProgress(health / initHealth);
    }

    public BorderPane getTowerPane(Tile tile) {
        healthBar.setProgress(1);
        imageView.setFitHeight(tile.getHeight() - 16);
        imageView.setFitWidth(tile.getHeight());
        healthBar.setPrefWidth(tile.getHeight());
        healthBar.setPrefHeight(tile.getHeight() / 5);
        towerPane.setCenter(imageView);
        healthBar.setStyle("fx-accent: green;");
        towerPane.setTop(healthBar);
        towerPane.setLayoutX(tile.getX());
        towerPane.setLayoutY(tile.getY());
        return towerPane;
    }

    public TypeOfTowers getType() {
        return type;
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

    public int getRange() {
        return range;
    }

    public int getPlayer() {
        return player;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setTile(Tile tile, Map map) {
        this.tile = tile;
        this.map = map;
    }

    public void dead(){
        this.alive = false;
        Platform.runLater(()->{
            map.removeFromMap(this.towerPane);
        });
        this.tile.removeTower();
    }

    public void shoot(Soldier soldier){
        if(soldier != null){
            soldier.takeDamage(damage);
        }
    }

    @Override
    public void run() {
        while(alive){
            try{
                Thread.sleep(1000);
            } catch (Exception e){

            }
            if(alive) {
                shoot(tile.soldierFinder(range,1 - player));
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
