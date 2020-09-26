package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import org.barnhorse.sts.lib.model.MapNode;
import org.barnhorse.sts.lib.model.MapNodeEdge;

import java.util.ArrayList;
import java.util.List;

public class MapGenerated extends GameEvent {
    public final static String key = "map_generated";
    public List<MapNode> nodes;
    public List<MapNodeEdge> edges;
    public String bossKey;

    public MapGenerated() {
        super(key, "Act map generated");
    }

    public MapGenerated(ArrayList<ArrayList<MapRoomNode>> map, String boss) {
        this();
        this.bossKey = boss;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                MapRoomNode room = map.get(i).get(j);
                if (room != null && room.getRoom() != null) {
                    nodes.add(new MapNode(room.x, room.y, room));
                    for (MapEdge edge : room.getEdges()) {
                        edges.add(new MapNodeEdge(
                                edge.srcX,
                                edge.srcY,
                                edge.dstX,
                                edge.dstY));
                    }
                }
            }
        }
    }
}
