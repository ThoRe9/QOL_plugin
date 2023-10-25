package net.okuri.qol.superItems.drinks.whisky;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

import java.time.LocalDateTime;

public class AleBeer extends Beer {

    public AleBeer() {
        super(SuperItemType.ALE_BEER);
        this.setSuperItemType(SuperItemType.ALE_BEER);
    }

    public AleBeer(SuperItemStack ingredient, LocalDateTime start) {
        super(SuperItemType.ALE_BEER, ingredient, start);
        this.setSuperItemType(SuperItemType.ALE_BEER);
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem(1);
    }
}
