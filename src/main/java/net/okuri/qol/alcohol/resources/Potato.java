package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.PotatoTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Potato extends LiquorResource {
    public Potato() {
        super("じゃがいも", Material.POTATOES, SuperItemType.POTATO, 0.05, PotatoTaste.instance, 121);
    }
}
