package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.RiceTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Rice extends LiquorResource {
    public Rice() {
        super("米", Material.WHEAT, SuperItemType.RICE, 0.05, RiceTaste.instance, 4);
        super.setMinHumid(0.6);
        super.setMinTemp(0.6);
    }
}
