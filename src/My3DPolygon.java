import javafx.geometry.Point3D;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.awt.*;
import java.util.ArrayList;

public class My3DPolygon extends MyPolygon {

    /**
    * Как задать параллелепипед:
     * Введите 4 вершины по порядку на одной грани,
     * затем в таком же порядке на другой.
     */
    private ArrayList<Point3D> tops;
    private ArrayList<Point> topsPr;
    private ArrayList<Point> projection = new ArrayList<>();

    My3DPolygon(ArrayList<Point3D> tops) {
        this.tops = tops;
    }

    private void topsProjection(double nn) {
        topsProjection(nn, 0);
    }

    private void topsProjection(double nn, double rr) {
        double[][] B = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, rr}, {0, 0, nn, 1}};
        topsProjection(B);
    }

    private void topsProjection(double[][] B) {
        topsPr = new ArrayList<>();
        int[] A = new int[4];
        int len = tops.size();
        for (int k = 0; k < len; k++) {
            double[] C = {0, 0, 0, 0};
            A[0] = (int) tops.get(k).getX();
            A[1] = (int) tops.get(k).getY();
            A[2] = (int) tops.get(k).getZ();
            A[3] = 1;
            // Matrix multiplication
            int m = 4;
            int n = 4;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    C[i] += A[j] * B[j][i];
                }
            }
            topsPr.add(new Point((int)(C[0]/C[3]), (int)(C[1]/C[3])));
        }
    }

    public ArrayList<Point> getZProjection(double n) {
        topsProjection(n);
        projectionDrawing();
        return projection;
    }

    public ArrayList<Point> getSinglePointPerspectiveProjection(double n, double r) {
        topsProjection(n, r);
        projectionDrawing();
        return projection;
    }

    private void projectionDrawing() {
        for (int i=0; i<4; i++) {
            projection.addAll((new MyLine(topsPr.get(i % 4), topsPr.get((i + 1) % 4))).getPoints()); // Ребра одной грани
            projection.addAll((new MyLine(topsPr.get(i % 4+4), topsPr.get((i + 1) % 4+4))).getPoints()); // Ребра противоположной грани
            projection.addAll((new MyLine(topsPr.get(i), topsPr.get(i+4))).getPoints()); // Ребра между гранями
        }
    }

    public ArrayList<Point> povorot(double x, double y, double z, double fi) {
        double nx = x/Math.sqrt(x*x+y*y+z*z);
        double ny = y/Math.sqrt(x*x+y*y+z*z);
        double nz = z/Math.sqrt(x*x+y*y+z*z);
        double cosfi = Math.cos(fi);
        double sinfi = Math.sin(fi);
        double[][] T = new double[4][4];
        T[0][0] = cosfi + nx*nx*(1-cosfi);
        T[0][1] = nz*sinfi+nx*ny*(1-cosfi);
        T[0][2] = -ny*sinfi+nx*ny*(1-cosfi);
        T[0][3] = 0;
        T[1][0] = nx*ny*(1-cosfi)-nz*sinfi;
        T[1][1] = cosfi+ny*ny*(1-cosfi);
        T[1][2] = nx*sinfi+ny*nz*(1-cosfi);
        T[1][3] = 0;
        T[2][0] = ny*sinfi+nx*nz*(1-cosfi);
        T[2][1] = -nx*sinfi+ny*nz*(1-cosfi);
        T[2][2] = cosfi+nz*nz*(1-cosfi);
        T[2][3] = 0;
        T[3][0] = 0;
        T[3][1] = 0;
        T[3][2] = 0;
        T[3][3] = 1;
        topsProjection(T);
        projectionDrawing();
        return projection;
    }


}