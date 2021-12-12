package Algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Float.POSITIVE_INFINITY;

public class Board {


    private final int width,height;
    private final Tile[][] board;
    private final List<BoardListener> boardListeners;
    private Algos alg;
    private boolean running;
    private boolean startPlaced;
    private boolean endPlaced;
    private Tile tileID;

    private int endX;
    private int endY;
    private int startX;
    private int startY;
    int randint;
    Random rand;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Tile[height][width];
        this.boardListeners = new ArrayList<>();
        this.running = false;
        this.tileID = Tile.START;
        this.alg = Algos.BFS;
        this.startPlaced = false;
        this.endPlaced = false;
        this.rand = new Random();
        this.randint = 0;

        boardMaker();
        mazePattern();
        notifyListeners();
    }

    private void boardMaker() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                board[h][w] = Tile.UNKNOWN;
            }
        }
    }

    public void clearBoard(){
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if(board[h][w] != Tile.START && board[h][w] != Tile.END && board[h][w] != Tile.OBSTACLE){
                board[h][w] = Tile.UNKNOWN;
            }
            }
        }
    }

    public void mazePattern(){
        setStartPlaced();
        setStartX(0);
        setStartY(0);
        setEndX(width-1);
        setEndY(height-1);
        setEndPlaced();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if(board[h][w] != Tile.START && board[h][w] != Tile.END && board[h][w] != Tile.OBSTACLE
                && h<height-1 && w<width-1){
                    //toss coin
                    randint = rand.nextInt(4);
                    if(randint==0){
                        board[h][w+1] = Tile.UNKNOWN;
                        board[h+1][w] = Tile.OBSTACLE;
                    }

                }
            }
        }
    }

    public Node findStartNode(){
        for(int h = 0; h< height; h++ ){
            for(int w = 0; w < width; w++){
                if(getTile(h, w) == Tile.START){
                    return new Node(w, h);
                }
            }
        }
        return null;
    }

    public Node findEndNode(){
        for(int h = 0; h < height; h++ ){
            for(int w = 0; w < width; w++){
                if(getTile(h, w) == Tile.END){
                    return new Node(w, h);
                }
            }
        }
        return null;
    }

    public void fillFalse(Object[][] map){
        clearBoard();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                map[h][w] = false;
            }
        }
    }

    public void fillValue(double[][] map){
        clearBoard();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                map[h][w] = POSITIVE_INFINITY;
            }
        }
    }


    public List<Node> findNeighbors(Node curr, Boolean[][] visited) {
        List<Node> neighbors = new ArrayList<>();
        List<Node> directions = Arrays.asList(new Node(curr.getX() - 1, curr.getY()),new Node(curr.getX(), curr.getY() + 1),
                new Node(curr.getX(), curr.getY() - 1), new Node(curr.getX() + 1, curr.getY()) );

        for(Node direction : directions){
            if (direction.getY() >= 0 && direction.getY() < height && direction.getX() >= 0 && direction.getX() < width &&
                    !visited[direction.getY()][direction.getX()] && getTile(direction.getY(), direction.getX()) != Tile.OBSTACLE
                    && getTile(direction.getY(), direction.getX()) != Tile.START) {
                neighbors.add(direction);
            }
        }
        return neighbors;
    }



    public void drawPath(Node goal){
        int numOfNodes = 0;
        goal = goal.getParent();
        while(goal.getParent() != null){
            numOfNodes++;
            setTile(goal.getY(), goal.getX(), Tile.PATH);
            goal = goal.getParent();
            notifyListeners();
        }
        System.out.println("Number of Nodes in path: " + numOfNodes);
    }



    public Tile getTile(int h, int w) {
        return board[h][w];
    }

    public void setTile(int h, int w, Tile t){
        board[h][w] = t;
    }

    /** Listens to board actions*/
    public void notifyListeners(){
        for(BoardListener listeners : boardListeners) {
            listeners.boardChanged();
        }
    }

    public void addBoardListener(BoardListener bl) {
        boardListeners.add(bl);
    }

    public void tick() {
        while(running && endPlaced && startPlaced) switch (alg) {
            case DFS -> new DFS(this);
            case BFS -> new BFS(this);
            case DIJKSTRA -> new Dijkstra(this);
            case ASTAR -> new AStar(this);
            default -> {
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setAlg(Algos alg) {
        this.alg = alg;
    }

    public void setRunning(boolean state) {
        this.running = state;
    }

    public Tile getTileID() {
        return tileID;
    }

    public void setTileID(Tile tileID) {
        this.tileID = tileID;
    }

    public boolean isStartPlaced() {
        return startPlaced;
    }

    public boolean isEndPlaced() {
        return endPlaced;
    }

    public void setStartPlaced() {
        this.startPlaced = true;
    }

    public void setEndPlaced() {
        this.endPlaced = true;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public static void main(String[] args) {
        Board board = new Board(80,80);
        new GameViewer(board);
    }


}
