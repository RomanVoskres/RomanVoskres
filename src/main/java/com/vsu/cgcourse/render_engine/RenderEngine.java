package com.vsu.cgcourse.render_engine;

import java.util.ArrayList;
import java.util.Collections;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.MeshContext;
import com.vsu.cgcourse.model.Vertices;
import javafx.scene.canvas.GraphicsContext;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final int width,
            final int height,
            MeshContext meshContext) throws Exception {
        Matrix4 modelMatrix = rotateScaleTranslate(meshContext);
        Matrix4 viewMatrix = camera.getViewMatrix();
        Matrix4 projectionMatrix = camera.getProjectionMatrix();

        Matrix4 modelViewProjectionMatrix = new Matrix4(modelMatrix);
        modelViewProjectionMatrix.multiply(viewMatrix);
        modelViewProjectionMatrix.multiply(projectionMatrix);
        modelViewProjectionMatrix.transposite();
        Collections.synchronizedList(meshContext.getMesh().getPolygons()).parallelStream().
                forEachOrdered(p1 -> {
                    final int nVerticesInPolygon = p1.getPolygonVertexIndices().size();
                    ArrayList<Vertices> vertices = new ArrayList<>();
                    for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                        Vector3 vertex = meshContext.getMesh().getVertices().get(p1
                                .getPolygonVertexIndices().get(vertexInPolygonInd));
                        Vector2 textureVertex = meshContext.getMesh().getTextureVertices().get(p1
                                .getPolygonTextureVertexIndices().get(vertexInPolygonInd));
                        Vector3 resultPoint = null;
                        try {
                            resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        vertices.add(new Vertices(resultPoint, textureVertex, null));
                    }

                    if (meshContext.getStatus().isGrid()) {
                        for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                            graphicsContext.strokeLine(
                                    vertices.get(vertexInPolygonInd - 1).getV().getX(),
                                    vertices.get(vertexInPolygonInd - 1).getV().getY(),
                                    vertices.get(vertexInPolygonInd).getV().getX(),
                                    vertices.get(vertexInPolygonInd).getV().getY());
                        }

                        if (nVerticesInPolygon > 0)
                            graphicsContext.strokeLine(
                                    vertices.get(nVerticesInPolygon - 1).getV().getX(),
                                    vertices.get(nVerticesInPolygon - 1).getV().getY(),
                                    vertices.get(0).getV().getX(),
                                    vertices.get(0).getV().getY());
                    }


                    if (meshContext.getStatus().isPainting()) {
                        for (int i = 0; i < vertices.size(); i += 3) {
                                try {
                                    DrawTexture.drawPixels(vertices.get(i), vertices.get(i + 1), vertices.get(i + 2),
                                            graphicsContext.getPixelWriter());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                });
    }
}