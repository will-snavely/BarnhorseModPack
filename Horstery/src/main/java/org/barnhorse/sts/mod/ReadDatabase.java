package org.barnhorse.sts.mod;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import org.barnhorse.sts.lib.events.BattleStart;
import org.barnhorse.sts.lib.events.DamageAll;
import org.barnhorse.sts.lib.events.DamageSingle;
import org.barnhorse.sts.lib.events.GameEvent;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.File;

public class ReadDatabase {
    public static void fromNitrite(File file) {
        Nitrite db = null;
        ObjectRepository<GameEvent> eventStore = null;
        try {
            db = Nitrite.builder()
                    .filePath(file)
                    .openOrCreate();
            eventStore = db.getRepository(GameEvent.class);
            for (GameEvent event : eventStore.find().toList()) {
                System.out.println(event.actNumber + "," + event.floorNumber + "," + event.getClass().getName());
            }
        } finally {
            if (eventStore != null)
                eventStore.close();
            if (db != null)
                db.close();
        }
    }

    public static void main(String[] args) {
        File file = new File(args[0]);
        fromNitrite(file);
    }
}
