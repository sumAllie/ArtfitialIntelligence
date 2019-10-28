import java.util.ArrayList;

public class AO {
    public static boolean algorithmAO(NodeAO root) {
        if (root.operators == null) return false; //node leaf and not final
        if (!root.visited) {
            root.visited = true;
            // calc all operators in this node
            root = calcOperators(root);
            // update parent
            root = modifyAncestor(root);

            Operators curr = root.operatorsQueue.peek();
            // subProblem
            for (NodeAO node : curr.nodeAOS) {
                node.parent = root;
                node.parentOp = curr;
                if (!node.IsFinal)
                    return algorithmAO(node);
            }
        } else {
            Operators curr = root.operatorsQueue.peek();
            for (NodeAO node : curr.nodeAOS) {
                if (!node.IsFinal)
                    return algorithmAO(node);
            }
        }
        return true;
    }

    private static NodeAO calcOperators(NodeAO root) {
        for (Operators op : root.operators) {
            if (!op.isItAnd) {
                op.cost = op.staticCost + root.heuristicValue;
            } else {
                op.cost += op.staticCost;
                for (NodeAO n : op.nodeAOS) {
                    op.cost += n.heuristicValue;
                }
            }
            root.operatorsQueue.add(op);
        }
        return root;
    }

    private static NodeAO modifyAncestor(NodeAO root) {
        while (root.parent != null) {
            NodeAO parent = root.parent;
            Operators perantOp = root.parentOp;
            parent.operatorsQueue.remove(perantOp);
            perantOp.cost = perantOp.staticCost + root.operatorsQueue.peek().cost;
            parent.operatorsQueue.add(perantOp);
            root = parent;
        }
        return root;
    }

    public NodeAO buildTree() {
        // node not final
        NodeAO p0 = new NodeAO("p0", 0, false);
        NodeAO p2 = new NodeAO("p2", 50, false);
        NodeAO p3 = new NodeAO("p3", 28, false);
        NodeAO p4 = new NodeAO("p4", 40, false);
        NodeAO p7 = new NodeAO("p7", 30, false);
        NodeAO p8 = new NodeAO("p8", 22, false);
        NodeAO p9 = new NodeAO("p9", 20, false);
        NodeAO p10 = new NodeAO("p10", 30, false);
        NodeAO p11 = new NodeAO("p11", 15, false);
        NodeAO p12 = new NodeAO("p12", 15, false);
        // final nodes
        NodeAO p1 = new NodeAO("P1", 0, true);
        NodeAO p5 = new NodeAO("P5", 0, true);
        NodeAO p6 = new NodeAO("P6", 0, true);
        NodeAO p13 = new NodeAO("P13", 0, true);
        NodeAO p14 = new NodeAO("P14", 0, true);
        NodeAO p15 = new NodeAO("P15", 0, true);

        // operators
        ArrayList<NodeAO> nop = new ArrayList<>();
        nop.add(p1);
        nop.add(p2);
        Operators op1 = new Operators(true, 5, p0, nop);

        nop = new ArrayList<>();
        nop.add(p3);
        Operators op2 = new Operators(false, 19, p0, nop);

        nop = new ArrayList<>();
        nop.add(p4);
        nop.add(p5);
        Operators op3 = new Operators(true, 8, p0, nop);

        nop = new ArrayList<>();
        nop.add(p15);
        nop.add(p10);
        Operators op4 = new Operators(true, 5, p2, nop);

        nop = new ArrayList<>();
        nop.add(p6);
        nop.add(p7);
        Operators op5 = new Operators(true, 20, p3, nop);

        nop = new ArrayList<>();
        nop.add(p8);
        nop.add(p9);
        Operators op6 = new Operators(true, 10, p3, nop);

        nop = new ArrayList<>();
        nop.add(p10);
        Operators op7 = new Operators(false, 10, p4, nop);

        nop = new ArrayList<>();
        nop.add(p11);
        nop.add(p12);
        Operators op8 = new Operators(true, 20, p4, nop);

        nop = new ArrayList<>();
        nop.add(p13);
        nop.add(p14);
        nop.add(p6);
        Operators op9 = new Operators(true, 45, p10, nop);

        ArrayList<Operators> operators = new ArrayList<>();
        operators.add(op1);
        operators.add(op2);
        operators.add(op3);
        p0.operators = operators;

        operators = new ArrayList<>();
        operators.add(op4);
        p2.operators = operators;

        operators = new ArrayList<>();
        operators.add(op5);
        operators.add(op6);
        p3.operators = operators;

        operators = new ArrayList<>();
        operators.add(op7);
        operators.add(op8);
        p4.operators = operators;

        operators = new ArrayList<>();
        operators.add(op9);
        p10.operators = operators;

        return p0;
    }
}
