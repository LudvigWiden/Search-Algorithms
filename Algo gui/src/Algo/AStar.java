package Algo;

import java.util.*;

public class AStar {

    private final Board board;
    private final Node startNode;
    private final Node endNode;
    private Node goal;
    private final Boolean[][] visited;
    private final double[][] values;

    private final Queue<Node> pq;

    public AStar(Board board) {

        this.board = board;
        this.visited = new Boolean[board.getHeight()][board.getWidth()];
        this.values = new double[board.getHeight()][board.getWidth()];
        this.pq = new PriorityQueue<>(new NodeOrder());
        this.startNode = board.findStartNode();
        this.endNode = board.findEndNode();
        board.fillFalse(visited);
        board.fillValue(values);
        ASTAR();
        board.drawPath(goal);
        board.setRunning(false);
    }


    public void ASTAR(){
        startNode.setCost(0);
        values[startNode.getY()][startNode.getX()] = 0;
        pq.add(startNode);

        while(!pq.isEmpty()){
            Node current = pq.poll();
            visited[current.getY()][current.getX()] = true;
            if(current.getY() == endNode.getY() && current.getX() == endNode.getX()){
                goal = current;
                break;
            }
            if(board.getTile(current.getY(), current.getX()) != Tile.START)
                board.setTile(current.getY(), current.getX(), Tile.SEARCHED);

            for (Node neighbor : board.findNeighbors(current, visited)) {
                double moveCost = (current.getCost() + current.distTo(neighbor) + neighbor.distTo(endNode));
                if(moveCost < values[neighbor.getY()][neighbor.getX()]) {
                    values[neighbor.getY()][neighbor.getX()] = moveCost;
                    neighbor.setCost(moveCost);
                    neighbor.setParent(current);
                    pq.add(neighbor);
                    if (board.getTile(neighbor.getY(), neighbor.getX()) != Tile.END) {
                        board.setTile(neighbor.getY(), neighbor.getX(), Tile.QUEUE);
                    }
                }
            }
        }
    }
}
