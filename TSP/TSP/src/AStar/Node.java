package AStar;

public class Node {
    private int x;
    private int y;
    private int countryId;
    private double fvalue;

    private boolean isVisited;

    void setX(int x) {
        this.x = x;
    }

    int getX() {
        return x;
    }

    void setY(int y) {
        this.y = y;
    }

    int getY() {
        return y;
    }

    void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    int getCountryId() {
        return countryId;
    }

    void setVisited(boolean visited) {
        isVisited = visited;
    }

    boolean isVisited() {
        return isVisited;
    }

    void setFvalue(double fvalue) {
        this.fvalue = fvalue;
    }

    double getFvalue() {
        return fvalue;
    }

    String tostring(){
        return (this.getCountryId() + ":" + this.getX()+ "," + this.getY() + " ");
    }
}
