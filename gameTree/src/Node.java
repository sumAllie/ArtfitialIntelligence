import java.util.LinkedList;
import java.util.Queue;

public class Node {

    public int value;
    public int value1;

    public int alpha = 1000;
    public int beta = 1000;

    public String name;
    public boolean IsLeaf;

    public int depth;
    public Node parent;
    public Queue<Node> children = new LinkedList<Node>();

}
