package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MapNode {
    public String type;
    public int x;
    public int y;
    public boolean hasKey;
    public String symbol;

    public MapNode() {
    }

    public MapNode(int x, int y, MapRoomNode node) {
        this.x = x;
        this.y = y;

        if (node != null) {
            if (node.room != null) {
                this.type = node.room.getClass().getName();
                this.symbol = node.room.getMapSymbol();
            }
            this.hasKey = node.hasEmeraldKey;
        }
    }
}
