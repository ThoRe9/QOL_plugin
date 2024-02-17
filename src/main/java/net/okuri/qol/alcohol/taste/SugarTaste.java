package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;

public class SugarTaste extends Taste {
    public static final SugarTaste instance = new SugarTaste();

    private SugarTaste() {
        super("sugar", "砂糖", NamedTextColor.WHITE, 0.9, 1);
        super.delicacyBuff = 0.9;
    }
}
