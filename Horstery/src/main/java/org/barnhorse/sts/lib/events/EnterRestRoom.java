package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import org.barnhorse.sts.lib.model.CampfireOption;
import org.barnhorse.sts.lib.util.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

public class EnterRestRoom extends GameEvent {
    public final static String key = "enter_rest_room";
    public List<CampfireOption> options;


    public EnterRestRoom() {
        super(key, "Entered a rest room");
    }

    public EnterRestRoom(RestRoom room) {
        this();
        ArrayList<AbstractCampfireOption> options =
                ReflectionHelper.<ArrayList<AbstractCampfireOption>>tryGetFieldValue(
                        room.campfireUI,
                        "buttons",
                        true).orElse(null);

        if (options != null) {
            this.options = new ArrayList<>();
            for (AbstractCampfireOption option : options) {
                this.options.add(new CampfireOption(option));
            }
        }
    }
}
