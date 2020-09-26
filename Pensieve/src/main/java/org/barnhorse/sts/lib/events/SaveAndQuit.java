package org.barnhorse.sts.lib.events;

public class SaveAndQuit extends GameEvent {
    public final static String key = "save_and_quit";

    public SaveAndQuit() {
        super(key, "The player saved and quit");
    }
}
