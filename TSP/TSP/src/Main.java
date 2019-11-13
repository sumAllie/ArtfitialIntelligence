import AStar.AStar;
import ACO.antColony;

public class Main{
    public static void main(String[] args){
        String filepath = "data/a280.tsp";

        long startTime = System.nanoTime();
        AStar a = new AStar();
        a.aStar(filepath);
        long endTime = System.nanoTime();
        System.out.println("AStar Running Time : " + (endTime - startTime) + "ns");

        antColony antColony = new antColony(100,100,1,5,0.5);
        antColony.antCol(filepath);
    }
}
