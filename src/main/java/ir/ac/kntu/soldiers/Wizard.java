package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Wizard extends Soldier implements Serializable {
    public Wizard(Player player){
        super(player,"Wizard",30,1000,2,100,1, Color.BLUE);
    }
}
