import java.util.*;

public class Tree {

    private Random r = new Random();

    public Node buildTree(){

        Node root = new Node();
        build(root, null, "A1", false);

        Node temp1 = new Node();
        build(temp1, root, "B1",false);

        Node temp2 = new Node();
        build(temp2, root,"B2",false);

        Node temp3 = new Node();
        build(temp3, root, "B3", false);

        Node temp4 = new Node();
        build(temp4, temp1, "C1", false);

        Node temp5 = new Node();
        build(temp5, temp1, "C2", false);

        Node temp6 = new Node();
        build(temp6,temp2, "C3", false);

        Node temp7 = new Node();
        build(temp7,temp2, "C4", false);

        Node temp8 = new Node();
        build(temp8, temp3, "C5", false);

        Node temp9 = new Node();
        build(temp9, temp3, "C6", false);

        Node temp10 = new Node();
        build(temp10, temp3, "C7", false);

        Node temp11 = new Node();
        build(temp11, temp4, "D1", false);

        Node temp12 = new Node();
        build(temp12, temp4, "D2", false);

        Node temp13 = new Node();
        build(temp13, temp5, "D3", false);

        Node temp14 = new Node();
        build(temp14, temp5, "D4", false);

        Node temp15 = new Node();
        build(temp15, temp6, "D5", false);

        Node temp16 = new Node();
        build(temp16, temp6, "D6", false);

        Node temp17 = new Node();
        build(temp17, temp7, "D7", false);

        Node temp18 = new Node();
        build(temp18, temp7, "D8", false);

        Node temp19 = new Node();
        build(temp19, temp8, "D9", false);

        Node temp20 = new Node();
        build(temp20, temp8, "D10", false);

        Node temp21 = new Node();
        build(temp21, temp9, "D11", false);

        Node temp22 = new Node();
        build(temp22, temp10, "D12", false);

        Node temp23 = new Node();
        build(temp23, temp10, "D13", false);

        Node temp24 = new Node();
        build(temp24, temp11,"E1",true);

        Node temp25 = new Node();
        build(temp25, temp11,"E2",true);

        Node temp26 = new Node();
        build(temp26, temp11,"E3",true);

        Node temp27 = new Node();
        build(temp27, temp13,"E4",true);

        Node temp28 = new Node();
        build(temp28, temp14,"E5",true);

        Node temp29 = new Node();
        build(temp29, temp14,"E6",true);

        Node temp30 = new Node();
        build(temp30, temp16,"E7",true);

        Node temp31 = new Node();
        build(temp31, temp16,"E8",true);

        Node temp32 = new Node();
        build(temp32, temp17,"E9",true);

        Node temp33 = new Node();
        build(temp33, temp17,"E10",true);

        Node temp34 = new Node();
        build(temp34, temp17,"E11",true);

        Node temp35 = new Node();
        build(temp35, temp19,"E12",true);

        Node temp36 = new Node();
        build(temp36, temp20,"E13",true);

        Node temp37 = new Node();
        build(temp37, temp20,"E14",true);

        Node temp38 = new Node();
        build(temp38, temp20,"E15",true);

        Node temp39 = new Node();
        build(temp39, temp22,"E16",true);

        Node temp40 = new Node();
        build(temp40, temp22,"E17",true);

        Node temp41 = new Node();
        build(temp41, temp23,"E18",true);


        return root;
    }

    public void build(Node node, Node parent, String name,boolean isLeaf){
        node.name = name;

        if (parent == null){
            node.parent = null;
            node.depth = 1;
        }
        else {
            node.parent = parent;
            node.depth = parent.depth + 1;
            parent.children.offer(node);
        }

        if (isLeaf == true){
            node.IsLeaf = isLeaf;
            node.value = r.nextInt(99);
        }
        else if (node.depth == 4){
            node.value = r.nextInt(99);
        }
        else{
            if ((node.depth % 2) == 0){
                node.value = 100;
            }
            else{
                node.value = -1;
            }

        }

        node.value1 = node.value;

    }

    public void print(Node root,boolean isChanged){
        System.out.println("The tree is as followed:");
        Queue<Node> temp =  new LinkedList<>();
        Queue<Node> curChild = new LinkedList<>();
        temp.add(root);
        Node node;

        int i = 1;

        while (!temp.isEmpty()){
            node = temp.poll();

            if (i == node.depth - 1){
                System.out.println();
                i++;
            }
            if (isChanged){
                System.out.print(node.name + ":" + node.value1);
            }else {
                System.out.print(node.name + ":" + node.value);
            }


            if (!temp.isEmpty()){
                if (node.parent == temp.peek().parent){
                    System.out.print("--");
                }
                else {
                    System.out.print("  ");
                }
            }


            while (!node.children.isEmpty()){
                curChild.offer(node.children.poll());
            }

            while (!curChild.isEmpty()){
                node.children.offer(curChild.peek());
                temp.offer(curChild.poll());
            }


        }

        System.out.println("\n\n");

    }
}
