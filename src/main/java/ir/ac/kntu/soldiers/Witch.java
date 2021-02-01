package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;

import java.io.Serializable;

public class Witch extends Soldier implements Serializable {
    public Witch(Player player){
        super(player,"Witch",50,300,1,800,3, Color.BROWN);
    }

}
