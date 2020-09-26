package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.helpers.EventHelper;

public class QuestionMarkResolved extends GameEvent {
    public final static String key = "question_mark_resolved";
    public EventHelper.RoomResult result;

    public QuestionMarkResolved() {
        super(key, "A '?' room was resolved'");
    }

    public QuestionMarkResolved(EventHelper.RoomResult result) {
        this();
        this.result = result;
    }
}
