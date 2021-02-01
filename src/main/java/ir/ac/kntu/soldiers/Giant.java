package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Giant extends Soldier implements Serializable {
    public Giant(Player player) {
        super(player,"Giant",50,300,1,800,3, Color.PINK);
    }

}
