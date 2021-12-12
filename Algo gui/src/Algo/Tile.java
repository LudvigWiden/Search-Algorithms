package Algo;

import java.awt.*;

public enum Tile {

    UNKNOWN(Color.GRAY),
    QUEUE(Color.YELLOW),
    SEARCHED(Color.GREEN),
    PATH(Color.RED),
    END(Color.BLACK),
    OBSTACLE(Color.BLUE),
    START(Color.WHITE);

    private final Color color;

    Tile(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
