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
        int x0 = point1.x;
        int y0 = point1.y;
        int x1 = point2.x;
        int y1 = point2.y;
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int signX = x1 > x0 ? 1 : -1;
        int signY = y1 > y0 ? 1 : -1;
        if (dx == 0 && dy == 0)
        {
            points.add(new Point(x0, y0));
        }
        else
        {
            if (dx - dy >= 0)
            {
                int x = x0;
                int y = y0;
                int E = 2 * dy - dx;
                while (x != x1 || y != y1)
                {
                    points.add(new Point(x, y));
                    if (signY >= 0)
                    {
                        if (E >= 0)
                        {
                            E -= 2 * dx;
                            y += signY;
                        }
                    }
                    else
                    {
                        if (E > 0)
                        {
                            E -= 2 * dx;
                            y += signY;
                        }
                    }
                    x += signX;
                    E += 2 * dy;

                }
                points.add(new Point(x, y));
            }
            else
            {
                int x = x0;
                int y = y0;
                int E = 2 * dx - dy;
                while (x != x1 || y != y1)
                {
                    points.add(new Point(x, y));
                    if (signX >= 0)
                    {
                        if (E >= 0)
                        {
                            E -= 2 * dy;
                            x += signX;
                        }
                    }
                    else
                    {
                        if (E > 0)
                        {
                            E -= 2 * dy;
                            x += signX;
                        }
                    }
                    y += signY;
                    E += 2 * dx;
                }
                points.add(new Point(x, y));
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
        if (Math.abs(x1-x) <= 0) {
            checkX1 = true;
            bx = 0;
        }
        boolean checkX2 = false;
        if (Math.abs(x2-x) <= 0) checkX2 = true;
        boolean checkY1 = false;
        if (Math.abs(y1-y) <= 0) {
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
        int num = nx*(xa-xc) + ny*(ya-yc);
        if (Math.abs(denom) <= 0)
            denom = 0;
        if (Math.abs(num) <= 0)
            num = 0;
        PointPosition type;
        if (denom == 0) {
            type = (new MyLine(new Point(xc, yc), new Point(xd, yd))).relativityPoint(new Point(xa, ya));
            if ((type == PointPosition.LEFT) || (type == PointPosition.RIGHT)) return IntersectionPosition.PARRALEL;
            else return IntersectionPosition.COLIINEAR;
        }
        float t = -(float)num/denom;
        if (0<t && t<=1) {
            if (con != 2) {
                return (new MyLine(new Point(xc, yc), new Point(xd, yd))).relativityLine(new MyLine(new Point(xa, ya), new Point(xb, yb)), 2);
            }
            else {
                return IntersectionPosition.SKEW_CROSS;
            }
        }
        return IntersectionPosition.SKEW_NON_CROSS;
    }

    public MyLine clipLine(MyPolygon polygon){
        PolygonDirection polygonDirection = polygon.getPolygonDirection();
        ArrayList<Point> tops = new ArrayList<>();
        if (polygonDirection == PolygonDirection.RIGHT) {
            tops.add(polygon.getTops().get(0));
            for (int i=0; i<polygon.getTops().size()-1; i++) {
                tops.add(polygon.getTops().get(polygon.getTops().size()-1-i));
            }
            polygon = new MyPolygon(tops);
        }
        int x11, x22, y11, y22;
        double t0 = 0;
        double t1 = 1;
        double t;
        int denom;
        int num;
        int sx = this.x2 - this.x1;
        int sy = this.y2 - this.y1;
        int nx;
        int ny;
        int n = polygon.getTops().size();
        for (int i = 0; i < n; i++){
            nx = polygon.getTops().get((i+1)%n).y - polygon.getTops().get(i).y;
            ny = polygon.getTops().get(i).x - polygon.getTops().get((i+1)%n).x;
            denom = nx*sx+ny*sy;
            num = nx*(this.x1-polygon.getTops().get(i).x)+ny*(this.y1-polygon.getTops().get(i).y);
            if (denom != 0) {
                t = -(double)num/denom;
                if (denom > 0) {
                    if (t>t0)
                        t0 = t;
                }
                else {
                    if (t<t1)
                        t1 = t;
                }
            }
            else if (num < 0)
                return this; //what is here???
        }
        if (t0<=t1) {
            double ax1 = this.x1 + t0*(this.x2-this.x1);
            double ay1 = this.y1 + t0*(this.y2-this.y1);
            double ax2 = this.x1 + t1*(this.x2-this.x1);
            double ay2 = this.y1 + t1*(this.y2-this.y1);
            x11 = (int)Math.round(ax1);
            y11 = (int)Math.round(ay1);
            x22 = (int)Math.round(ax2);
            y22 = (int)Math.round(ay2);
            return new MyLine(new Point(x11, y11), new Point(x22, y22));
        }
        return this; //what is here???
    }
}















