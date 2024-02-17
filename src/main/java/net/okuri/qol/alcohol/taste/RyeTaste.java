package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;

public class RyeTaste extends Taste {
    public static final RyeTaste instance = new RyeTaste();

    private RyeTaste() {
        super("rye", "ライ麦の酸味", NamedTextColor.DARK_GREEN, 0.4, 0);
        super.delicacyBuff = 1.1;
        super.durationAmplifier = 1.1;
        super.levelAmplifier = 0.9;
    }
}
