package net.okuri.qol.superItems.resources;

import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;

public class Wheat extends SuperWheat {
    public Wheat() {
        super(SuperItemType.WHEAT);
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        if (args.length == 2) {
            return super.getDebugItem(args[0], args[1]);
        }
        return super.getDebugItem(2);
    }
}
