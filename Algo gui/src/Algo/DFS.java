package Algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class DFS{

    private final Board board;

    private final Node startNode;
    private final Node endNode;

    private List<Node> neighbors;
    private final Boolean[][] visited;
    private final Stack<Node> s;

    public DFS(Board board) {
        this.board = board;
        this.visited = new Boolean[board.getHeight()][board.getWidth()];
        this.s = new Stack<>();
        this.startNode = findStartNode();
        this.endNode = findEndNode();

        fillFalse();
        depthFirstSearch();

        //depthFirstSearch_r(startNode);
        board.setRunning(false);
    }

    public Node findStartNode(){
        for(int h = 0; h< board.getHeight(); h++ ){
            for(int w = 0; w < board.getWidth(); w++){
                if(board.getTile(h, w) == Tile.START){
                    return new Node(w, h);
                }
            }
        }
        return null;
    }

    public Node findEndNode(){
        for(int h = 0; h< board.getHeight(); h++ ){
            for(int w = 0; w < board.getWidth(); w++){
                if(board.getTile(h, w) == Tile.END){
                    return new Node(w, h);
                }
            }
        }
        return null;
    }



    public void findNeighbors(Node curr){

        Node north = new Node(curr.getX(), curr.getY()-1);
        if (north.getY() >= 0 && !visited[north.getY()][north.getX()] && board.getTile(north.getY(), north.getX()) != Tile.OBSTACLE) {
            if(board.getTile(north.getY(), north.getX()) != Tile.END) {board.setTile(north.getY(), north.getX(), Tile.QUEUE);}
            north.setParent(curr);
            s.push(north);
        }

        Node west  = new Node(curr.getX()-1, curr.getY());
        if (west.getX() >= 0 && !visited[west.getY()][west.getX()] && board.getTile(west.getY(), west.getX()) != Tile.OBSTACLE) {
            if(board.getTile(west.getY(), west.getX()) != Tile.END){ board.setTile(west.getY(), west.getX(), Tile.QUEUE);}
            west.setParent(curr);
            s.push(west);
        }

        Node east  = new Node(curr.getX()+1, curr.getY());
        if (east.getX() < board.getWidth() && !visited[east.getY()][east.getX()] && board.getTile(east.getY(), east.getX()) != Tile.OBSTACLE) {
            if(board.getTile(east.getY(), east.getX()) != Tile.END){ board.setTile(east.getY(), east.getX(), Tile.QUEUE);}
            east.setParent(curr);
            s.push(east);
        }

        Node south = new Node(curr.getX(), curr.getY()+1);
        if (south.getY() < board.getHeight() && !visited[south.getY()][south.getX()] && board.getTile(south.getY(), south.getX()) != Tile.OBSTACLE) {
            if(board.getTile(south.getY(), south.getX()) != Tile.END){ board.setTile(south.getY(), south.getX(), Tile.QUEUE);}
            south.setParent(curr);
            s.push(south);
        }
    }


    public void fillFalse(){
        board.clearBoard();
        for (int h = 0; h < board.getHeight(); h++) {
            for (int w = 0; w < board.getWidth(); w++) {
                visited[h][w] = false;
            }
        }
    }


    public void depthFirstSearch(){

        s.push(startNode);
        visited[startNode.getY()][startNode.getX()] = true;

        while(!s.empty()){
            Node v = s.peek();
            s.pop();
            visited[v.getY()][v.getX()] = true;
            if(v.getY() == endNode.getY() && v.getX() == endNode.getX()){
                drawPath(v);
                break;
            }
            findNeighbors(v);
        }
    }


    public void findNeighbors_r(Node curr){
            neighbors = new ArrayList<>();

            Node north = new Node(curr.getX(), curr.getY() - 1);
            if (north.getY() >= 0 && board.getTile(north.getY(), north.getX()) != Tile.OBSTACLE) {
                north.setParent(curr);
                neighbors.add(north);
            }

            Node west = new Node(curr.getX() - 1, curr.getY());
            if (west.getX() >= 0 && board.getTile(west.getY(), west.getX()) != Tile.OBSTACLE) {
                west.setParent(curr);
                neighbors.add(west);
            }

            Node east = new Node(curr.getX() + 1, curr.getY());
            if (east.getX() < board.getWidth() && board.getTile(east.getY(), east.getX()) != Tile.OBSTACLE) {
                east.setParent(curr);
                neighbors.add(east);
            }

            Node south = new Node(curr.getX(), curr.getY() + 1);
            if (south.getY() < board.getHeight() && board.getTile(south.getY(), south.getX()) != Tile.OBSTACLE) {
                south.setParent(curr);
                neighbors.add(south);
        }
    }




    public void depthFirstSearch_r(Node node) {
        visited[node.getY()][node.getX()] = true;
        if(node.getX() == endNode.getX() && node.getY() == endNode.getY()){
            drawPath(node);
        }
        findNeighbors_r(node);
        for(Node n : neighbors){
            if(!visited[n.getY()][n.getX()]){
                depthFirstSearch_r(n);
            }
        }


    }

    public void drawPath(Node goal){
        goal = goal.getParent();
        int numOfNodes = 0;
        while(goal.getParent() != null){
            numOfNodes++;
            board.setTile(goal.getY(), goal.getX(), Tile.PATH);
            goal = goal.getParent();
            board.notifyListeners();
        }
        System.out.println("Number of nodes: " + numOfNodes);
    }
}
