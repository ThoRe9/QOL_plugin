package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Wheat extends LiquorResource {
    public Wheat() {
        super("小麦", Material.WHEAT, SuperItemType.WHEAT, 0.05, BarleyTaste.instance, 3);
        super.setMinTemp(0.6);
        super.setMaxHumid(0.6);
        super.setBaseTasteAmp(1.1);
    }
}
