import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class MyPolygon {
    private ArrayList<MyLine> lines = new ArrayList<>();
    private ArrayList<Point> tops;
    PolygonDirection polDir;

    MyPolygon(){}

    MyPolygon(ArrayList<Point> tops) {
        this.tops = tops;
        topsToLines(tops);
    }

    public ArrayList<MyLine> getLines() {
        return lines;
    }
    public ArrayList<Point> getTops() {return tops;}

    public PolygonDirection getPolygonDirection() {
        initPolygonDirection();
        return polDir;
    }

    private void initPolygonDirection() {
        boolean checker1 = true;
        boolean checker2 = true;
        for (MyLine line : this.lines) {
            for (Point point : this.tops) {
                if (line.relativityPoint(point) == PointPosition.LEFT) {
                    checker1 = false;
                }
            }
            if (checker1)
                this.polDir = PolygonDirection.LEFT;
        }
        for (MyLine line : this.lines) {
            for (Point point : this.tops) {
                if (line.relativityPoint(point) == PointPosition.RIGHT) {
                    checker2 = false;
                }
            }
            if (checker2)
                this.polDir = PolygonDirection.RIGHT;
        }
    }

    private void topsToLines(ArrayList<Point> tops) {
        int len = tops.size();
        for (int i = 0; i < len; i++) {
            this.lines.add(new MyLine(tops.get(i%len), tops.get((i+1)%len)));
        }
    }

    public PolygonProperties isConvex() {
        boolean checker1 = true;
        boolean checker2 = true;
        for (MyLine line : this.lines) {
            for (Point point : this.tops) {
                if (line.relativityPoint(point) == PointPosition.LEFT) {
                    checker1 = false;
                }
            }
            if (checker1)
                this.polDir = PolygonDirection.LEFT;
        }
        for (MyLine line : this.lines) {
            for (Point point : this.tops) {
                if (line.relativityPoint(point) == PointPosition.RIGHT) {
                    checker2 = false;
                }
            }
            if (checker2)
                this.polDir = PolygonDirection.LEFT;
        }
        if (checker1 || checker2) {
            this.polDir = PolygonDirection.ENOTHER;
            return PolygonProperties.CONVEX;
        }
        return PolygonProperties.NON_CONVEX;
    }

    public PolygonProperties isSimple() {
        for (MyLine line1 : this.lines) {
            for (MyLine line2 : this.lines) {
//            System.out.println(line1.x1+" | "+line1.y1 + " | " + line1.x2 + " | " + line1.y2);
//            System.out.println(line2.x1+" | "+line2.y1 + " | " + line2.x2 + " | " + line2.y2);
//                System.out.println(line1.relativityLine(line2, 1));
                if (line1.relativityLine(line2, 1) == IntersectionPosition.SKEW_CROSS) {
                    return PolygonProperties.NON_SIMPLE;
                }
            }
        }
        return PolygonProperties.SIMPLE;
    }

}
