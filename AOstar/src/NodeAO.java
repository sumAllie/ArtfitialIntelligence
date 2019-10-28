import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class NodeAO {

    public String name ;
    public int heuristicValue;
    public boolean IsFinal;
    public boolean visited=false;
    public NodeAO parent=null;
    public Operators parentOp=null;
    public ArrayList<Operators> operators;
    public PriorityQueue<Operators> operatorsQueue=new PriorityQueue<>(operatorsComparator);

    public static Comparator<Operators> operatorsComparator=new Comparator<Operators>() {
        @Override
        public int compare(Operators o1, Operators o2) {
            if(o1.cost>o2.cost)return 1;
            if(o1.cost<o2.cost)return -1;
            return 0;
        }
    };


    public NodeAO(String name, int heuristic, boolean IsFinal) {
        this.name = name;
        this.heuristicValue = heuristic;
        this.IsFinal = IsFinal;
    }
}
