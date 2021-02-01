package ir.ac.kntu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ir.ac.kntu.soldiers.Soldier;
import ir.ac.kntu.soldiers.Soldiers;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.towers.Tower;
import java.io.*;

public class Main extends Application {
    private static final int HEIGHT = 700;
    private static final int WIDTH = 1000;
    private static Map map;
    private Map newMap;
    private static Player player1;
    private static Player player2;
    private static Thread player1Thread;
    private static Thread player2Thread;
    private static Tower thisTower;
    private static Player thisPlayer;
    private static Soldier thisSoldier;
    private BorderPane beginningPane;
    private Pane mapPane;
    private BorderPane cardsSelectionPane;
    private BorderPane setTowersPane;
    private BorderPane mapPaneCreation;
    private BorderPane groundPane;
    private Scene beginningScene;
    private Scene mapScene;
    private Scene cardsSelectionScene;
    private Scene setTowersScene;
    private Scene mapSceneCreation;
    private Scene groundScene;

    public static void main(String[] args) {
        try {
            SocketClass.run();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        initSet(primaryStage);
        primaryStage.setTitle("Castle Defence");
        primaryStage.show();
    }


    public void keyBoardController(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    player1.setThisSoldiers(player1.getSoldiers().get(0).addSoldier(player1));
                    break;
                case DIGIT2:
                    player1.setThisSoldiers(player1.getSoldiers().get(1).addSoldier(player1));
                    break;
                case DIGIT3:
                    player1.setThisSoldiers(player1.getSoldiers().get(2).addSoldier(player1));
                    break;
                case DIGIT4:
                    player1.setThisSoldiers(player1.getSoldiers().get(3).addSoldier(player1));
                    break;
                case DIGIT5:
                    player2.setThisSoldiers(player2.getSoldiers().get(0).addSoldier(player2));
                    break;
                case DIGIT6:
                    player2.setThisSoldiers(player2.getSoldiers().get(1).addSoldier(player2));
                    break;
                case DIGIT7:
                    player2.setThisSoldiers(player2.getSoldiers().get(2).addSoldier(player2));
                    break;
                case DIGIT8:
                    player2.setThisSoldiers(player2.getSoldiers().get(3).addSoldier(player2));
                    break;
                case Q:
                    map.setSoldiersInMap(player1, 0);
                    break;
                case W:
                    map.setSoldiersInMap(player1, 1);
                    break;
                case E:
                    map.setSoldiersInMap(player1, 2);
                    break;
                case R:
                    map.setSoldiersInMap(player1, 3);
                    break;
                case T:
                    map.setSoldiersInMap(player1, 4);
                    break;
                case Y:
                    map.setSoldiersInMap(player2, 4);
                    break;
                case U:
                    map.setSoldiersInMap(player2, 3);
                    break;
                case I:
                    map.setSoldiersInMap(player2, 2);
                    break;
                case O:
                    map.setSoldiersInMap(player2, 1);
                    break;
                case P:
                    map.setSoldiersInMap(player2, 0);
                    break;
                default:
                    break;
            }
        });
    }

    public void initSet(Stage primaryStage){
        player1 = new Player();
        player2 = new Player();
        player1Thread = new Thread(player1);
        player2Thread = new Thread(player2);
        beginningScreen(primaryStage);
        setTowerMap(primaryStage);
        createMap(primaryStage);
        selectCards(primaryStage);
        gameScreen(primaryStage);
        setPlayGround();
        keyBoardController(groundScene);
        gameThread(primaryStage);
        primaryStage.setScene(beginningScene);
    }

    public void beginningScreen(Stage stage){
        beginningPane = new BorderPane();
        beginningScene = new Scene(beginningPane, WIDTH, HEIGHT);
        VBox input = new VBox(20);
        HBox name1 = new HBox(10);
        HBox name2 = new HBox(10);
        name1.setAlignment(Pos.CENTER);
        name2.setAlignment(Pos.CENTER);
        player1.setName(name1);
        player2.setName(name2);
        Button startButton = new Button(" start ");
        startButton.setOnAction(e -> stage.setScene(mapScene));
        input.getChildren().addAll(name1, name2, startButton);
        input.setAlignment(Pos.CENTER);
        beginningPane.setCenter(input);
        stage.centerOnScreen();
    }

    public void setTowerMap(Stage stage){
        mapPane = new Pane();
        mapScene = new Scene(mapPane, WIDTH, HEIGHT);
        VBox parts = new VBox(100);
        VBox addMap = new VBox(5);
        VBox createMap = new VBox(5);
        Label addMapLabel = new Label("Enter your map name :");
        TextField mapNameField = new TextField();
        Button addMapButton = new Button("find");
        addMapButton.setOnAction(e -> {
            try {
                map = Map.readMap(mapNameField.getText().trim());
                setTowersPane.setCenter(map.setTowers());
                stage.setScene(cardsSelectionScene);
            } catch (Exception exception) {
                addMapLabel.setText("Enter your map name : \n " + exception.getMessage());
            }
        });
        addMap.getChildren().addAll(addMapLabel, mapNameField, addMapButton);

        Label createMapLabel = new Label("Create a new map !");
        Button createMapButton = new Button("Create");
        createMapButton.setOnAction(event -> stage.setScene(mapSceneCreation));
        createMap.getChildren().addAll(createMapLabel, createMapButton);
        parts.getChildren().addAll(addMap, createMap);
        parts.setAlignment(Pos.CENTER);
        addMap.setAlignment(Pos.CENTER);
        createMap.setAlignment(Pos.CENTER);
        mapPane.getChildren().addAll(parts);
    }

    public void createMap(Stage stage){
        mapPaneCreation = new BorderPane();
        mapSceneCreation = new Scene(mapPaneCreation, WIDTH, HEIGHT);
        HBox inputHeightBox = new HBox(10);
        HBox saveMapBox = new HBox(10);
        Label mapLengthLabel = new Label("Enter your map dimensions: ");
        TextField inputLengthField = new TextField();
        Button getLengthMapButton = new Button("Create");
        getLengthMapButton.setOnAction(event -> {
            try {
                int lengthOfMyMap = Integer.parseInt(inputLengthField.getText());
                newMap = new Map(lengthOfMyMap);
                mapPaneCreation.setCenter(newMap.setMapTilesColor());
            } catch (Exception e) {
                mapLengthLabel.setText("Enter your map dimensions \n" + e.getMessage());
            }
        });
        inputHeightBox.getChildren().addAll(mapLengthLabel, inputLengthField, getLengthMapButton);
        mapPaneCreation.setTop(inputHeightBox);
        Label mapNameLabel = new Label("Enter your map name: ");
        TextField newMapNameField = new TextField();
        Button saveMyMap = new Button(" save ");
        saveMyMap.setOnAction(event -> {
            if (newMap != null) {
                if (newMapNameField.getText().equals("")) {
                    mapNameLabel.setText("Enter your map name:  \n" + "This field can't be empty!");
                }
                else {
                    try {
                        newMap.writeMap(newMapNameField.getText());
                        stage.setScene(beginningScene);
                    } catch (Exception ex) {
                        mapNameLabel.setText("Enter your map name: \n" + ex.getMessage());
                    }
                }
            }
            else {
                mapNameLabel.setText("Enter your map name: \n" + "Your map is invalid");
            }
        });
        saveMapBox.getChildren().addAll(mapNameLabel, newMapNameField, saveMyMap);
        mapPaneCreation.setBottom(saveMapBox);
    }

    public void selectCards(Stage stage){
        cardsSelectionPane = new BorderPane();
        cardsSelectionScene = new Scene(cardsSelectionPane,WIDTH + 200, HEIGHT);
        Button next = new Button("next");
        VBox vBox1 = new VBox(10);
        VBox vBox2 = new VBox(10);
        Soldiers.selectSoldier(player1, vBox1);
        Soldiers.selectSoldier(player2, vBox2);
        next.setOnAction(event -> stage.setScene(setTowersScene));
        cardsSelectionPane.setLeft(vBox1);
        cardsSelectionPane.setRight(vBox2);
        cardsSelectionPane.setBottom(next);

    }

    public void gameScreen(Stage stage){
        setTowersPane = new BorderPane();
        setTowersScene = new Scene(setTowersPane, WIDTH, HEIGHT);
        VBox towersVBox1 = player1.getTowers();
        VBox towersVBox2 = player2.getTowers();
        Button playButton = new Button(" play ");
        playButton.setOnAction(event -> {
            stage.setScene(groundScene);
            groundPane.setCenter(map.setSoldiers());
            groundPane.setBottom(player1.getPlayerPane());
            groundPane.setTop(player2.getPlayerPane());
            groundPane.setRight(player1.getListOfSoldiers());
            groundPane.setLeft(player2.getListOfSoldiers());
            map.setRedTiles(player1, player2);
            player1Thread.start();
            player2Thread.start();
        });
        setTowersPane.setBottom(playButton);
        setTowersPane.setRight(towersVBox1);
        setTowersPane.setLeft(towersVBox2);
    }

    public void setPlayGround(){
        groundPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu(" file ");
        MenuItem save = new MenuItem(" save ");
        MenuItem load = new MenuItem(" load ");
        menuBar.getMenus().add(file);
        file.getItems().add(save);
        save.setOnAction(event -> save());
        file.getItems().add(load);
        load.setOnAction(event -> load());

        VBox vbox = new VBox(menuBar, groundPane);
        groundScene = new Scene(vbox, WIDTH, HEIGHT);
    }

    public void gameThread(Stage stage) {
        Thread thread = new Thread(() -> {
            while(player1.getLives() != 0 && player2.getLives() != 0) {
                try {
                    Thread.sleep(100);
                } catch (Exception e){
                    System.out.println(e);
                }
            }
            Platform.runLater(() -> {
                BorderPane pane = new BorderPane();
                VBox box = new VBox(10);
                Scene scene = new Scene(pane,500, 500);
                Label label;
                if(player1.getLives()!=0) {
                    label = new Label(player1.getName()+" win! ");
                }
                else {
                    label = new Label(player2.getName() + " win! ");
                }
                Button button = new Button(" return ");
                box.getChildren().addAll(label, button);
                box.setAlignment(Pos.CENTER);
                pane.setCenter(box);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                stage.setScene(scene);
                stage.centerOnScreen();
                button.setOnAction(event -> initSet(stage));
            });
        });
        thread.start();
    }

    public static void setThisTower(Tower tower) {
        thisTower = tower;
    }

    public static void setThisPlayer(Player selectedPlayer) {
        Main.thisPlayer = selectedPlayer;
    }

    public static Player getThisPlayer() {
        return thisPlayer;
    }

    public static Tower getThisTower() {
        return thisTower;
    }

    public static void setThisSoldiers(Soldier selectedSoldier) {
        Main.thisSoldier = selectedSoldier;
    }

    public static Soldier getThisSoldiers() {
        return thisSoldier;
    }

    public static Player getPlayer(int playerId) {
        return playerId == 0 ? player1 : player2;
    }

    public static Map getMap() {
        return map;
    }

    public static void stopGame() {
        for (Thread thread : player1.getThreads()) {
            thread.stop();
        }
        for (Thread thread : player2.getThreads()) {
            thread.stop();
        }
        player1Thread.stop();
        player2Thread.stop();
    }

    public static void save() {
        stopGame();
        try {
            File file = new File("/home/helix/Desktop/Castle-Defence-Test/saved/players");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(player1);
            objectOutputStream.writeObject(player2);
            objectOutputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            File file = new File("/home/helix/Desktop/Castle-Defence-Test/saved/map");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    public static void load() {
        try {
            File file = new File("/home/helix/Desktop/Castle-Defence-Test/saved/players");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            player1 = (Player) objectInputStream.readObject();
            player2 = (Player) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            File file = new File("/home/helix/Desktop/Castle-Defence-Test/saved/map");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map = (Map) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}