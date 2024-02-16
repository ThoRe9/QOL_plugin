package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;

public class HopTaste extends BuffTaste {
    public static final HopTaste instance = new HopTaste();

    private HopTaste() {
        super("hop", "ホップ", 0, 1.0, NamedTextColor.GREEN, 0.9, 0.9);
        super.delicacyBuff = 1.5;
    }
}
