import java.util.ArrayList;

public class Operators {
    boolean isItAnd;
    int cost;
    int staticCost;
    NodeAO parent;
    ArrayList<NodeAO> nodeAOS;

    public Operators(boolean isItAnd, int staticCost, NodeAO parent, ArrayList<NodeAO> nodeAOS) {
        this.isItAnd = isItAnd;
        this.staticCost = staticCost;
        this.parent = parent;
        this.nodeAOS = nodeAOS;
    }
}
