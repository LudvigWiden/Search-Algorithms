package Algo;

import java.util.Comparator;

public class NodeOrder implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.getCost() > o2.getCost() ? 1 : -1;
    }
}
