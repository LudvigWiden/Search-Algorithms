package Algo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {


    private final Board board;

    public MouseManager(Board board) {
        this.board = board;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(board.getTileID() == Tile.END) {
            if(board.isEndPlaced()) {
                board.setTile(board.getEndY(), board.getEndX(), Tile.UNKNOWN);
            }
            board.setEndX(e.getX() / 10);
            board.setEndY(e.getY() / 10);
            board.setTile(e.getY() / 10, e.getX() / 10, Tile.END);
            board.setEndPlaced();
        }
        else if(board.getTileID() == Tile.START) {
            if(board.isStartPlaced()) {
                board.setTile(board.getStartY(), board.getStartX(), Tile.UNKNOWN);
            }
            board.setStartX(e.getX() / 10);
            board.setStartY(e.getY() / 10);
            board.setTile(e.getY() / 10, e.getX() / 10, Tile.START);
            board.setStartPlaced();
        }
        else{
            board.setTile(e.getY() / 10, e.getX() / 10, Tile.OBSTACLE);
        }
        board.notifyListeners();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(board.getTileID() == Tile.OBSTACLE) {
            board.setTile(e.getY() / 10, e.getX() / 10, Tile.OBSTACLE);
            board.notifyListeners();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
