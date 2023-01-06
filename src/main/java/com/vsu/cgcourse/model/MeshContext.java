package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.render_engine.Converter;

public class MeshContext {

    private Mesh oldMesh;
    private Mesh mesh;
    private Converter converter;
    private Status status;

    public MeshContext(Mesh mesh) {
        this.mesh = mesh;
        this.converter = new Converter(1, 1, 1, 0, 0, 0, new Vector3(new float[] {0, 0, 0}));
        status = new Status();
    }
    public MeshContext(float scaleX, float scaleY, float scaleZ, float angleX, float angleY, float angleZ) {
        mesh = new Mesh();
        converter = new Converter(scaleX, scaleY, scaleZ, angleX, angleY, angleZ, new Vector3(new float[] {0, 0, 0}));
    }
    public void setNewMeshConverter() {
        this.converter = new Converter(1, 1, 1, 0, 0, 0,  new Vector3(new float[] {0, 0, 0}));
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Converter getConverter() {
        return converter;
    }

    public Mesh getOldMesh() {
        return oldMesh;
    }

    public void setOldMesh(Mesh oldMesh) {
        this.oldMesh = oldMesh;
    }

    public Status getStatus() {
        return status;
    }
}
