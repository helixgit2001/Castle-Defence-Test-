package ir.ac.kntu.soldiers;

import javafx.scene.paint.Color;
import ir.ac.kntu.Player;
import ir.ac.kntu.map.Tile;
import ir.ac.kntu.towers.Tower;

import java.io.Serializable;
import java.util.ArrayList;

public class Musketeer extends Soldier implements Serializable {
    private final double POWER = 100;

    public Musketeer(Player player) {
        super(player,"Musketeer",30,300,1,0,3, Color.MINTCREAM);
    }
    @Override
    public void move(){
        try {
            Thread.sleep(1000);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        Tower tower = getTile().findADamagedTower(getRange(), getPlayer().getPlayerId());
        Soldier soldier = getTile().findDamagedSoldiers(getRange(), getPlayer().getPlayerId());
        if(tower != null) {
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(isAlive()) {
                tower.getPower(POWER);
            }
        }
        else if(soldier != null) {
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(isAlive()) {
                soldier.getPower(POWER);
            }
        }
        else{
            ArrayList<Tile> neighbours = getTile().nextTiles(getPlayer().getPlayerId());
            neighbours.remove(getLast());
            if(neighbours.size() == 0){
                dead();
            }
            else {
                moveToAnotherTile(neighbours.get(0));
            }
            neighbours.clear();
        }
    }

    @Override
    public void run() {
        while(isAlive()) {
            Tower tower = getTile().findADamagedTower(getRange(), getPlayer().getPlayerId());
            Soldier soldier = getTile().findDamagedSoldiers(getRange(), getPlayer().getPlayerId());
            if(tower != null) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(isAlive()) {
                    tower.getPower(POWER);
                }
            }
            else if(soldier != null){
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if(isAlive()) {
                    soldier.getPower(POWER);
                }
            }
            else {
                move();
            }
        }

    }
}
