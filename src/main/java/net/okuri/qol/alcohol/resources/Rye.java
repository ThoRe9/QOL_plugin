package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.alcohol.taste.RyeTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Rye extends LiquorResource {
    public Rye() {
        super("ライ麦", Material.WHEAT, SuperItemType.RYE, 0.05, BarleyTaste.instance, 2);
        super.setMaxTemp(0);
        super.setBaseTasteAmp(0.7);
        super.setAdditionalTaste(RyeTaste.instance, 0.5);
    }
}
