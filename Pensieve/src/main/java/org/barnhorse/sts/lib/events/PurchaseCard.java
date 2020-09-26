package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import org.barnhorse.sts.lib.model.Card;
import org.barnhorse.sts.lib.model.Player;
import org.barnhorse.sts.lib.model.ShopPotion;
import org.barnhorse.sts.lib.model.ShopRelic;
import org.barnhorse.sts.lib.util.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseCard extends GameEvent {
    public final static String key = "purchase_card";

    public Card card;
    public int price;

    public PurchaseCard() {
        super(key, "Purchased a card");
    }

    public PurchaseCard(AbstractCard card, int price) {
        this();
        this.card = new Card(card);
        this.price = price;
    }
}
