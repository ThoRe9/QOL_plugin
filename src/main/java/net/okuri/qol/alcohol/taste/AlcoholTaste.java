package net.okuri.qol.alcohol.taste;

import net.kyori.adventure.text.format.NamedTextColor;

public class AlcoholTaste extends BuffTaste {
    public final static AlcoholTaste instance = new AlcoholTaste();

    private AlcoholTaste() {
        super("alcohol", "酒感", 0, 0.1, NamedTextColor.WHITE, 1, 1);
    }
}
