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


//----------------Bese------------------------------------------//
        boolean ifDrawBese = true;
        if (ifDrawBese) {
            Point point1;
            Point point2;
            Point point3;
            Point point4;
            g.setColor(Color.RED);
            int a1 = 50;
            int a2 = 60;
            point1 = new Point(a1, a2);
            int b1 = 300;
            int b2 = 300;
            point2 = new Point(b1, b2);
            int c1 = 200;
            int c2 = 200;
            point3 = new Point(c1, c2);
            int d1 = 150;
            int d2 = 400;
            point4 = new Point(d1, d2);
            Point[] tops = {point1, point2, point3, point4};
            for (Point point : (new BeseLine(tops)).getPoints()){
                g.drawLine(point.x, point.y, point.x, point.y);
            }
        }
    }
}