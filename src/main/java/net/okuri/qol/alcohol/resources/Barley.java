package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Barley extends LiquorResource {
    /**
     * 大麦を生成するためのクラスです。
     */
    public Barley() {
        super("大麦", Material.WHEAT, SuperItemType.BARLEY, 0.05, BarleyTaste.instance, 1);
        super.setMaxTemp(0.6);
        super.setMinTemp(0);
    }
}
