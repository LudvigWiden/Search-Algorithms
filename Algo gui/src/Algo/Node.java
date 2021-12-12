package Algo;

import static java.lang.Math.abs;

public class Node implements Comparable<NodeOrder>{

    private double cost;
    private final int x;
    private final int y;
    private Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public double distTo(Node goal){
        int dX = abs(this.x - goal.getX());
        int dY = abs(this.x - goal.getY() );
        return (dX + dY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public int compareTo(NodeOrder o) {
        return 0;
    }
}
