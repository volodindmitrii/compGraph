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


        boolean asdfasdf = false;


        if (asdfasdf) {
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
//            System.out.print(point.x, point.y);
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
//            System.out.println(point.x, point.y);
                g.drawLine(point.x, point.y, point.x, point.y);
            }

            System.out.println((new MyLine(new Point(a1, a2), new Point(b1, b2))).
                    relativityLine((new MyLine(new Point(a11, a22), new Point(b11, b22))), 1));
        }
//----------------------------------------------------------//
        else {
            g.setColor(Color.BLUE);
            ArrayList<Point> tops = new ArrayList<>();
            tops.add(new Point(60, 50));
            tops.add(new Point(55, 55));
            tops.add(new Point(65, 60));
            tops.add(new Point(70, 45));
            MyPolygon polygon = new MyPolygon(tops);
            for (MyLine line : polygon.getLines()) {
                for (Point point : line.getPoints()) {
                    g.drawLine(point.x, point.y, point.x, point.y);
                }
            }
            System.out.println(polygon.isConvex());
            System.out.println(polygon.isSimple());
            Point point;
            int xMax = tops.get(0).x;
            int yMax = tops.get(0).y;
            int xMin = tops.get(0).x;
            int yMin = tops.get(0).y;
            for (Point apoint : tops) {
                if (apoint.x > xMax)
                    xMax = apoint.x;
                if (apoint.y > yMax)
                    yMax = apoint.y;
                if (apoint.x < xMin)
                    xMin = apoint.x;
                if (apoint.y < yMin)
                    yMin = apoint.y;
            }
//            for (int i = xMin; i <= xMax+2; i++) {
            int i = 50;
                for (int j = xMin; j <= xMax; j++) {
                    point = new Point(i, j);
                    if (ifPaint(point, xMax+1, polygon.getLines())) {
                        g.drawLine(point.x, point.y, point.x, point.y);
                    }
                }
//            }
        }
    }

    private boolean ifPaint(Point point, int xMax, ArrayList<MyLine> lines) {
        int checker = 0;
        Point point1 = new Point(xMax, point.y);
        for (MyLine line : lines) {
            if (line.relativityLine(new MyLine(point, point1), 1) == IntersectionPosition.SKEW_CROSS) {
                checker += 1;
            }
        }
        System.out.println(checker%2);
        if (checker%2 == 0) return true;
        return false;

    }
}