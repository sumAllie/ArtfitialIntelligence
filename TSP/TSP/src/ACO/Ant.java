package ACO;

import java.util.ArrayList;
import java.util.Random;

public class Ant{
    private double alpha;
    private double beta;
    private double[][]pher;
    private Node start;
    private Node current;
    private ArrayList<Node> nodes = new ArrayList<>();
    private double[][]countryDist;
    private ArrayList<Node> tabu = new ArrayList<>();
    private double tourLength;

    public Ant(double alpha, double beta, ArrayList<Node> nodelist, double[][]countryDist){
        this.alpha = alpha;
        this.beta = beta;
        this.countryDist = countryDist;

        tourLength = 0;
        int num = nodelist.size();

        pher = new double[num][num];

        Node temp;
        for (int i = 0; i < num; i++) {
            temp = new Node();
            temp.setCountryId(nodelist.get(i).getCountryId());
            temp.setX(nodelist.get(i).getX());
            temp.setY(nodelist.get(i).getY());
            temp.setVisited(false);
            this.nodes.add(i, temp);
            for (int j = 0; j < num; j++) {
                pher[i][j] = 0;
            }
        }

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                this.pher[i][j] = pher[i][j];
            }
        }

        Random random = new Random(System.currentTimeMillis());
        start = nodelist.get(random.nextInt(num));
        start.setVisited(true);
        tabu.add(start);
        nodes.get(start.getCountryId()).setVisited(true);
        current = start;

    }

    public void selectNext(double[][] phero){
        Node nextCountry = new Node();
        int num = nodes.size();

        double denominator = 0.00;
        for (int i = 0; i < num; i++) {
            if (!nodes.get(i).isVisited()){
                denominator = denominator + Math.pow(phero[current.getCountryId()][i], alpha)
                        * Math.pow(1.0/countryDist[current.getCountryId()][i], beta);
            }
        }

        double[] prob = new double[num];
        for (int i = 0; i < num; i++) {
            if (!nodes.get(i).isVisited()){
                prob[i] = Math.pow(phero[current.getCountryId()][i], alpha)
                        * Math.pow(1.0/countryDist[current.getCountryId()][i], beta)/denominator;
            }
            else {
                prob[i] = 0;
            }
        }

        Random random = new Random(System.currentTimeMillis());
        double selectProb = random.nextDouble();
        double count = 0;
        for (int i = 0; i < num; i++) {
            count = count + prob[i];
            if (count > selectProb){
                nextCountry = nodes.get(i);
                break;
            }
        }


        nextCountry.setVisited(true);
        tabu.add(nextCountry);
        nodes.get(nextCountry.getCountryId()).setVisited(true);
        current = nextCountry;
    }

    private double calTourLength(){
        double length = 0;
        for (int i = 0; i < this.countryDist.length; i++) {
            length = length + countryDist[this.tabu.get(i).getCountryId()][this.tabu.get(i+1).getCountryId()];
        }
        return length;

    }

    public double getTourLength() {
        tourLength = calTourLength();
        return tourLength;
    }

    public Node getStart() {
        return start;
    }

    public ArrayList<Node> getTabu() {
        return tabu;
    }

    public void setPher(double[][] pher) {
        this.pher = pher;
    }

    public double[][] getPher() {
        return pher;
    }
}
