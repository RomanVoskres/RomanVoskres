package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.model.MeshContext;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class SceneBuilder {
    private ArrayList<MeshContext> meshContexts;

    public SceneBuilder() {
        meshContexts = new ArrayList<>();
    }

    public ArrayList<MeshContext> getMeshContexts() {
        return meshContexts;
    }

}
