package ir.ac.kntu.soldiers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ir.ac.kntu.Player;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public enum Soldiers implements Serializable {
    ARCHER(new Archer(null),"/home/helix/Desktop/Castle-Defence-Test/images/archer.png"),
    DRAGON(new Dragon(null),"/home/helix/Desktop/Castle-Defence-Test/images/dragon.png"),
    GOBLIN(new Goblin(null),"/home/helix/Desktop/Castle-Defence-Test/images/goblin.png"),
    BARBARIANS(new Barbarians(null),"/home/helix/Desktop/Castle-Defence-Test/images/barbarians.png"),
    BOMBER(new Bomber(null),"/home/helix/Desktop/Castle-Defence-Test/images/bomber.png"),
    KNIGHT(new Knight(null),"/home/helix/Desktop/Castle-Defence-Test/images/knight.png"),
    SHIELD(new Shield(null),"/home/helix/Desktop/Castle-Defence-Test/images/shield.png"),
    WIZARD(new Wizard(null),"/home/helix/Desktop/Castle-Defence-Test/images/wizard.png"),
    MUSKETEER(new Musketeer(null),"/home/helix/Desktop/Castle-Defence-Test/images/musketeer.png"),
    WITCH(new Witch(null),"/home/helix/Desktop/Castle-Defence-Test/images/witch.png"),
    GIANT(new Giant(null),"/home/helix/Desktop/Castle-Defence-Test/images/giant.png"),
    BANDIT(new Bandit(null),"/home/helix/Desktop/Castle-Defence-Test/images/bandit.png");

    private final Soldier soldier;
    private Image image;

    private final static int NUM_OF_SOLDIERS = 12;
    private static final int NUM_OF_SELECTED_CARDS = 4;

    Soldiers(Soldier soldier, String imgOfSoldier){
        this.soldier = soldier;
        try {
            FileInputStream fileInputStream = new FileInputStream(imgOfSoldier);
            this.image = new Image(fileInputStream);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public Soldier addSoldier(Player player) {
        if(soldier instanceof Archer) {
            return new Archer(player);
        }
        else if(soldier instanceof Dragon) {
            return new Dragon(player);
        }
        else if(soldier instanceof Goblin) {
            return new Goblin(player);
        }
        else if(soldier instanceof Musketeer) {
            return new Musketeer(player);
        }
        else if(soldier instanceof Knight) {
            return new Knight(player);
        }
        else if(soldier instanceof Shield) {
            return new Shield(player);
        }
        else if(soldier instanceof Barbarians) {
            return new Barbarians(player);
        }
        else if(soldier instanceof Witch) {
            return new Witch(player);
        }
        else if(soldier instanceof Giant) {
            return new Giant(player);
        }
        else if(soldier instanceof Bomber) {
            return new Bomber(player);
        }
        else if(soldier instanceof Wizard) {
            return new Wizard(player);
        }
        else if(soldier instanceof Bandit) {
            return new Bandit(player);
        }
        return null;
    }

    public static void selectSoldier(Player player, VBox vBox) {
        Button submitButton = new Button("Set");
        Label label = new Label("Select Soldiers For "+player.getName()+" :");
        vBox.getChildren().add(label);
        RadioButton[] radioButtons = new RadioButton[NUM_OF_SOLDIERS];
        for(int i = 0; i < Soldiers.NUM_OF_SOLDIERS; i++){
            radioButtons[i] = new RadioButton(Soldiers.values()[i].getName() + "\t\t" + "Damage: " + Soldiers.values()[i].getDamage() + "\t"
                            + " Range: " + Soldiers.values()[i].getRange() + "\t" + " Speed: " + Soldiers.values()[i].getVelocity() + "\t"
                            + " Health: " + Soldiers.values()[i].getHealth() + "\t" + " Energy: " + Soldiers.values()[i].getEnergy() + "\t");
            vBox.getChildren().add(radioButtons[i]);
        }
        submitButton.setOnAction(event -> {
            int numOfSelectedCards = 0;
            for(int i = 0; i< NUM_OF_SOLDIERS; i++){
                if(radioButtons[i].isSelected()){
                    numOfSelectedCards++;
                }
            }
            if(numOfSelectedCards == NUM_OF_SELECTED_CARDS){
                ArrayList<Soldiers> cards = new ArrayList<>();
                for(int i = 0; i< NUM_OF_SOLDIERS; i++){
                    if(radioButtons[i].isSelected()){
                        cards.add(Soldiers.values()[i]);
                    }
                }
                player.setSoldiers(cards);
                label.setText("Set selected soldiers for " + player.getName() + " .");
                vBox.getChildren().remove(submitButton);
            }
            else {
                label.setText("Select " + NUM_OF_SELECTED_CARDS + " soldiers for " + player.getName() + " : ");
            }
        });
        vBox.getChildren().add(submitButton);
    }

    public String getName() {
        return soldier.getName();
    }

    public double getEnergy() {
        return soldier.getEnergy();
    }

    public double getDamage() {
        return soldier.getDamage();
    }

    public double getHealth() {
        return soldier.getHealth();
    }

    public int getRange() {
        return soldier.getRange();
    }

    public int getVelocity() {
        return soldier.getSpeed();
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(this.image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
        return imageView;
    }
}
