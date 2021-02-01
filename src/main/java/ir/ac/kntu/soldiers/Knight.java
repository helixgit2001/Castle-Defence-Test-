package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Knight extends Soldier implements Serializable {
    public Knight(Player player){
        super(player,"Knight",30,600,2,400,1, Color.BLACK);
    }
}
