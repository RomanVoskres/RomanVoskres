package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.model.Vertices;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DrawTexture {

    private static Color fillingColor = Color.rgb(255, 111, 255, 1.0);

    public static void drawPixels(Vertices ver0, Vertices ver1, Vertices ver2,
                                  PixelWriter pw) throws Exception {

        ArrayList<Vertices> sortedVectors = getSortedVectors(ver0, ver1, ver2);
        draw(sortedVectors, pw);
    }

    private static void draw(ArrayList<Vertices> sortedVectors,
                             PixelWriter pw) throws Exception {
        Vertices ver0 = sortedVectors.get(0);
        Vertices ver1 = sortedVectors.get(1);
        Vertices ver2 = sortedVectors.get(2);
        if (sortedVectors.size() == 3) {
            float n;
            if (ver0.getV().getY() > ver1.getV().getY()) {
                n = -1;
            } else {
                n = 1;
            }
            for (float y = ver0.getV().getY(); y * n < ver1.getV().getY(); y += n) {
                if (Math.floor(y) == Math.floor(ver1.getV().getY())) {
                    break;
                }
                for (float x = getXFuncLine(ver0, ver1, y); x < getXFuncLine(ver0, ver2, y); x++) {
                    pw.setColor((int) x, (int) y, fillingColor);
                }
            }
        } else if (sortedVectors.size() == 4) {
            Vertices ver3 = sortedVectors.get(3);
            for (float y = ver0.getV().getY(); y > ver2.getV().getY(); y--) { //down
                for (float x = getXFuncLine(ver0, ver2, y); x < getXFuncLine(ver0, ver3, y); x++) {
                    pw.setColor((int) x, (int) y, fillingColor);
                }
            }
            for (float y = ver1.getV().getY(); y < ver2.getV().getY(); y++) { //up
                for (float x = getXFuncLine(ver1, ver2, y); x < getXFuncLine(ver1, ver3, y); x++) {
                    pw.setColor((int) x, (int) y, fillingColor);
                }
            }
        }
    }

    private static ArrayList<Vertices> getSortedVectors(Vertices ver0, Vertices ver1, Vertices ver2) throws Exception {
        ArrayList<Vertices> sortedVectors = new ArrayList<>();
        if (Math.abs(ver0.getV().getY() - ver1.getV().getY()) < 1E-6) {
            sortedVectors.add(ver2);
            if (ver0.getV().getX() - ver1.getV().getX() < 0) {
                sortedVectors.add(ver0);
                sortedVectors.add(ver1);
            } else {
                sortedVectors.add(ver1);
                sortedVectors.add(ver0);
            }
            return sortedVectors;
        } else if (Math.abs(ver0.getV().getY() - ver2.getV().getY()) < 1E-6) {
            sortedVectors.add(ver1);
            if (ver0.getV().getX() - ver2.getV().getX() < 0) {
                sortedVectors.add(ver0);
                sortedVectors.add(ver2);
            } else {
                sortedVectors.add(ver2);
                sortedVectors.add(ver0);
            }
            return sortedVectors;
        } else if (Math.abs(ver1.getV().getY() - ver2.getV().getY()) < 1E-6) {
            sortedVectors.add(ver0);
            if (ver1.getV().getX() - ver2.getV().getX() < 0) {
                sortedVectors.add(ver1);
                sortedVectors.add(ver2);
            } else {
                sortedVectors.add(ver2);
                sortedVectors.add(ver1);
            }
            return sortedVectors;
        }
        Vertices max = getVectorMaxY(ver0, ver1, ver2);
        Vertices min = getVectorMinY(ver0, ver1, ver2);
        Vertices mid = getVectorMidY(ver0, ver1, ver2);
        Vertices opposite = getOppositeVector(max, min, mid.getV().getY());
        sortedVectors.add(max);
        sortedVectors.add(min);
        if (mid.getV().getX() - opposite.getV().getX() < 0) {
            sortedVectors.add(mid);
            sortedVectors.add(opposite);
        } else {
            sortedVectors.add(opposite);
            sortedVectors.add(mid);
        }
        return sortedVectors;
    }

    private static float getXFuncLine(Vertices ver0, Vertices ver1, float y0) {
        return (y0 - ver0.getV().getY()) * (ver1.getV().getX() - ver0.getV().getX()) / (ver1.getV().getY() - ver0.getV().getY()) + ver0.getV().getX();
    }

    private static Vertices getOppositeVector(Vertices ver0, Vertices ver1, float y0) throws Exception {
        Vector3 ver3 = new Vector3(new float[]{(y0 - ver0.getV().getY()) * (ver1.getV().getX() - ver0.getV().getX()) / (ver1.getV().getY() - ver0.getV().getY()) + ver0.getV().getX(), y0, 0});
        Vector3 verMax = new Vector3(ver1.getV().subtraction(ver0.getV()));
        Vector3 verMin = new Vector3(ver3.subtraction(ver0.getV()));
        float prop = verMin.length() / verMax.length();
        Vector2 verTexture = new Vector2(ver1.getVt().subtraction(ver0.getVt()));
        verTexture.multiply(prop);
        verTexture.plus(ver0.getVt());
        return new Vertices(ver3, verTexture, new Vector3(new float[]{1, 2, 3}));
    }

    private static Vertices getVectorMinY(Vertices ver0, Vertices ver1, Vertices ver2) {
        if (ver0.getV().getY() - ver1.getV().getY() < 0 && ver0.getV().getY() - ver2.getV().getY() < 0) {
            return ver0;
        } else if (ver1.getV().getY() - ver0.getV().getY() < 0 && ver1.getV().getY() - ver2.getV().getY() < 0) {
            return ver1;
        }
        return ver2;
    }

    private static Vertices getVectorMidY(Vertices ver0, Vertices ver1, Vertices ver2) {
        if (ver0.getV().getY() - ver1.getV().getY() < 0 && ver0.getV().getY() - ver2.getV().getY() > 0 ||
                ver0.getV().getY() - ver1.getV().getY() > 0 && ver0.getV().getY() - ver2.getV().getY() < 0) {
            return ver0;
        } else if (ver1.getV().getY() - ver0.getV().getY() < 0 && ver1.getV().getY() - ver2.getV().getY() > 0 ||
                ver1.getV().getY() - ver0.getV().getY() > 0 && ver1.getV().getY() - ver2.getV().getY() < 0) {
            return ver1;
        }
        return ver2;
    }

    private static Vertices getVectorMaxY(Vertices ver0, Vertices ver1, Vertices ver2) {
        if (ver0.getV().getY() - ver1.getV().getY() > 0 && ver0.getV().getY() - ver2.getV().getY() > 0) {
            return ver0;
        } else if (ver1.getV().getY() - ver0.getV().getY() > 0 && ver1.getV().getY() - ver2.getV().getY() > 0) {
            return ver1;
        }
        return ver2;
    }
}