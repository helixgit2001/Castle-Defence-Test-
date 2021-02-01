package ir.ac.kntu.map;

import ir.ac.kntu.soldiers.Soldier;
import ir.ac.kntu.towers.Tower;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ir.ac.kntu.RandomHelper;

import java.io.Serializable;
import java.util.ArrayList;


public class Tile extends Rectangle implements Serializable {
    // fields
    private TypeOfTiles type;
    private Tower tower;
    private final int x;
    private final int y;
    private int length;
    private Map map;
    private final ArrayList<Soldier> soldiers = new ArrayList<>();

    // constructors

    /**
     * This constructor has three input type, x, y, dimensions
     * @param type
     * @param x
     * @param y
     * @param dimensions
     */

    public Tile(TypeOfTiles type, int x, int y, int dimensions){
        super(x * dimensions,y * dimensions, dimensions, dimensions);
        this.x = x;
        this.y = y;
        this.type = type;
        this.setFill(type.getTileColor());
        this.setStroke(Color.BLACK);
    }

    /**
     *
     * @param typeOfTiles
     * @param x
     * @param y
     * @param height
     * @param map
     * @param length
     */

    public Tile(TypeOfTiles typeOfTiles, int x, int y, int height, Map map, int length){
        super(x * height,y * height, height, height);
        this.length = length;
        this.map = map;
        this.x = x;
        this.y = y;
        this.type = typeOfTiles;
        this.setFill(typeOfTiles.getTileColor());
        this.setStroke(Color.BLACK);
    }

    /**
     * This method fill each tile with color
     */

    public void nextColor(){
        this.type = TypeOfTiles.values()[(this.type.ordinal() + 1) % 5];
        this.setFill(type.getTileColor());
    }

    public void setTower(Tower tower){
        this.tower = tower;
    }

    public TypeOfTiles getTileType() {
        return type;
    }

    /**
     * This method check tile has soldier or not
     * @return
     */

    public boolean hasSoldier(){
        return soldiers.size() != 0;
    }

    /**
     * This method find next tiles
     * @param id
     * @return array list of near tiles
     */

    public ArrayList<Tile> nextTiles(int id) {
        ArrayList<Tile> tiles = new ArrayList<>();
        if(id == 1 && y + 1 < length) {
            if(map.getTable()[y + 1][x] == 2 || map.getTable()[y + 1][x] == 3 ){
                tiles.add(map.getMap()[y + 1][x]);
            }
        }
        if(id == 0 && y - 1 >= 0) {
            if (map.getTable()[y - 1][x] == 2 || map.getTable()[y - 1][x] == 3) {
                tiles.add(map.getMap()[y - 1][x]);
            }
        }

        boolean randomBool = RandomHelper.nextBoolean();
        if(randomBool) {
            if(x + 1 < length){
                if (map.getTable()[y][x + 1] == 2 || map.getTable()[y][x + 1] == 3) {
                    tiles.add(map.getMap()[y][x + 1]);
                }
            }
            if(x - 1 >= 0) {
                if (map.getTable()[y][x - 1] == 2 || map.getTable()[y][x - 1] == 3) {
                    tiles.add(map.getMap()[y][x - 1]);
                }
            }
        }
        else {
            if(x - 1 >= 0) {
                if (map.getTable()[y][x - 1] == 2 || map.getTable()[y][x - 1] == 3) {
                    tiles.add(map.getMap()[y][x - 1]);
                }
            }
            if(x + 1 < length) {
                if (map.getTable()[y][x + 1] == 2 || map.getTable()[y][x + 1] == 3) {
                    tiles.add(map.getMap()[y][x + 1]);
                }
            }
        }

        if(id == 1 && y - 1 >= 0) {
            if(map.getTable()[y - 1][x] == 2 || map.getTable()[y - 1][x] == 3 ) {
                tiles.add(map.getMap()[y - 1][x]);
            }
        }
        if(id == 0 && y + 1 < length) {
            if(map.getTable()[y + 1][x] == 2 || map.getTable()[y + 1][x] == 3 ) {
                tiles.add(map.getMap()[y + 1][x]);
            }
        }
        return new ArrayList<Tile>(tiles);
    }

    /**
     * This method find a soldier according to range and player id
     * @param range
     * @param playerId
     * @return a soldier
     */

