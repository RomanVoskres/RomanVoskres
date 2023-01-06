package com.vsu.cgcourse.model;

public class Status {

    private boolean selected = true;
    private boolean changes;
    private boolean isGrid = true;
    private boolean isPainting = true;

    public Status() {}

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    public boolean isPainting() {
        return isPainting;
    }

    public void setPainting(boolean painting) {
        isPainting = painting;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isChanges() {
        return changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }
}
