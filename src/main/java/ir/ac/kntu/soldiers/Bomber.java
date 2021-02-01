package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Bomber extends Soldier implements Serializable {
    public Bomber(Player player) {
        super(player,"Bomber",20,250,3,400,2, Color.CRIMSON);
    }
}
