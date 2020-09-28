package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class CampfireOption {
    public String kind;
    public boolean usable;

    public CampfireOption() {
    }

    public CampfireOption(AbstractCampfireOption option) {
        this.kind = option.getClass().getName();
        this.usable = option.usable;
    }
}
