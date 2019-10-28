public class Main {
    public static void main(String[] args){
        Tree tree = new Tree();
        Node root = tree.buildTree();
        tree.print(root,true);

        Search a = new Search();
        a.minmax(root);
        System.out.println("\n");

        a.minmaxWithAlphaBeta(root);
    }

}