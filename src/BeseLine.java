import java.awt.*;
import java.util.ArrayList;

public class BeseLine {
    private Point[] tops;
    private ArrayList<Point> points = new ArrayList<>();

    BeseLine(Point[] tops){
        if (tops.length == 4) {
            this.tops = tops;
            drawLine();
        }
        else
            System.out.println("Wrong line initialization");
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void drawLine(){
        double t = 0;
        double tau = 0.0001;
        int pointX;
        int pointY;
        while(t<=1){
            pointX = (int)(Math.pow(1-t, 3)*tops[0].x+3*t*Math.pow(1-t, 2)*tops[1].x+
                    3*Math.pow(t, 2)*(1-t)*tops[2].x+Math.pow(t, 3)*tops[3].x);
            pointY = (int)(Math.pow(1-t, 3)*tops[0].y+3*t*Math.pow(1-t, 2)*tops[1].y+
                    3*Math.pow(t, 2)*(1-t)*tops[2].y+Math.pow(t, 3)*tops[3].y);
            t+=tau;
            this.points.add(new Point(pointX, pointY));
        }
    }
}
