package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.GrapeBitterness;
import net.okuri.qol.alcohol.taste.GrapeSweetness;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class FermentedGrape extends FermentationLiquorResource {
    public FermentedGrape() {
        super("発酵したぶどう", Material.BEDROCK, SuperItemType.FERMENTED_GRAPE, 0, GrapeSweetness.instance, 123123);
        super.addResourceTaste(GrapeSweetness.instance, 1);
        super.addProductTaste(GrapeBitterness.instance, 0.5);
        super.addProductTaste(GrapeSweetness.instance, 0.3);
    }
}
