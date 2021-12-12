package Algo;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent implements BoardListener {

    private final Board board;
    private final int height, width;
    private final int TILE_SIZE = 10;

    public GameComponent(Board board) {
        this.board = board;
        this.height = board.getHeight();
        this.width = board.getWidth();


    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                g2d.setColor(board.getTile(h,w).getColor());
                g2d.fillRect(w*TILE_SIZE, h*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(w*TILE_SIZE, h*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

    }

    @Override public Dimension getPreferredSize() {
        int pref_w = width * TILE_SIZE;
        int pref_h = height * TILE_SIZE;
        return new Dimension(pref_w, pref_h);
    }

    @Override public void boardChanged() {
        repaint();
    }

}
