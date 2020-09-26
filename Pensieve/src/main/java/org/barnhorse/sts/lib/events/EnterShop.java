package org.barnhorse.sts.lib.events;

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

public class EnterShop extends GameEvent {
    public final static String key = "enter_shop";

    public Player player;
    public List<ShopPotion> potions;
    public List<Card> coloredCards;
    public List<Card> colorlessCards;
    public List<ShopRelic> relics;
    public boolean purgeAvailable;
    public int purgeCostRamped;
    public int purgeCostActual;
    public String saleId;

    public EnterShop() {
        super(key, "Entered a shop");
    }

    public EnterShop(AbstractPlayer player, ShopScreen shop) {
        this();
        this.player = new Player(player);
        this.potions = ReflectionHelper
                .<ArrayList<StorePotion>>tryGetFieldValue(shop, "potions", true)
                .orElse(new ArrayList<>())
                .stream()
                .map(ShopPotion::new)
                .collect(Collectors.toList());
        this.coloredCards = shop.coloredCards
                .stream()
                .map(Card::new)
                .collect(Collectors.toList());
        this.colorlessCards = shop.colorlessCards
                .stream()
                .map(Card::new)
                .collect(Collectors.toList());
        this.relics = ReflectionHelper
                .<ArrayList<StoreRelic>>tryGetFieldValue(shop, "relics", true)
                .orElse(new ArrayList<>())
                .stream()
                .map(ShopRelic::new)
                .collect(Collectors.toList());

        this.purgeAvailable = shop.purgeAvailable;
        this.purgeCostRamped = ShopScreen.purgeCost;
        this.purgeCostActual = ShopScreen.actualPurgeCost;

        OnSaleTag saleTag = ReflectionHelper
                .<OnSaleTag>tryGetFieldValue(shop, "saleTag", true)
                .orElse(null);

        if (saleTag != null) {
            this.saleId = saleTag.card.uuid.toString();
        }
    }
}
