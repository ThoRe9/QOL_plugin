package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.RumTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Molasses extends LiquorResource {
    public Molasses() {
        super("モラセス", Material.BEDROCK, SuperItemType.MOLASSES, 0.01, RumTaste.instance, 1234);
    }
}
