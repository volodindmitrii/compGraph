import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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


        boolean asdfasdf = true;


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
            tops.add(new Point(100, 300));
            tops.add(new Point(50, 250));
            tops.add(new Point(300, 300));
            MyPolygon polygon = new MyPolygon(tops);
            for (MyLine line : polygon.getLines()) {
//            System.out.println(line.x1+" | "+line.y1);
//            System.out.println(line.x2+" | "+line.y2);
                for (Point point : line.getPoints()) {
                    g.drawLine(point.x, point.y, point.x, point.y);
                }
            }
            System.out.println(polygon.isConvex());
            System.out.println(polygon.isSimple());
        }
    }
}