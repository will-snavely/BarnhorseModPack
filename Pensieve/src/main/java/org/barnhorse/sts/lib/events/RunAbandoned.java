package org.barnhorse.sts.lib.events;

public class RunAbandoned extends GameEvent {
    public final static String key = "run_abandoned";

    public RunAbandoned() {
        super(key, "A run was abandoned");
    }
}
