package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.HopTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Hop extends LiquorResource {
    public Hop() {
        super("Hop", Material.VINE, SuperItemType.HOP, 0.05, HopTaste.instance, 241);
    }
}
