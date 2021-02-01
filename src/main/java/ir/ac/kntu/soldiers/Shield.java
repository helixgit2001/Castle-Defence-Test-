package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Shield extends Soldier implements Serializable {
    public Shield(Player player) {
        super(player,"Shield",10,1000,1,150,1, Color.CHOCOLATE);
    }
}
