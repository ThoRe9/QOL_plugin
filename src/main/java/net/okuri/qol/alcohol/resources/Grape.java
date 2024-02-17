package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.GrapeSweetness;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Grape extends LiquorResource {
    public Grape() {
        super("ぶどう", Material.ACACIA_LEAVES, SuperItemType.GRAPE, 0.05, GrapeSweetness.instance, 12145);
    }
}
