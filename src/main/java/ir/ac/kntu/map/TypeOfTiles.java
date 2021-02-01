package ir.ac.kntu.map;

import javafx.scene.paint.Color;

import java.io.Serializable;

public enum TypeOfTiles implements Serializable {
    GREY(Color.LIGHTGREY),
    RED(Color.RED),
    YELLOW(Color.YELLOW),
    BLUE(Color.BLUE),
    GREEN(Color.GREEN);

    private final Color tileColor;

    /**
     * This constructor take color of tile
     * @param tileColor
     */

    TypeOfTiles(Color tileColor){
        this.tileColor = tileColor;
    }

    public Color getTileColor() {
        return tileColor;
    }
}