package ir.ac.kntu.map;

import javafx.scene.paint.Color;

import java.io.Serializable;

public enum TypeOfTiles implements Serializable {
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    RED(Color.RED),
    GREY(Color.LIGHTGREY),
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