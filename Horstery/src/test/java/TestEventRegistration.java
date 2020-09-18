import org.barnhorse.sts.lib.events.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEventRegistration {

    @Test
    void testRegistrations() {
        assertEquals(EventMapper.eventMap.get(CardAddedToDeck.key), CardAddedToDeck.class);
        assertEquals(EventMapper.eventMap.get(CardRemovedFromDeck.key), CardRemovedFromDeck.class);
        assertEquals(EventMapper.eventMap.get(CardUsed.key), CardUsed.class);
    }
}
