package net.okuri.qol.superItems.factory.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.LoreGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;

public class Soda extends SuperItem implements SuperCraftable {
    private double strength = 1.0;
    private final SuperItemType superItemType = SuperItemType.SODA;
    public Soda(){
        super(SuperItemType.SODA);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        // settingにmatrixの0~2をわたす
        setting(new SuperItemStack[]{matrix[0], matrix[1], matrix[2]});
    }

    private void setting(SuperItemStack[] coals) {
        // coalのrarity * qualityの平均値をstrengthにする
        double sum = 0;
        for (SuperItemStack coal : coals) {
            double r = PDCC.get(coal.getItemMeta(), PDCKey.X);
            double q = PDCC.get(coal.getItemMeta(), PDCKey.QUALITY);
            sum += (1+ r) * q;
        }
        this.strength = sum / coals.length;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack soda = new SuperItemStack(this.getSuperItemType());
        PotionMeta meta = (PotionMeta)soda.getItemMeta();
        // strength, SuperItemTypeをPersistentDataContainerに保存
        PDCC.set(meta,PDCKey.SODA_STRENGTH, strength);
        PDCC.set(meta,PDCKey.TYPE, superItemType.toString());
        // displayName, loreを設定
        meta.displayName(Component.text("Soda").color(NamedTextColor.AQUA));
        LoreGenerator lore = new LoreGenerator();
        lore.addInfoLore("Sparkling water!");
        lore.addParametersLore("Strength", (strength-1)*10);
        meta.lore(lore.generateLore());

        meta.setColor(Color.AQUA);

        soda.setItemMeta(meta);
        return soda;
    }
    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.strength = 1.0;
        return getSuperItem();
    }
}
