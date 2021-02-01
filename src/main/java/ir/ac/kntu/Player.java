package ir.ac.kntu;

import ir.ac.kntu.map.Tile;
import ir.ac.kntu.soldiers.Giant;
import ir.ac.kntu.soldiers.Soldier;
import ir.ac.kntu.soldiers.Soldiers;
import ir.ac.kntu.soldiers.Witch;
import ir.ac.kntu.towers.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Runnable , Serializable {
    private static final double ONE_MIN = 1000;
    private static final double MAX_ENERGY = 100d;
    private static final int SLEEP_TIME = 100;
    private static final int NUM_OF_TOWERS = 3;
    private static int num = 0;

    private final ArrayList<Thread> threads = new ArrayList<>();
    private Soldier selectedSoldier;
    private final int playerId;
    private double extraEnergy = 10d;
    private boolean running = true;
    private int lives = 3;
    private String name;
    private double energy;
    private final Label livesLab = new Label("Lives : " + lives);
    private final Label nameLab = new Label();
    private final ProgressBar energyBar = new ProgressBar();
    private final TilePane playerPane = new TilePane(energyBar, livesLab, nameLab);
    private ArrayList <Soldiers> soldiers = new ArrayList<>();
    private final ArrayList <Tower> towers = new ArrayList<>();
    private final ArrayList <Tile> reds = new ArrayList<>();
    private final VBox towersList = new VBox();
    private final VBox soldiersList = new VBox();

    public Player(){
        this.playerId = num++;
        this.energy = 0;
        energyBar.setPrefSize(180,10);
        energyBar.setStyle("-fx-accent: green;");
        for(int i = 0; i< NUM_OF_TOWERS; i++){
            towers.add(new BlackTower(playerId));
            towers.add(new PowerTower(playerId));
            towers.add(new ElectricTower(playerId));
        }
        towers.add(new BuilderTower(playerId));
        for (Tower tower : towers) {
            towersList.getChildren().add(tower.getImageView());
        }
        this.towersList.setOnMouseClicked(event -> {
            Main.setThisTower(towers.get((int)event.getY() / Tower.IMAGE_HEIGHT));
            Main.setThisPlayer(this);
        });
        this.soldiersList.setOnMouseClicked(event -> Main.setThisSoldiers(soldiers.get((int)(event.getY() / 64d)).addSoldier(this)));
    }

    @Override
    public void run() {
        while(running) {
            try{
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            addEnergy();
            Platform.runLater(()->{
                energyBar.setProgress(getEnergy() / 100);
                livesLab.setText("Lives : " + lives);
            });
            for (Thread thread :threads){
                try {
                    if(thread.isInterrupted()){
                        System.out.println("interrupt");
                        threads.remove(thread);
                     }
                } catch (Exception e){
                    System.out.println("can't remove");
                }
            }
        }
        Main.stopGame();
    }

    public int getLives() {
        return lives;
    }

    public String getName() {
        return name;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setName(HBox box) {
        Label name = new Label("Enter your Name :");
        TextField nameField = new TextField();
        nameField.setPrefWidth(200);
        Button accept = new Button("set");
        accept.setOnAction(e -> {
            if(nameField.getText().trim().equals("")){
                name.setText("Enter Your Name : * This fields can't be empty *");
            }
            else {
                this.name = nameField.getText().trim();
                box.getChildren().remove(accept);
                name.setText("* Your entered name set *");
            }
        });
        box.getChildren().addAll(name,nameField,accept);
    }

    public double getEnergy() {
        return energy;
    }

    public void setThisSoldiers(Soldier selectedSoldier) {
        this.selectedSoldier = selectedSoldier;
    }

    public Soldier getThisSoldiers() {
        return selectedSoldier;
    }

    public TilePane getPlayerPane() {
        return playerPane;
    }

    public ArrayList<Soldiers> getSoldiers() {
        return soldiers;
    }

    public VBox getTowers() {
        return towersList;
    }

    public void addRedTile(Tile tile){
        reds.add(tile);
    }

    public ArrayList<Tile> getReds() {
        return reds;
    }

    public void addEnergy() {
        double newEnergy = this.energy + extraEnergy * SLEEP_TIME / ONE_MIN;
        this.energy = Math.min(newEnergy, MAX_ENERGY);
    }

    public Boolean decreaseEnergy(double dec) {
        double newEnergy = this.energy - dec;
        if(newEnergy >= 0){
            this.energy = newEnergy ;
            return true;
        }
        return false;
    }

    public void setSoldiers(ArrayList<Soldiers> soldiers) {
        boolean isGiant = false;
        boolean isWitch = false;
        this.soldiers = soldiers;
        for(Soldiers soldiersList : soldiers){
            this.soldiersList.getChildren().add(soldiersList.getImageView());
            if(soldiersList.getSoldier() instanceof Giant){
                isGiant = true;
            }
            else if(soldiersList.getSoldier() instanceof Witch){
                isWitch = true;
            }
        }
        if(isGiant && isWitch) {
            nameLab.setText(name + " Giant entered! ");
        }
        else if(isGiant) {
            nameLab.setText(name + " is a Giant! ");
        }
        else if(isWitch) {
            nameLab.setText(name + " Witch entered! ");
        }
        else {
            nameLab.setText(name);
        }
    }

    public VBox getListOfSoldiers() {
        return soldiersList;
    }

    public void pickedBuilder(){
        this.extraEnergy /= 4;
    }

    public void removeTower(Tower tower){
        towers.remove(tower);
    }

    public void hit(){
        this.lives--;
        if(lives == 0) {
            running = false;
        }
    }

    public ArrayList <Thread> getThreads(){
        return threads;
    }

    public void addThreads(Thread thread){
        threads.add(thread);
    }
}
