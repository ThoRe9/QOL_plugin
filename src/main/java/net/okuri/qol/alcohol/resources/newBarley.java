package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class newBarley extends LiquorResource {
    /**
     * 大麦を生成するためのクラスです。
     */
    public newBarley() {
        super("大麦", Material.WHEAT, SuperItemType.NEW_BARLEY, 0.01, BarleyTaste.instance, 1);
    }
}
