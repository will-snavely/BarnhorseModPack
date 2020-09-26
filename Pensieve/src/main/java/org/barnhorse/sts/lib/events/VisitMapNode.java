package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.model.MapNode;
import org.barnhorse.sts.lib.model.Room;

public class VisitMapNode extends GameEvent {
    public final static String key = "visit_map_node";
    public MapNode node;

    public VisitMapNode() {
        super(key, "Visited a new map node");
    }

    public VisitMapNode(MapRoomNode node) {
        this();
        this.node = new MapNode(node);
    }
}
