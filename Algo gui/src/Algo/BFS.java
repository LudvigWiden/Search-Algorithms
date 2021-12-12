package Algo;


import java.util.Deque;
import java.util.LinkedList;

public class BFS {

    private final Board board;
    private final Node endNode;
    private final Node startNode;
    private final Deque<Node> deque;
    private final Boolean[][] visited;

    public BFS(Board board) {
        this.board = board;
        this.endNode = findEndNode();
        this.startNode = findStartNode();
        this.visited = new Boolean[board.getHeight()][board.getWidth()];
        this.deque = new LinkedList<>();
        breadthFirstSearch();
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

    private void breadthFirstSearch() {
        board.clearBoard();
        for (int h = 0; h < board.getHeight(); h++) {
            for (int w = 0; w < board.getWidth(); w++) {
                visited[h][w] = false;
            }
        }
        Node work;
        deque.push(startNode);
        visited[startNode.getY()][startNode.getX()] = true;

        while(!deque.isEmpty()){
            work = deque.getFirst();
            deque.removeFirst();
            if(work.getX() == endNode.getX() && work.getY() == endNode.getY()){
                drawPath(work);
                break;
            }
            if(board.getTile(work.getY(), work.getX()) != Tile.START)
                board.setTile(work.getY(), work.getX(), Tile.SEARCHED);
            findNeighbors(work);

        }

    }

    public void findNeighbors(Node curr){

        Node north = new Node(curr.getX(), curr.getY()-1);
        if (north.getY() >= 0 && !visited[north.getY()][north.getX()] && board.getTile(north.getY(), north.getX()) != Tile.OBSTACLE
                && board.getTile(north.getY(), north.getX()) != Tile.START) {
            north.setParent(curr);
            deque.addLast(north);
            if(board.getTile(north.getY(), north.getX()) != Tile.END) {board.setTile(north.getY(), north.getX(), Tile.QUEUE);}
            visited[north.getY()][north.getX()] = true;
        }

        Node west  = new Node(curr.getX()-1, curr.getY());
        if (west.getX() >= 0 && !visited[west.getY()][west.getX()] && board.getTile(west.getY(), west.getX()) != Tile.OBSTACLE
                && board.getTile(west.getY(), west.getX()) != Tile.START) {
            west.setParent(curr);
            deque.addLast(west);
            if(board.getTile(west.getY(), west.getX()) != Tile.END){ board.setTile(west.getY(), west.getX(), Tile.QUEUE);}
            visited[west.getY()][west.getX()] = true;
        }

        Node east  = new Node(curr.getX()+1, curr.getY());
        if (east.getX() < board.getWidth() && !visited[east.getY()][east.getX()] && board.getTile(east.getY(), east.getX()) != Tile.OBSTACLE
                && board.getTile(east.getY(), east.getX()) != Tile.START) {
            east.setParent(curr);
            deque.addLast(east);
            if(board.getTile(east.getY(), east.getX()) != Tile.END){ board.setTile(east.getY(), east.getX(), Tile.QUEUE);}
            visited[east.getY()][east.getX()] = true;
        }

        Node south = new Node(curr.getX(), curr.getY()+1);
        if (south.getY() < board.getHeight() && !visited[south.getY()][south.getX()] && board.getTile(south.getY(), south.getX()) != Tile.OBSTACLE
        && board.getTile(south.getY(), south.getX()) != Tile.START) {
            south.setParent(curr);
            deque.addLast(south);
            if(board.getTile(south.getY(), south.getX()) != Tile.END){ board.setTile(south.getY(), south.getX(), Tile.QUEUE);}
            visited[south.getY()][south.getX()] = true;
        }
        board.notifyListeners();
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
