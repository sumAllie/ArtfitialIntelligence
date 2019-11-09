package AStar;

import input.*;

import java.util.*;


public class AStar {
    private static final int INFINITY = Integer.MAX_VALUE;

    public void aStar(String filepath){
        ArrayList<Node> nodelist = toNodelist(filepath);
        double countryDist[][] = CountryDistance(nodelist);

        int countryNum = nodelist.size();
        Stack<Node> open = new Stack<>();
        Queue<Node> close = new LinkedList<>();
        Queue<Double> fvalueQueue = new LinkedList<>();

        Node start = nodelist.get(0);
        open.add(start);

        Node curNode;
        Node current;
        int depth = 0;
        double curFvalue;
        Stack<Node> nodeStack = new Stack<Node>();
        Stack<Node> tempStack = new Stack<Node>();

        for (int i = 0; i < countryNum; i++) {
            nodelist.get(i).setVisited(false);
        }

        while (!open.isEmpty()){
            depth++;
            curNode = open.pop();
            close.offer(curNode);

            while (!open.isEmpty()){
                open.pop();
            }

            for (int i = 0; i < countryNum; i++) {
                current = nodelist.get(i);
                if (current != curNode && !current.isVisited()){
                    curFvalue = get_gvalue(0,i,countryDist) + get_hvalue(depth,countryDist);
                    current.setFvalue(curFvalue);
                    if (curFvalue > 0 && curFvalue < INFINITY){
                        if (nodeStack.isEmpty()){
                            nodeStack.push(current);
                        }else {
                            while(!nodeStack.isEmpty()){
                                if (curFvalue < nodeStack.peek().getFvalue()){
                                    tempStack.add(nodeStack.pop());
                                    nodeStack.push(current);
                                }else{
                                    if (curFvalue > nodeStack.peek().getFvalue()){
                                        nodeStack.push(current);
                                    }
                                    break;
                                }
                            }
                            while (!tempStack.isEmpty()){
                                nodeStack.push(tempStack.pop());
                            }
                        }
                    }
                }
            }

            curNode.setVisited(!curNode.isVisited());

            while(!nodeStack.isEmpty()){
                open.push(nodeStack.pop());
            }
            if (!open.isEmpty()){
                fvalueQueue.offer(open.peek().getFvalue());
            }
        }

        close.offer(start);
        Node last = close.poll();
        while(!close.isEmpty()){
            System.out.print(last.getCountryId() + "-"
                    + close.peek().getCountryId() + " : ");
            if (close.size() > 1){
                System.out.println(fvalueQueue.poll());
            }else{
                double fval = get_gvalue(last.getCountryId(), start.getCountryId(),countryDist)
                        + get_hvalue(depth + 1, countryDist);
                System.out.println(fval);
            }
            last = close.poll();
        }

    }


    private double get_gvalue(int currentId, int startId, double[][] countryDist){
        return countryDist[startId][currentId];
    }

    private double get_hvalue(int depth, double[][] countryDist){
        int countryNum = countryDist.length;
        double min = get_minDistance(countryDist);

        return (countryNum - depth)*min;
    }

    private double get_minDistance(double[][] countryDist){
        int num = countryDist.length;
        double minDist = INFINITY;

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (countryDist[i][j] > 0 && countryDist[i][j] < minDist){
                    minDist = countryDist[i][j];
                }
            }
        }

        return minDist;
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
        double countryDist[][] = new double[countryNum][countryNum];

        double distance, xDist, yDist;
        for (int i = 0; i < countryNum; i++) {
            for (int j = 0; j < countryNum; j++) {
                xDist = nodelist.get(i).getX() - nodelist.get(j).getX();
                yDist = nodelist.get(i).getY() - nodelist.get(j).getY();
                distance = xDist * xDist + yDist * yDist;

                countryDist[i][j] = Math.sqrt(Math.abs(distance));
            }
        }

        return countryDist;
    }

}
