package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.format.NamedTextColor;
import net.okuri.qol.alcohol.taste.Taste;
import org.bukkit.Bukkit;

import java.util.ArrayList;

class TasteEffectInfo {
    Taste taste;
    double ampParam;
    double durParam;
    double fermentationBuff;
    double delicacyBuff;
}

public class LiquorLore extends Lore {

    private final ArrayList<TasteEffectInfo> effects = new ArrayList<>();
    private double totalDurBuff;
    private double totalAmpBuff;

    public LiquorLore() {
        super(3, "Liquor", NamedTextColor.GOLD);
    }

    @Override
    public void generateLore() {
        addLore(title);
        for (TasteEffectInfo info : effects) {
            Bukkit.getLogger().info("Level Amplifier: " + info.taste.getID());
            Bukkit.getLogger().info("Duration Amplifier: " + info.taste.getDurationAmplifier());
            Bukkit.getLogger().info("Delicacy Buff: " + info.delicacyBuff);
            double[] lore = new double[]{info.ampParam, info.taste.getEffectAmplifier(), info.durParam, info.taste.getEffectDuration() / 1200, info.fermentationBuff, info.delicacyBuff};
            String[] units = new String[]{"p", "lv/p", "p", "min/p", "倍", "倍"};
            addDoubleArrayLore(info.taste.getDisplayName(), info.taste.getColor(), lore, units);
        }
        super.addAmpLore("レベルの増幅率", Lore.A_COLOR, totalAmpBuff);
        super.addAmpLore("持続時間の増幅率", Lore.B_COLOR, totalDurBuff);
    }

    public void addTasteEffect(Taste taste, double ampParam, double durParam, double fermentationBuff, double delicacyBuff) {
        TasteEffectInfo info = new TasteEffectInfo();
        info.taste = taste;
        info.ampParam = ampParam;
        info.durParam = durParam;
        info.fermentationBuff = fermentationBuff;
        info.delicacyBuff = delicacyBuff;
        effects.add(info);
    }

    public void setTotalDurBuff(double totalDurBuff) {
        this.totalDurBuff = totalDurBuff;
    }

    public void setTotalAmpBuff(double totalAmpBuff) {
        this.totalAmpBuff = totalAmpBuff;
    }

}
