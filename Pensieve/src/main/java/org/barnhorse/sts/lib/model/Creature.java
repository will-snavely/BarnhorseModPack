package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.List;
import java.util.stream.Collectors;

public class Creature {
    public String name;
    public String id;
    public int currentHealth;
    public int currentBlock;
    public int maxHealth;
    public boolean isEscaping;
    public List<Power> powers;
    public int objectId;

    public Creature() {
    }

    public Creature(AbstractCreature creature) {
        this.name = creature.name;
        this.id = creature.id;
        this.currentHealth = creature.currentHealth;
        this.currentBlock = creature.currentBlock;
        this.maxHealth = creature.maxHealth;
        this.isEscaping = creature.isEscaping;
        this.powers = creature.powers.stream().map(Power::new).collect(Collectors.toList());
        this.objectId = creature.hashCode();
    }
}
