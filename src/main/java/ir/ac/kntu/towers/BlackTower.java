package ir.ac.kntu.towers;

import java.io.Serializable;

public class BlackTower extends Tower implements Serializable {
    public BlackTower(int player) {
        super(player, TypeOfTowers.BLACK_Tower, "Black", 2000, 40, 2, 300, "/home/helix/Desktop/Castle-Defence-Test/images/black.png");
    }

}
