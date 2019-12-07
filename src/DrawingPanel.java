import javafx.geometry.Point3D;

import javax.swing.*;
import java.awt.*;
import java.util.*;

class DrawingPanel extends JPanel {


    public DrawingPanel() {
        setBackground(Color.WHITE);
        setOpaque(true);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


//----------------Line------------------------------------------//
        boolean ifDrawLine = false;
        if (ifDrawLine) {
            Point point1;
            Point point2;
            g.setColor(Color.RED);
            int a1 = 50;
            int a2 = 60;
            int b1 = 300;
            int b2 = 300;
            point1 = new Point(a1, a2);
            point2 = new Point(b1, b2);
            for (Point point : (new MyLine(point1, point2)).getPoints()) {
                g.drawLine(point.x, point.y, point.x, point.y);
            }
            g.setColor(Color.BLUE);
            int a11 = 300;
            int a22 = 300;
            int b11 = 50;
            int b22 = 60;
            point1 = new Point(a11, a22);
            point2 = new Point(b11, b22);
            for (Point point : (new MyLine(point2, point1)).getPoints()) {
                g.drawLine(point.x, point.y, point.x, point.y);
            }

            System.out.println((new MyLine(new Point(a1, a2), new Point(b1, b2))).
                    relativityLine((new MyLine(new Point(a11, a22), new Point(b11, b22))), 1));
        }


//----------------Bezier------------------------------------------//
        boolean ifDrawBezier = false;
        if (ifDrawBezier) {
            Point point1;
            Point point2;
            Point point3;
            Point point4;
            g.setColor(Color.RED);
            int a1 = 50;
            int a2 = 60;
            point1 = new Point(a1, a2);
            int b1 = 300;
            int b2 = 600;
            point2 = new Point(b1, b2);
            int c1 = 300;
            int c2 = -100;
            point3 = new Point(c1, c2);
            int d1 = 150;
            int d2 = 300;
            point4 = new Point(d1, d2);
            Point[] tops = {point1, point2, point3, point4};
            for (Point point : (new BezierLine(tops)).getPoints()){
                g.drawLine(point.x, point.y, point.x, point.y);
            }
            g.setColor(Color.BLUE);
            g.drawLine(a1, a2, a1, a2);
            g.drawLine(b1, b2, b1, b2);
            g.drawLine(c1, c2, c1, c2);
            g.drawLine(d1, d2, d1, d2);
        }

        //----------------Clipping------------------------------------------//
        boolean ifClipping = false;
        if (ifClipping) {
            g.setColor(Color.RED);
            ArrayList<Point> tops = new ArrayList<>();
            tops.add(new Point(60, 50));
            tops.add(new Point(200, 100));
            tops.add(new Point(300, 300));
            tops.add(new Point(50, 200));
            MyPolygon polygon = new MyPolygon(tops);
            for (MyLine line : polygon.getLines()){
                for (Point apoint : line.getPoints()){
                    g.drawLine(apoint.x, apoint.y, apoint.x, apoint.y);
                }
            }
            g.setColor(Color.BLUE);
            MyLine myLine = new MyLine(new Point(50, 60), new Point(400, 450));
            myLine = myLine.clipLine(polygon);
            for (Point apoint : myLine.getPoints()){
                g.drawLine(apoint.x, apoint.y, apoint.x, apoint.y);
            }

        }

        //----------------3D Modelling------------------------------------------//
        boolean if3DModelling =true;
        if (if3DModelling) {
            ArrayList<Point3D> tops = new ArrayList<>();
            int x0 = 100;
            int y0 = 100;
            int z0 = 0;
            int x1 = 100;
            int y1 = 100;
            int z1 = 100;
            int dx = 0;
            int dy = 0;
            tops.add(new Point3D(x0, y0, z0));
            tops.add(new Point3D(x0+x1, y0, z0));
            tops.add(new Point3D(x0+x1, y0+y1, z0));
            tops.add(new Point3D(x0, y0+y1, z0));
            tops.add(new Point3D(x0+dx, y0+dy, z1));
            tops.add(new Point3D(x0+x1+dx, y0+dy, z1));
            tops.add(new Point3D(x0+x1+dx, y0+y1+dy, z1));
            tops.add(new Point3D(x0+dx, y0+y1+dy, z1));
            My3DPolygon polygon = new My3DPolygon(tops);

//            double n = 1;
//            ArrayList<Point> zProjection = polygon.getZProjection(n);
//            for (Point apoint : zProjection){
//                g.drawLine(apoint.x, apoint.y, apoint.x, apoint.y);
//            }

//            double n = 300;
//            double r = (double)1/400;
//            ArrayList<Point> singlePointPerspectiveProjection = polygon.getSinglePointPerspectiveProjection(n, r);
//            for (Point apoint : singlePointPerspectiveProjection){
//                g.drawLine(apoint.x, apoint.y, apoint.x, apoint.y);
//            }


//            ArrayList<Point> povorotProjection = polygon.povorot(1, 2, 0.5, MovingCircle.fi);
//            for (Point apoint : povorotProjection){
//                g.drawLine(apoint.x, apoint.y, apoint.x, apoint.y);
//            }


        }

    }
}