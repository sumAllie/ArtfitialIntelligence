import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Search {
    int numMinmax = 0;

    void minmaxSearch(Node node){
        if (node != null){
            numMinmax++;

            if (!node.children.isEmpty()){
                for (Node n : node.children){
                    minmaxSearch(n);
                }
            }

            if (node.value1 > -1 && node.value1 < 100){
                if (node.depth % 2 == 0){// min level
                    if (node.parent != null && node.value1 > node.parent.value1){
                        node.parent.value1 = node.value1;
                    }
                }
                else{// max level
                    if (node.parent != null && node.value1 < node.parent.value1){
                        node.parent.value1 = node.value1;
                    }
                }
            }
        }
    }

    void minmax(Node root){
        minmaxSearch(root);
        searchHelpler(root, "minmax", numMinmax);
    }


    int numAB = 0;
    Queue<Node> alphaPruning = new LinkedList<>();
    Queue<Node> betaPruning = new LinkedList<>();


    void minmaxWithAlphaBetaSearch(Node node){
        if (node != null){
            numAB++;


            if (node.alpha == 1000 && node.beta == 1000){
                node.alpha = -1;
                node.beta = 100;
            }

            if (node.depth % 2 == 0){//min level
                //pruning
                if (node.parent != null && node.value < node.parent.alpha){
                    alphaPruning.offer(node);
                    return;
                }
            }
            else{// max level
                //pruning
                if (node.parent != null && node.value > node.parent.beta){
                    betaPruning.offer(node);
                    return;
                }
            }

            if (!node.children.isEmpty()){
                for (Node n : node.children){
                    minmaxWithAlphaBetaSearch(n);
                }
            }

            if (node.value > -1 && node.value < 100 && node.parent != null){
                if (node.depth % 2 == 0){//min level
                    if (node.value > node.parent.value){
                        node.parent.value = node.value;
                        node.parent.alpha = node.value;
                    }else {
                        node.beta = node.parent.alpha;
                        node.alpha = node.value;
                    }

                }
                else{// max level
                    if (node.value < node.parent.value){
                        node.parent.value = node.value;
                        node.parent.beta = node.value;
                    }else {
                        node.alpha = node.parent.beta;
                        node.beta = node.value;
                    }
                }
            }

        }
    }

    void minmaxWithAlphaBeta(Node root){
        minmaxWithAlphaBetaSearch(root);

        searchHelpler(root, "minmaxWithAlphaBeta", numAB);

        minmaxABHelpler(alphaPruning,"Alpha");
        minmaxABHelpler(betaPruning, "Beta");

        System.out.println("\n");
    }


    void minmaxABHelpler(Queue<Node>queue, String type){
        if (!queue.isEmpty()){
            System.out.print(type + " pruning : ");
        }
        while(!queue.isEmpty()){
            System.out.print(queue.peek().parent.name + "--" + queue.poll().name);
            if (!queue.isEmpty()){
                System.out.print(" , ");
            }
            else {
                System.out.println();
            }
        }
    }

    void searchHelpler(Node root, String type, int num){
        Stack<Node>ans = new Stack<>();
        Stack<Node> temp = new Stack<>();
        Queue<Node> curChild = new LinkedList<>();
        Queue<Node> leafList = new LinkedList<>();
        Queue<Stack<Node>> resultMinmax = new LinkedList<>();

        Node node;
        Node newNode;
        Node node1;

        temp.push(root);
        while (!temp.isEmpty()){
            node = temp.pop();

            if (node.depth == 4){
                leafList.offer(node);
                continue;
            }

            while (!node.children.isEmpty()){
                curChild.offer(node.children.poll());
            }

            while (!curChild.isEmpty()){
                node.children.offer(curChild.peek());
                temp.push(curChild.poll());
            }
        }

        boolean isAdded;

        while (!leafList.isEmpty()){
            newNode = leafList.poll();
            isAdded = false;
            if (newNode.value1 == root.value1){
                while(!newNode.children.isEmpty()){
                    curChild.offer(newNode.children.poll());
                }
                while (!curChild.isEmpty()){
                    newNode.children.offer(curChild.peek());
                    node1 = curChild.poll();
                    if (node1.value1 == root.value1){
                        leafList.offer(node1);
                        isAdded = true;
                    }
                }

                if (isAdded){
                    continue;
                }

                ans.push(newNode);
                do {
                    ans.push(newNode.parent);
                    newNode = newNode.parent;
                }while (newNode.parent != null);
            }
            if (!ans.isEmpty()){
                resultMinmax.offer(ans);
                ans = new Stack<>();
            }
        }

        System.out.println("The " + type + " Search value is : " + root.value1);

        Stack<Node> a;

        if (!resultMinmax.isEmpty()){
            System.out.print("The " + type + " Search route is : ");
        }

        while (!resultMinmax.isEmpty()){
            a = resultMinmax.poll();
            while(!a.isEmpty()){
                System.out.print(a.pop().name + " ");
            }
            if (!resultMinmax.isEmpty()){
                System.out.print(", ");
            }
        }

        System.out.println("\nThe Search number is : " + num);
    }


}
