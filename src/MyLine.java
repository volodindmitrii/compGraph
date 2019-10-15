import com.sun.javafx.geom.Point2D;

import java.awt.*;
import java.util.ArrayList;

public class MyLine {
    private ArrayList<Point> points;
    int x1;
    int x2;
    int y1;
    int y2;

    MyLine(Point point1, Point point2) {
        this.points = new ArrayList<>();
        drawLine(point1, point2);
        this.x1 = this.points.get(0).x;
        this.y1 = this.points.get(0).y;
        this.x2 = this.points.get(this.points.size()-1).x;
        this.y2 = this.points.get(this.points.size()-1).y;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    private void drawLine(Point point1, Point point2) {
        int deltaX = point1.x - point2.x;
        int deltaY = point1.y - point2.y;
        double delta = Math.abs((double)deltaX / deltaY);
        int E;
        int x = point1.x;
        int y = point1.y;
        int con;
        if (delta>=1) {
            E = 2 * Math.abs(deltaY) - Math.abs(deltaX);
            if (point1.x < point2.x) {
                con = 1;
            } else {
                con = -1;
            }
            this.points.add(new Point(x, y));
            deltaY = point2.y - y;
            if (deltaY > 0) {
                while (x != point2.x-con) {
                    E += 2 * Math.abs(deltaY);
                    x+=con;
                    if (E > 0) {
                        y++;
                        E -= 2 * Math.abs(deltaX);
                    }
                    this.points.add(new Point(x, y));
                }
            } else {
                while (x != point2.x-con) {
                    E += 2 * Math.abs(deltaY);
                    x+=con;
                    if (E >= 0) {
                        y--;
                        E -= 2 * Math.abs(deltaX);
                    }
                    this.points.add(new Point(x, y));
                }
            }
        }
        else {
            E = 2 * Math.abs(deltaX) - Math.abs(deltaY);
            if (point1.y < point2.y) {
                con = 1;
            }
            else {
                con = -1;
            }
            this.points.add(new Point(x, y));
            deltaX = point2.x-x;
            if (deltaX > 0){
                while (y != point2.y-con) {
                    E += 2 * Math.abs(deltaX);
                    y+=con;
                    if (E > 0) {
                        x++;
                        E -= 2 * Math.abs(deltaY);
                    }
                    this.points.add(new Point(x, y));
                }
            }
            else {
                while (y != point2.y-con) {
                    E += 2 * Math.abs(deltaX);
                    y+=con;
                    if (E >= 0) {
                        x--;
                        E -= 2 * Math.abs(deltaY);
                    }
                    this.points.add(new Point(x, y));
                }
            }
        }
    }

    public PointPosition relativityPoint(Point point) {
        int x = point.x;
        int y = point.y;
        int ax = x2 - x1;
        int bx = x - x1;
        int ay = y2 - y1;
        int by = y - y1;
        boolean checkX1 = false;
        if (Math.abs(x1-x) <= 2) {
            checkX1 = true;
            bx = 0;
        }
        boolean checkX2 = false;
        if (Math.abs(x2-x) <= 2) checkX2 = true;
        boolean checkY1 = false;
        if (Math.abs(y1-y) <= 2) {
            checkY1 = true;
            by = 0;
        }
        boolean checkY2 = false;
        if (Math.abs(y2-y) <= 2) checkY2 = true;
        int s = ax*by - bx*ay;
        if (checkX1 && checkY1) return PointPosition.ORIGIN;
        else if (checkX2 && checkY2) return PointPosition.DESTINATION;
        else if (s>0) return PointPosition.LEFT;
        else if (s<0) return PointPosition.RIGHT;
        else if ((ax*bx < 0) || (ay*by < 0)) return PointPosition.BEHIND;
        else if (ax*ax + ay*ay < bx*bx + by*by) return PointPosition.BEYOND;
        return PointPosition.BETWEEN;
    }

    public IntersectionPosition relativityLine(MyLine line, int con) {
        int xa = this.x1;
        int ya = this.y1;
        int xb = this.x2;
        int yb = this.y2;
        int xc = line.x1;
        int yc = line.y1;
        int xd = line.x2;
        int yd = line.y2;
        int nx = yd - yc;
        int ny = xc - xd;
        int denom = nx*(xb-xa) + ny*(yb-ya);
//        System.out.println(nx);
//        System.out.println(ny);
//        System.out.println(xa + "|" + ya);
//        System.out.println(xb + "|" + yb);
//        System.out.println(xc + "|" + yc);
//        System.out.println(xd + "|" + yd);
//        System.out.println(denom);
        int num = nx*(xa-xc) + ny*(ya-yc);
        if (Math.abs(denom) <= 2)
            denom = 0;
        if (Math.abs(num) <= 2)
            num = 0;
        PointPosition type;
        if (denom == 0) {
            type = (new MyLine(new Point(xc, yc), new Point(xd, yd))).relativityPoint(new Point(xa, ya));
            if ((type == PointPosition.LEFT) || (type == PointPosition.RIGHT)) return IntersectionPosition.PARRALEL;
            else return IntersectionPosition.COLIINEAR;
        }
        float t = -(float)num/denom;
        if (0<t && t<1) {
            if (con != 2) {
                return (new MyLine(new Point(xc, yc), new Point(xd, yd))).relativityLine(new MyLine(new Point(xa, ya), new Point(xb, yb)), 2);
            }
            else {
                return IntersectionPosition.SKEW_CROSS;
            }
        }
        return IntersectionPosition.SKEW_NON_CROSS;
    }
}
