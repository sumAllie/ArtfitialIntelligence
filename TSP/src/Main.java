import AStar.AStar;

public class Main{
    public static void main(String[] args){
        long startTime = System.nanoTime();
        AStar a = new AStar();
        int start = 1;
        int end = 23;
        String filepath = "data/a280.tsp";

        a.aStar(filepath);
        long endTime = System.nanoTime();
        System.out.println("\nAStar Running Time : " + (endTime - startTime) + "ns");
    }
}
