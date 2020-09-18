package org.barnhorse.sts.lib.events;

import java.util.HashMap;
import java.util.Map;

public class EventMapper {
    public static final Map<String, Class> eventMap;

    static class ConflictingEventKeyError extends RuntimeException {
        ConflictingEventKeyError(String key) {
            super(String.format(
                    "An event with the key %s already exists (value=%s)",
                    key,
                    eventMap.get(key)));
        }
    }

    private static void register(String key, Class cls) {
        if (eventMap.containsKey(key)) {
            throw new ConflictingEventKeyError(key);
        }
        eventMap.put(key, cls);
    }

    static {
        eventMap = new HashMap<>();
        register(BattleStart.key, BattleStart.class);
        register(CardAddedToDeck.key, CardAddedToDeck.class);
        register(CardDraw.key, CardDraw.class);
        register(CardRemovedFromDeck.key, CardRemovedFromDeck.class);
        register(CardUsed.key, CardUsed.class);
    }
}
