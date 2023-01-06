package com.vsu.cgcourse.model;

import java.util.ArrayList;

public class Polygon {

    private final ArrayList<Integer> polygonVertexIndices;
    private final ArrayList<Integer> polygonTextureVertexIndices;
    private final ArrayList<Integer> polygonNormalIndices;

    public Polygon() {
        polygonVertexIndices = new ArrayList<>();
        polygonTextureVertexIndices = new ArrayList<>();
        polygonNormalIndices = new ArrayList<>();
    }

    public ArrayList<Integer> getPolygonVertexIndices() {
        return polygonVertexIndices;
    }

    public ArrayList<Integer> getPolygonTextureVertexIndices() {
        return polygonTextureVertexIndices;
    }

    public ArrayList<Integer> getPolygonNormalIndices() {
        return polygonNormalIndices;
    }

}
