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
        int D = Math.max(Math.abs(tops[0].x-2*tops[1].x+tops[2].x)+Math.abs(tops[0].y-2*tops[1].y+tops[2].y),
            Math.abs(tops[1].x-2*tops[2].x+tops[3].x)+Math.abs(tops[1].y-2*tops[2].y+tops[3].y));
        double t = 0;
        int N = 1 + (int)Math.pow(3.0*D, 0.5);
        double tau = 1.0/N;
        int pointX;
        int pointY;
        int pointX1;
        int pointY1;
        pointX = tops[0].x;
        pointY = tops[0].y;
        while(t<=1){
            t+=tau;
            pointX1 = (int)(Math.pow(1-t, 3)*tops[0].x+3*t*Math.pow(1-t, 2)*tops[1].x+
                    3*Math.pow(t, 2)*(1-t)*tops[2].x+Math.pow(t, 3)*tops[3].x);
            pointY1 = (int)(Math.pow(1-t, 3)*tops[0].y+3*t*Math.pow(1-t, 2)*tops[1].y+
                    3*Math.pow(t, 2)*(1-t)*tops[2].y+Math.pow(t, 3)*tops[3].y);
            this.points.addAll((new MyLine(new Point(pointX, pointY), new Point(pointX1, pointY1))).getPoints());
            pointX=pointX1;
            pointY=pointY1;
        }
    }
}
