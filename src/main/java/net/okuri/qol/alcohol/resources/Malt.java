package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.alcohol.taste.MaltTaste;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class Malt extends FermentationLiquorResource {
    public Malt() {
        super("麦芽", Material.BEDROCK, SuperItemType.MALT, 0.00, MaltTaste.instance, 115);
        super.addResourceTaste(BarleyTaste.instance, 1);
        super.addProductTaste(MaltTaste.instance, 1);
    }

}
