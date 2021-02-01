package ir.ac.kntu.towers;

import ir.ac.kntu.soldiers.Soldier;

import java.io.Serializable;

public class PowerTower extends Tower implements Serializable {
    private final double POWER = 100;

    public PowerTower(int player) {
        super(player, TypeOfTowers.POWER_TOWER,"Power",2000,40,3,0,"/home/helix/Desktop/Castle-Defence-Test/images/power.png");
    }

    public void power(Soldier soldier) {
        if(soldier != null){
            soldier.getPower(POWER);
        }
    }

    @Override
    public void run() {
        while(isAlive()){
            try{
                Thread.sleep(1000);
            } catch (Exception e){

            }
            if(isAlive()) {
                power(getTile().soldierFinder(getRange(), getPlayer()));
            }
        }
    }
}
