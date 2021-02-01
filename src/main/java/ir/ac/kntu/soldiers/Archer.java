package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Archer extends Soldier implements Serializable {
    public Archer(Player player) {
        super(player,"Archer",15,300,1,200,2, Color.PURPLE);
    }
}
