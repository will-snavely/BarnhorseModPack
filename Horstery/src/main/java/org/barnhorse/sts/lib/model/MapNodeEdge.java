package org.barnhorse.sts.lib.model;

public class MapNodeEdge {
    public int srcX;
    public int srcY;
    public int destX;
    public int destY;

    public MapNodeEdge() {
    }

    public MapNodeEdge(int srcX, int srcY, int destX, int destY) {
        this.srcX = srcX;
        this.srcY = srcY;
        this.destX = destX;
        this.destY = destY;
    }
}
