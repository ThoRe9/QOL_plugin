package net.okuri.qol.loreGenerator;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Loreを生成するためのベースとなるクラスです。
 */
public class LoreGenerator {

    private final ArrayList<Lore> lores = new ArrayList<>();

    public void addLore(Lore lore) {
        lores.add(lore);
    }

    public void setLore(ItemMeta meta) {
        // priorityの昇順にソート
        lores.sort(Comparator.comparingInt(a -> a.priority));
        ArrayList<Component> lore = new ArrayList<>();
        for (Lore l : lores) {
            l.generateLore();
            lore.addAll(l.getLore());
        }
        meta.lore(lore);
    }

}
