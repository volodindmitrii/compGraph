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
    private ArrayList<Point3D> tops3DPr = new ArrayList<>();
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
            tops3DPr.add(new Point3D((int)(C[0]/C[3]), (int)(C[1]/C[3]), (int)(C[2]/C[3])));
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

    public ArrayList<Point> povorot(boolean ifWire, double x, double y, double z, double fi) {
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
        if (ifWire) projectionDrawing();
        else projectionNonWireDrawing();
        return projection;
    }

    private void projectionNonWireDrawing() {
        boolean[] ifDraw = new boolean[6];
        double[] normal;
        double[] a = new double[3];
        double[] b = new double[3];
        double[] zed = {0, 0, 1};
        Point3D subta;
        Point3D subtb;
        for (int i=0; i<4; i++){
            subta = tops3DPr.get((i+1)%4).subtract(tops3DPr.get(i));
            subtb = tops3DPr.get((i+1)%4+4).subtract(tops3DPr.get(i));
            a[0] = subta.getX();
            a[1] = subta.getY();
            a[2] = subta.getZ();
            b[0] = subtb.getX();
            b[1] = subtb.getY();
            b[2] = subtb.getZ();
            normal = vecMult(a, b);
            if (zed[0]*normal[0]+zed[1]*normal[1]+zed[2]*normal[2]<=0)
                ifDraw[i] = false;
            else ifDraw[i] = true;
        }
        subta = tops3DPr.get(1).subtract(tops3DPr.get(0));
        subtb = tops3DPr.get(3).subtract(tops3DPr.get(0));
        a[0] = subta.getX();
        a[1] = subta.getY();
        a[2] = subta.getZ();
        b[0] = subtb.getX();
        b[1] = subtb.getY();
        b[2] = subtb.getZ();
        normal = vecMult(a, b);
        if (zed[0]*normal[0]+zed[1]*normal[1]+zed[2]*normal[2]<=0)
            ifDraw[4] = true;
        else ifDraw[4] = false;
        subta = tops3DPr.get(7).subtract(tops3DPr.get(4));
        subtb = tops3DPr.get(5).subtract(tops3DPr.get(4));
        a[0] = subta.getX();
        a[1] = subta.getY();
        a[2] = subta.getZ();
        b[0] = subtb.getX();
        b[1] = subtb.getY();
        b[2] = subtb.getZ();
        normal = vecMult(a, b);
        if (zed[0]*normal[0]+zed[1]*normal[1]+zed[2]*normal[2]<=0)
            ifDraw[5] = true;
        else ifDraw[5] = false;
        for (int i=0; i<4; i++) {
            if (ifDraw[i]) {
                projection.addAll((new MyLine(topsPr.get(i), topsPr.get((i+1)%4))).getPoints());
                projection.addAll((new MyLine(topsPr.get((i+1)%4), topsPr.get((i+1)%4+4))).getPoints());
                projection.addAll((new MyLine(topsPr.get((i+1)%4+4), topsPr.get(i+4))).getPoints());
                projection.addAll((new MyLine(topsPr.get(i+4), topsPr.get(i))).getPoints());
            }
            if (ifDraw[4]) {
                projection.addAll((new MyLine(topsPr.get(0), topsPr.get(1))).getPoints());
                projection.addAll((new MyLine(topsPr.get(1), topsPr.get(2))).getPoints());
                projection.addAll((new MyLine(topsPr.get(2), topsPr.get(3))).getPoints());
                projection.addAll((new MyLine(topsPr.get(3), topsPr.get(0))).getPoints());
            }
            if (ifDraw[5]) {
                projection.addAll((new MyLine(topsPr.get(4), topsPr.get(5))).getPoints());
                projection.addAll((new MyLine(topsPr.get(5), topsPr.get(6))).getPoints());
                projection.addAll((new MyLine(topsPr.get(6), topsPr.get(7))).getPoints());
                projection.addAll((new MyLine(topsPr.get(7), topsPr.get(4))).getPoints());
            }
        }
    }

    public ArrayList<Point> getNonWireZProjection(double n) {
        topsProjection(n);
        projectionNonWireDrawing();
        return projection;
    }

    private double[] vecMult(double[] a, double[] b){
        double[] c = new double[3];
        c[0] = a[1]*b[2]-a[2]*b[1];
        c[1] = a[0]*b[2]-a[2]*b[0];
        c[2] = a[0]*b[1]-a[1]*b[0];
        return c;
    }

}