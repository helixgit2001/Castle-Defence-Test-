package ir.ac.kntu.map;

import ir.ac.kntu.Main;
import ir.ac.kntu.soldiers.Soldier;
import ir.ac.kntu.towers.Tower;
import ir.ac.kntu.towers.TypeOfTowers;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import ir.ac.kntu.Player;

import java.io.*;

public class Map implements Serializable {
    // fields
    private final Tile[][] map;
    private int[][] table;
    private final int length;
    private final int tileHeight;
    private final Group group = new Group();
    private static final int WIDTH = 600;

    // constructors
    public Map(int[][] table, int length){
        this.length = length;
        this.table = table;
        this.map = new Tile[length][length];
        this.tileHeight = (WIDTH / length);
        this.group.setLayoutX(tileHeight * length);
        this.group.setLayoutX(tileHeight * length);
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                map[i][j] = new Tile(TypeOfTiles.values()[table[i][j]], j, i, WIDTH / length, this, length);group.getChildren().add(map[i][j]);
            }
        }
    }

    public Map(int length){
        this.length = length;
        this.tileHeight = ((int)Math.floor(WIDTH / length));
        this.map = new Tile[length][length];
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                map[i][j] = new Tile(TypeOfTiles.values()[0], j, i,WIDTH / length);
                group.getChildren().add(map[i][j]);
            }
        }
    }

    /**
     * This method used for reading map
     * @param nameOfMap
     * @return
     * @throws Exception
     */


    public static Map readMap(String nameOfMap) throws Exception {
        File file = new File("/home/helix/Desktop/Castle-Defence-Test/maps/" + nameOfMap + ".map");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = bufferedReader.readLine();
        int length = Integer.parseInt(line);

        int[][] mapArray = new int[length][length];

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                mapArray[i][j] = Integer.parseInt(bufferedReader.readLine());
            }
        }
        bufferedReader.close();
        return new Map(mapArray,length);
    }

    /**
     * This method used for writing new map
     * @param nameOfMap
     * @throws Exception
     */

    public void writeMap(String nameOfMap) throws Exception{
        File file = new File("/home/helix/Desktop/Castle-Defence-Test/maps/" + nameOfMap + ".map");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bufferedWriter.write(this.length+"");
        bufferedWriter.newLine();
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                bufferedWriter.write(this.map[i][j].getTileType().ordinal()+"");
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
    };

    /**
     * This method set each tiles color
     * @return
     */

    public Group setMapTilesColor() {
        this.group.setOnMouseClicked(event -> {
            this.map[(int)event.getY() / tileHeight][(int)event.getX() / tileHeight].nextColor();
        });
        return group;
    }

    /**
     * This method set towers in map with mouse
     * @return
     */

    public Group setTowers(){
        this.group.setOnMouseClicked(event -> {
            Tower tower = Main.getThisTower();
            Player player = Main.getThisPlayer();
            Tile tile = this.map[(int)event.getY() / tileHeight][(int)event.getX() / tileHeight];
            if(tower != null && tile.isEmpty()) {
                if((tile.getTileType().equals(TypeOfTiles.GREEN) && tower.getType().equals(TypeOfTowers.BUILDER_Tower)) ||
                    (tile.getTileType().equals(TypeOfTiles.BLUE) && !tower.getType().equals(TypeOfTowers.BUILDER_Tower))) {
                    if(tile.getTileType().equals(TypeOfTiles.GREEN)) {
                        player.pickedBuilder();
                    }
                    tile.setTower(tower);
                    tower.setTile(tile,this);
                    player.removeTower(tower);
                    Thread thread = new Thread(tower);
                    thread.start();
                    player.addThreads(thread);
                    Main.setThisPlayer(null);
                    Main.setThisTower(null);
                    group.getChildren().add(tower.getTowerPane(tile));
                }
            }
        });
        return group;
    }

    /**
     * This method set soldiers in their starting position with mouse
     * @return a group of soldiers
     */

    public Group setSoldiers() {
        this.group.setOnMouseClicked(event -> {
            Soldier soldier = Main.getThisSoldiers();
            Tile tile = this.map[(int)event.getY() / tileHeight][(int)event.getX() / tileHeight];
            if(soldier != null && !tile.hasSoldier()) {
                if(tile.getTileType().equals(TypeOfTiles.RED)&&
                    soldier.getPlayer().decreaseEnergy(soldier.getEnergy())){
                    soldier.setTile(tile,this);
                    group.getChildren().add(soldier.getSoldierPane());
                    Thread thread = new Thread(soldier);
                    thread.start();
                    soldier.getPlayer().addThreads(thread);
                    Main.setThisSoldiers(null);
                }
            }
        });
        return group;
    }

    /**
     * This method set soldiers of each player in map according g to tile id
     * @param player
     * @param tileId
     */

    public void setSoldiersInMap(Player player, int tileId) {
        Soldier soldier = player.getThisSoldiers();
        Tile tile ;
        try {
            tile = player.getReds().get(tileId);
        } catch (IndexOutOfBoundsException e){
            return;
        }
        if(soldier != null && !tile.hasSoldier()) {
            if(soldier.getPlayer().decreaseEnergy(soldier.getEnergy())) {
                soldier.setTile(tile,this);
                tile.addSoldier(soldier);
                group.getChildren().add(soldier.getSoldierPane());
                Thread thread = new Thread(soldier);
                thread.start();
                player.addThreads(thread);
                player.setThisSoldiers(null);
            }
        }
    }

    /**
     * This method set soldiers in starting red tiles of each soldiers
     * @param player1
     * @param player2
     */

    public void setRedTiles(Player player1, Player player2) {
        for(int i = 0; i < length; i++) {
            if (map[0][i].getTileType().equals(TypeOfTiles.RED)) {
                player2.addRedTile(map[0][i]);
            }
        }

        for(int i = 0; i < length; i++) {
            if(map[length - 1][i].getTileType().equals(TypeOfTiles.RED)) {
                player1.addRedTile(map[length - 1][i]);
            }
        }
    }

    /**
     * This method remove object from map
     * @param object
     */


    public void removeFromMap(BorderPane object) {
        group.getChildren().remove(object);
    }

    public Group getGroup() {
        return group;
    }

    public int getLength() {
        return length;
    }

    public int[][] getTable() {
        return table;
    }

    public Tile[][] getMap() {
        return map;
    }
}
