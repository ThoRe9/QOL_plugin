package net.okuri.qol.superItems.factory.resources;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;

public class Wheat extends SuperWheat {
    public Wheat() {
        super(SuperItemType.WHEAT);
    }

    @Override
    public SuperResourceStack getDebugItem(int... args) {
        if (args.length == 2) {
            return super.getDebugItem(args[0], args[1]);
        }
        return super.getDebugItem(2);
    }
}
