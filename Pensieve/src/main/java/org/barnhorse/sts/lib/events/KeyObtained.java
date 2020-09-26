package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

public class KeyObtained extends GameEvent {
    public final static String key = "key_obtained";
    public ObtainKeyEffect.KeyColor color;

    public KeyObtained() {
        super(key, "A key was obtained");
    }

    public KeyObtained(ObtainKeyEffect.KeyColor color) {
        this();
        this.color = color;
    }
}
