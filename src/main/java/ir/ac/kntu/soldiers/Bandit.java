package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Bandit extends Soldier implements Serializable {
    public Bandit(Player player) {
        super(player,"Bandit",5,10,2,1,1, Color.GOLD);
    }
}
