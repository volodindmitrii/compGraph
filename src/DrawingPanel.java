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
        Graphics2D g2 = (Graphics2D) g;


        boolean checkerLinePolygon = false;


        if (checkerLinePolygon) {
//----------------Line------------------------------------------//
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
                g2.drawLine(point.x, point.y, point.x, point.y);
            }
            g.setColor(Color.BLUE);
            int a11 = 300;
            int a22 = 300;
            int b11 = 50;
            int b22 = 60;
            point1 = new Point(a11, a22);
            point2 = new Point(b11, b22);
            for (Point point : (new MyLine(point2, point1)).getPoints()) {
                g2.drawLine(point.x, point.y, point.x, point.y);
            }

            System.out.println((new MyLine(new Point(a1, a2), new Point(b1, b2))).
                    relativityLine((new MyLine(new Point(a11, a22), new Point(b11, b22))), 1));
        }
//----------------Polygon------------------------------------------//
        else {
            g.setColor(Color.BLUE);
            ArrayList<Point> tops = new ArrayList<>();
            tops.add(new Point(60, 50));
            tops.add(new Point(50, 250));
            tops.add(new Point(300, 400));
            tops.add(new Point(200, 10));
            MyPolygon polygon = new MyPolygon(tops);
            for (MyLine line : polygon.getLines()) {
                for (Point point : line.getPoints()) {
                    g2.drawLine(point.x, point.y, point.x, point.y);
                }
            }
//----------------Is Simple------------------------------------------//
            System.out.println(polygon.isConvex());
//----------------Is convex------------------------------------------//
            System.out.println(polygon.isSimple());
//----------------Pain Over------------------------------------------//
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


            boolean howToPaint = false;


//----------------EO------------------------------------------//
            if (howToPaint) {
                for (int i = xMin+1; i < xMax; i++) {
                    for (int j = yMin+1; j < yMax; j++) {
                        point = new Point(i, j);
                        if (ifEOPaint(point, xMax + 1, polygon.getLines())) {
                            g.drawOval(point.x, point.y, 0, 0);
                        }
                    }
                }
            }
//----------------NZW-----------------------------------------//
            else {
                ArrayList<Integer> ys = new ArrayList<>();
                for (Point atop : tops){
                    ys.add(atop.y);
                }
                for (int i = xMin+1; i < xMax; i++) {
                    for (int j = yMin; j <= yMax-1; j++) {
                        point = new Point(i, j);
                        if (ifNZWPaint(point, xMax + 1, polygon.getLines())) {
                            g.drawOval(point.x, point.y, 0, 0);
                        }
                    }
                }
            }
        }
    }

    private boolean ifEOPaint(Point point, int xMax, ArrayList<MyLine> lines) {
        int checker = 0;
        Point point1 = new Point(xMax, point.y);
        for (MyLine line : lines) {
            IntersectionPosition pos = line.relativityLine(new MyLine(point, point1), 1);
            if (pos == IntersectionPosition.SKEW_CROSS)
                checker += 1;
            PointPosition posPoint = (new MyLine(point, point1)).relativityPoint(new Point(line.x1, line.y1));
            if (posPoint == PointPosition.BETWEEN)
                checker += 1;
        }
        if (checker%2 == 0) return false;
        return true;

    }

    private boolean ifNZWPaint(Point point, int xMax, ArrayList<MyLine> lines) {
        CrossDirection direction;
        int counter = 0;
        for (MyLine line : lines){
            if (line.relativityLine(new MyLine(point, new Point(xMax, point.y)), 1) == IntersectionPosition.SKEW_CROSS) {
                switch (line.relativityPoint(point)) {
                    case LEFT:
                        if ((point.y>line.y1) && (point.y<=line.y2))
                            direction = CrossDirection.CROSS_LEFT;
                        else
                            direction =  CrossDirection.INESSENTIAL;
                        break;
                    case RIGHT:
                        if ((point.y>line.y2) && (point.y<=line.y1))
                            direction =  CrossDirection.CROSS_RIGHT;
                        else
                            direction =  CrossDirection.INESSENTIAL;
                        break;
                    case BETWEEN:
                    case ORIGIN:
                    case DESTINATION:
                        return false;
                    default:
                        direction =  CrossDirection.INESSENTIAL;
                        break;
                }
                if (direction == CrossDirection.CROSS_LEFT)
                    counter += 1;
                else if (direction == CrossDirection.CROSS_RIGHT)
                    counter -= 1;
                PointPosition posPoint = (new MyLine(point, new Point(xMax, point.y))).relativityPoint(new Point(line.x1, line.y1));
                if (posPoint == PointPosition.BETWEEN && line.y2 < point.y)
                    counter -= 1;

            }
        }
        if (counter == 0)
            return false;
        return true;
    }
}