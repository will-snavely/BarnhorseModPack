package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;
import org.barnhorse.sts.lib.model.Creature;
import org.barnhorse.sts.lib.util.ReflectionHelper;

public class EnemyMove extends GameEvent {
    public final static String key = "enemy_move";
    public String message;
    public Creature source;

    public EnemyMove() {
        super(key, "Enemy used a move");
    }

    public EnemyMove(ShowMoveNameAction action) {
        this();
        this.source = new Creature(action.source);
        this.message = ReflectionHelper
                .<String>tryGetFieldValue(action, "msg", true)
                .orElse(null);
    }
}
