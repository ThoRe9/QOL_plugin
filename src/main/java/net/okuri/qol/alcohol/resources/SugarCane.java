package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.SugarTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class SugarCane extends LiquorResource {
    public SugarCane() {
        super("サトウキビ", Material.SUGAR_CANE, SuperItemType.SUGAR_CANE, 0.01, SugarTaste.instance, 1234);
    }
}
