package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class Player {
    public String chosenClass;
    public int gameHandSize;
    public int masterHandSize;
    public int startingMaxHP;
    public List<Card> masterDeck;
    public List<Card> drawPile;
    public List<Card> hand;
    public List<Card> discardPile;
    public List<Card> exhaustPile;
    public List<Card> limbo;
    public List<Relic> relics;
    List<Blight> blights;
    public int potionSlots;
    public List<Potion> potions;
    public int energy;
    public int energyMaster;
    public boolean isEndingTurn;
    public boolean viewingRelics;
    public boolean inspectMode;
    public int damagedThisCombat;
    public String title;
    public List<Orb> orbs;
    public int masterMaxOrbs;
    public int maxOrbs;
    public Stance stance;

    public Player(AbstractPlayer player) {
        this.chosenClass = player.chosenClass.name();
        this.gameHandSize = player.gameHandSize;
        this.masterHandSize = player.masterHandSize;
        this.startingMaxHP = player.startingMaxHP;
        this.masterDeck = player.masterDeck.group.stream().map(Card::new).collect(Collectors.toList());
        this.drawPile = player.drawPile.group.stream().map(Card::new).collect(Collectors.toList());
        this.hand = player.hand.group.stream().map(Card::new).collect(Collectors.toList());
        this.discardPile = player.discardPile.group.stream().map(Card::new).collect(Collectors.toList());
        this.exhaustPile = player.exhaustPile.group.stream().map(Card::new).collect(Collectors.toList());
        this.limbo = player.limbo.group.stream().map(Card::new).collect(Collectors.toList());
        this.relics = player.relics.stream().map(Relic::new).collect(Collectors.toList());
        this.blights = player.blights.stream().map(Blight::new).collect(Collectors.toList());
        this.potionSlots = player.potionSlots;
        this.potions = player.potions.stream().map(Potion::new).collect(Collectors.toList());
        this.energy = player.energy.energy;
        this.energyMaster = player.energy.energyMaster;
        this.isEndingTurn = player.isEndingTurn;
        this.viewingRelics = player.viewingRelics;
        this.inspectMode = player.inspectMode;
        this.damagedThisCombat = player.damagedThisCombat;
        this.title = player.title;
        this.orbs = player.orbs.stream().map(Orb::new).collect(Collectors.toList());
        this.masterMaxOrbs = player.masterMaxOrbs;
        this.maxOrbs = player.maxOrbs;
        this.stance = new Stance(player.stance);
    }
}
