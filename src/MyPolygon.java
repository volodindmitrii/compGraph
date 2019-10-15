import java.awt.*;
import java.util.ArrayList;

public class MyPolygon {
    private ArrayList<MyLine> lines = new ArrayList<>();
    private ArrayList<Point> tops = new ArrayList<>();

    MyPolygon(ArrayList<Point> tops) {
        this.tops = tops;
        topsToLines(tops);
    }

    public ArrayList<MyLine> getLines() {
        return this.lines;
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
//                System.out.println(line.relativityPoint(point));
                if (line.relativityPoint(point) == PointPosition.LEFT) {
                    checker1 = false;
                }
            }
        }
        for (MyLine line : this.lines) {
            for (Point point : this.tops) {
//                System.out.println(line.relativityPoint(point));
                if (line.relativityPoint(point) == PointPosition.RIGHT) {
                    checker2 = false;
                }
            }
        }
        if (checker1 || checker2) return PolygonProperties.CONVEX;
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

//    public ArrayList<Point> paintOver() {
//        int xMax = tops.get(0).x;
//        int yMax = tops.get(0).y;
//        int xMin = tops.get(0).x;
//        int yMin = tops.get(0).y;
//        for (Point point : tops) {
//            if (point.x > xMax)
//                xMax = point.x;
//            if (point.y > yMax)
//                yMax = point.y;
//            if (point.x < xMin)
//                xMin = point.x;
//            if (point.y < yMin)
//                yMin = point.y;
//        }
//        edgeType();
//    }
}
