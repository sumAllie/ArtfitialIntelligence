public class Main {
    public static void main(String[] arrs){
        AO a = new AO();
        NodeAO p0 = a.buildTree();

        if (AO.algorithmAO(p0)){
            System.out.println("There is at least one solution to solve the tree, and the route is as followed.\n");
        }
        else {
            System.out.println("There is no solution to solve the tree.\n");
        }

        System.out.println("p0 ");
        NodeAO node=p0;
        int c;
        int num;

        while (true) {
            c=0;
            Operators op = node.operatorsQueue.peek();
            if (op!=null){
                num = op.nodeAOS.size();
                for (NodeAO n:op.nodeAOS){
                    num--;
                    System.out.print(n.name);
                    if(!n.IsFinal){
                        node=n;
                    }
                    if (num != 0){
                        System.out.print("--");
                    }

                    if(n.IsFinal){
                        c++;
                    }
                }
                System.out.println();
                if(c==op.nodeAOS.size())
                    break;
            }
        }



    }
}
