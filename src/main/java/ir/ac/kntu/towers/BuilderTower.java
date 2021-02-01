package ir.ac.kntu.towers;

import ir.ac.kntu.Main;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.map.Tile;
import ir.ac.kntu.soldiers.Soldier;
import javafx.application.Platform;
import ir.ac.kntu.Player;
import ir.ac.kntu.RandomHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class BuilderTower extends Tower implements Serializable {

    private static final int SLEEP_TIME = 5000;

    public BuilderTower(int player){
        super(player , TypeOfTowers.BUILDER_Tower,500, "/home/helix/Desktop/Castle-Defence-Test/images/builder.png");
    }

    @Override
    public void run() {
        Player player = Main.getPlayer(getPlayer());
        Map map = Main.getMap();
        while(isAlive()) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ArrayList<Tile> reds = new ArrayList<>(player.getReds());
            Collections.shuffle(reds);
            for(Tile tile : reds){
                if(!tile.hasSoldier()) {
                    int random = RandomHelper.nextInt(4);
                    Soldier soldier = Main.getPlayer(1 - getPlayer()).getSoldiers().get(random).addSoldier(player);
                    tile.addSoldier(soldier);
                    soldier.setTile(tile, map);
                    Platform.runLater(()-> { map.getGroup().getChildren().add(soldier.getSoldierPane()); });
                    Thread thread = new Thread(soldier);
                    thread.start();
                    player.addThreads(thread);
                    break;
                }
            }
            reds.clear();
        }
    }
}
