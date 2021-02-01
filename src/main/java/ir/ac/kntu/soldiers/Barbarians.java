package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Barbarians extends Soldier implements Serializable {
    public Barbarians(Player player) {
        super(player,"Barbarians",20,500,1,350,1, Color.SILVER);
    }
}
