package ACO;

import input.inputFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class antColony {
    private static final int INFINITY = Integer.MAX_VALUE;

    private Ant[] ants;
    private int antNum;
    private int generationNum;
    private double[][] pheromone;

    private double bestLength;
    private Queue<Node> bestRoute = new LinkedList<>();

    private double alpha;
    private double beta;
    private double rho;

    public antColony(int antNum, int generationNum, double alpha, double beta, double rho){
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
        this.antNum = antNum;
        this.generationNum = generationNum;
    }

    public void antCol(String filepath){
        ArrayList<Node> nodelist = toNodelist(filepath);
        double countryDist[][] = CountryDistance(nodelist);

        int countryNum = nodelist.size();
        bestLength = INFINITY;
        bestRoute = new LinkedList<>();

        pheromone = new double[countryNum][countryNum];
        for (int i = 0; i < countryNum; i++) {
            for (int j = 0; j < countryNum; j++) {
                pheromone[i][j] = 0.1;
            }
        }

        // randomly set ants
        ants = new Ant[antNum];
        for (int i = 0; i < antNum; i++) {
            ants[i] = new Ant(alpha,beta,nodelist,countryDist);
        }

        for (int g = 0; g < generationNum; g++) {
            for (int i = 0; i < antNum; i++) {
                for (int j = 0; j < countryNum - 1; j++) {
                    ants[i].selectNext(pheromone);
                }
                ants[i].getTabu().add(ants[i].getStart());
                if (ants[i].getTourLength() < bestLength && ants[i].getTourLength() > 0){
                    bestLength = ants[i].getTourLength();
                    bestRoute = new LinkedList<>();
                    for (int j = 0; j < countryNum + 1; j++) {
                        bestRoute.add(ants[i].getTabu().get(j));
                    }
                }
                for (int j = 0; j < countryNum; j++) {
                    ants[i].getPher()[ants[i].getTabu().get(j).getCountryId()][ants[i].getTabu().get(j+1).getCountryId()]
                            = 1./ants[i].getTourLength();
                    ants[i].getPher()[ants[i].getTabu().get(j+1).getCountryId()][ants[i].getTabu().get(j).getCountryId()]
                            = 1./ants[i].getTourLength();
                }
            }

            updatePher(countryNum);

            for (int i = 0; i < antNum; i++) {
                ants[i] = new Ant(alpha,beta,nodelist,countryDist);
            }
        }

        printOptimal();


    }

    private void updatePher(int countryNum){
        for (int i = 0; i < countryNum; i++) {
            for (int j = 0; j < countryNum; j++) {
                pheromone[i][j] = pheromone[i][j] * (1 - rho);
            }
        }
        for (int i = 0; i < countryNum; i++) {
            for (int j = 0; j < countryNum; j++) {
                for (int k = 0; k < antNum; k++) {
                    pheromone[i][j] += ants[k].getPher()[i][j];
                }
            }
        }
    }

    private void printOptimal(){
        System.out.println("The optimal length is: " + bestLength);
        System.out.println("The optimal tour is: ");

        while (!bestRoute.isEmpty()){
            System.out.println(bestRoute.poll().getCountryId());
        }
    }

    private ArrayList<Node> toNodelist(String filepath){
        ArrayList<Node> nodelist = new ArrayList<>();
        Node node;

        inputFile input = new inputFile();
        ArrayList<List> inputRes = input.input(filepath);

        for (List<Integer> curList: inputRes) {
            node = new Node();

            node.setCountryId(curList.get(0) - 1);
            node.setX(curList.get(1));
            node.setY(curList.get(2));

            nodelist.add(node);
        }

        return nodelist;
    }

    private double[][] CountryDistance(ArrayList<Node> nodelist){

        int countryNum = nodelist.size();
        double[][] countryDist= new double[countryNum][countryNum];

        double distance, xDist, yDist;
        for (int i = 0; i < countryNum; i++) {
            for (int j = 0; j < countryNum; j++) {
                xDist = nodelist.get(i).getX() - nodelist.get(j).getX();
                yDist = nodelist.get(i).getY() - nodelist.get(j).getY();
                distance = xDist * xDist + yDist * yDist;

                countryDist[i][j] = Math.sqrt(Math.abs(distance/10));
            }
        }

        return countryDist;
    }
}