    public Soldier soldierFinder(int range, int playerId) {
        if(range > 1){
            for(int i = -range; i <= range; i++) {
                if(x + i< 0 || x + i >= map.getLength()) {
                    continue;
                }
                for(int j = -range; j <= range; j++) {
                    if(y + j < 0 || y + j >= map.getLength()) {
                        continue;
                    }
                    if(map.getTable()[y + j][x + i] == 2) {
                        Soldier soldier = map.getMap()[y + j][x + i].getSoldier(playerId);
                        if(soldier != null) {
                            return soldier;
                        }
                    }
                }
            }
        }
        else {
            for(int i = -1; i <= 1; i++) {
                if(x + i < 0 || x + i >= map.getLength()) {
                    continue;
                }
                if(map.getTable()[y][x + i] == 2) {
                    Soldier soldier = map.getMap()[y][x + i].getSoldier(playerId);
                    if(soldier != null){
                        return soldier;
                    }
                }
            }
            for(int j = -1; j <= 1; j++) {
                if(y + j < 0 || y + j >= map.getLength()) {
                    continue;
                }
                if(map.getTable()[y + j][x] == 2) {
                    Soldier soldier = map.getMap()[y + j][x].getSoldier(playerId);
                    if(soldier != null) {
                        return soldier;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method find damaged soldier
     * @param range
     * @param playerId
     * @return a damaged soldier
     */
    
    public Soldier findDamagedSoldiers(int range, int playerId) {
        for(int i = -range; i <= range; i++){
            if(x + i < 0 || x + i >= map.getLength()) {
                continue;
            }
            for(int j = -range; j <= range; j++){
                if(y + j < 0 || y + j >= map.getLength()){
                    continue;
                }
                if(map.getTable()[y + j][x + i] == 2){
                    Soldier soldier = map.getMap()[y + j][x + i].getSoldier(playerId);
                    if(soldier != null && soldier.isDamaged()){
                        return soldier;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method find tower
     * @param range
     * @param playerId
     * @return
     */

    public Tower findTower(int range, int playerId) {
        if(range > 1){
            for(int i = -range; i <= range; i++) {
                if(x + i < 0 || x + i >= map.getLength()) {
                    continue;
                }
                for(int j = -range; j <= range; j++) {
                    if(y + j < 0 || y + j >= map.getLength()) {
                        continue;
                    }
                    if(map.getTable()[y + j][x + i] == 1 || map.getTable()[y + j][x + i] == 4) {
                        Tower tower = map.getMap()[y + j][x + i].getTower(playerId);
                        if(tower != null) {
                            return tower;
                        }
                    }
                }
            }
        }
        else {
            for(int i = -range; i <= range; i++) {
                if(x + i< 0 || x + i >= map.getLength()) {
                    continue;
                }
                if(map.getTable()[y][x + i] == 1 || map.getTable()[y][x + i] == 4) {
                    Tower tower = map.getMap()[y][x + i].getTower(playerId);
                    if(tower != null) {
                        return tower;
                    }
                }
            }
            for(int j = -range; j <= range; j++) {
                if(y + j < 0 || y + j >= map.getLength()) {
                    continue;
                }
                if(map.getTable()[y + j][x] == 1 || map.getTable()[y + j][x] == 4) {
                    Tower tower = map.getMap()[y + j][x].getTower(playerId);
                    if(tower != null) {
                        return tower;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method find damaged tower
     * @param range
     * @param playerId
     * @return a damaged tower
     */

    public Tower findADamagedTower(int range, int playerId) {
        for(int i = -range; i <= range; i++) {
            if(x + i < 0 || x + i >= map.getLength()) {
                continue;
            }
            for(int j = -range; j <= range; j++) {
                if(y + j < 0 || y + j >= map.getLength()) {
                    continue;
                }
                if(map.getTable()[y + j][x + i] == 1) {
                    Tower tower = map.getMap()[y + j][x + i].getTower(playerId);
                    if(tower != null && tower.isDamaged()) {
                        return tower;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method get soldiers according to player id
     * @param playerId
     * @return a soldier
     */

    public Soldier getSoldier(int playerId) {
        for(Soldier soldier : soldiers) {
            if(soldier != null) {
                if (soldier.getPlayer().getPlayerId() == playerId) {
                    return soldier;
                }
            }
        }
        return null;
    }

    /**
     * This method get towers according to player id
     * @param playerId
     * @return  a tower
     */


    public Tower getTower(int playerId) {
        if(isEmpty())
            return null;
        if(tower.getPlayer() == playerId) {
            return tower;
        }
        return null;
    }

    /**
     * This method check if the tile is empty or not
     * @return a bool
     */

    public boolean isEmpty() {
        return tower == null;
    }

    /**
     * This method remove soldier from a tile
     * @param soldier This soldier remove from tile
     */

    public void removeSoldier(Soldier soldier){
        soldiers.remove(soldier);
    }

    /**
     * This method add soldier to tile
     * @param soldier This soldier add to tile
     */

    public void addSoldier(Soldier soldier){
        soldiers.add(soldier);
    }

    /**
     * This method remove tower from tile
     */

    public void removeTower(){
        this.tower= null;
    }


    @Override
    public String toString() {
        return x + " : " + y;
    }

    @Override
    public boolean equals(Object obj) {
        Tile newTile = (Tile)obj;
        return this.getX() == newTile.getX() && this.getY() == newTile.getY();
    }
}
