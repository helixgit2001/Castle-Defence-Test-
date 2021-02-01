package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Goblin extends Soldier implements Serializable {
    public Goblin(Player player) {
        super(player,"Goblin",10,200,3,350,1, Color.GREEN);
    }
}
