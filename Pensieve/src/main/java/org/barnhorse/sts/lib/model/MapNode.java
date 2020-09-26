package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.map.MapRoomNode;

public class MapNode {
    public String type;
    public int x;
    public int y;
    public boolean hasKey;
    public String symbol;

    public MapNode() {
    }

    public MapNode(MapRoomNode node) {
        if (node != null) {
            this.x = node.x;
            this.y = node.y;
            if (node.room != null) {
                this.type = node.room.getClass().getName();
                this.symbol = node.room.getMapSymbol();
            }
            this.hasKey = node.hasEmeraldKey;
        }
    }
}
