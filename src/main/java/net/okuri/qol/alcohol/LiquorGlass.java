package net.okuri.qol.alcohol;

import net.okuri.qol.superItems.SuperItemType;

public class LiquorGlass extends Liquor {
    public LiquorGlass() {
        super(SuperItemType.LIQUOR_GLASS);
        super.setRecentAmount(0.3);
        super.adjustTasteValue();
    }


}
