package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import org.barnhorse.sts.lib.model.CampfireOption;

public class RestRoomOptionSelected extends GameEvent {
    public final static String key = "rest_room_option_selected";
    public CampfireOption option;


    public RestRoomOptionSelected() {
        super(key, "Chose a rest room option");
    }

    public RestRoomOptionSelected(AbstractCampfireOption option) {
        this();
        if (option != null) {
            this.option = new CampfireOption(option);
        }
    }
}
