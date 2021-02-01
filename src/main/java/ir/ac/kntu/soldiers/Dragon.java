package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Dragon extends Soldier implements Serializable {
    public Dragon(Player player) {
        super(player,"Dragon",35,500,2,350,3, Color.RED);
    }
}
