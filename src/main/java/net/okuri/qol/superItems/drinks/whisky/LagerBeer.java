package net.okuri.qol.superItems.drinks.whisky;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

import java.time.LocalDateTime;

public class LagerBeer extends Beer {

    public LagerBeer() {
        super(SuperItemType.LAGER_BEER);
        this.setSuperItemType(SuperItemType.LAGER_BEER);
    }

    public LagerBeer(SuperItemStack ingredient, LocalDateTime start) {
        super(SuperItemType.LAGER_BEER, ingredient, start);
        this.setSuperItemType(SuperItemType.LAGER_BEER);
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        return super.getDebugItem(0);
    }
}
