package net.okuri.qol.listener;

import net.okuri.qol.Alcohol;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ConsumeListener implements Listener {
    private final Plugin plugin;

    public ConsumeListener(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        SuperItemStack item = new SuperItemStack(event.getItem());
        ItemMeta meta = item.getItemMeta();
        // 材料ポーションなど使用できないアイテムを使用したときの処理
        if (!item.isConsumable()) {
            event.setCancelled(true);
            new ChatGenerator().addWarning("You can't consume this item.").sendMessage(player);
            return;
        }
        // alcoholを使用したとき
        this.alcoholEvent(player, meta);
        // liverHelperを使用したとき
        this.liverHelperEvent(player, item);


    }

    private void alcoholEvent(Player player, ItemMeta meta) {
        // metaのPersistentDataContainerにalcoholKeyがあるか確認
        if (!PDCC.has(meta, PDCKey.ALCOHOL)) {
            return;
        }
        if (!(boolean) PDCC.get(meta, PDCKey.ALCOHOL)) {
            return;
        }
        // プレイヤーのAlcoholLevelを取得
        // ない場合は作成
        if (!PDCC.has(player, PDCKey.ALCOHOL_LEVEL)) {
            PDCC.set(player, PDCKey.ALCOHOL_LEVEL, 0.0);
        }
        double alcLv = PDCC.get(player, PDCKey.ALCOHOL_LEVEL);
        // itemのalcAmountをalcLvに加算
        double alc = PDCC.get(meta, PDCKey.ALCOHOL_AMOUNT);
        alcLv += (alc / 0.35);
        PDCC.set(player, PDCKey.ALCOHOL_LEVEL, alcLv);
        // alcoholの効果を与える
        new Alcohol(plugin).run();

        // drink costの確認
        double drink_cost = PDCC.get(meta, PDCKey.DRINK_COST);
        double capability = 0;
        if (PDCC.has(player, PDCKey.DRINK_COST_CAPABILITY)) {
            capability = PDCC.get(player, PDCKey.DRINK_COST_CAPABILITY);
        } else {
            PDCC.set(player, PDCKey.DRINK_COST_CAPABILITY, 0.1);
            capability = 500;
        }

        if (capability * 2 < drink_cost) {
            new ChatGenerator().addWarning("You drunk so much at once!! so, you died :(").sendMessage(player);
            player.setHealth(0);
        } else if (capability * 1.5 < drink_cost) {
            new ChatGenerator().addWarning("You drunk so much at once!").sendMessage(player);
            player.setHealth(5);
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.CONFUSION, 400, 0));
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 200, 0));
        } else if (capability < drink_cost) {
            new ChatGenerator().addWarning("You drunk little much at once.").sendMessage(player);
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 200, 0));
            PDCC.set(player, PDCKey.DRINK_COST_CAPABILITY, capability + drink_cost * 0.1);
        } else {
            PDCC.set(player, PDCKey.DRINK_COST_CAPABILITY, capability + drink_cost * 0.5);
        }
    }
    private void liverHelperEvent(Player player, SuperItemStack item) {

        if (!item.isSimilar(new SuperItemData(SuperItemType.LIVER_HELPER))) {
            return;
        }
        // プレイヤーのAlcoholLevelを取得
        // ない場合は無視
        if (!PDCC.has(player, PDCKey.ALCOHOL_LEVEL)) {
            return;
        }
        double alcLv = PDCC.get(player, PDCKey.ALCOHOL_LEVEL);
        alcLv = alcLv * 0.9;
        PDCC.set(player, PDCKey.ALCOHOL_LEVEL, alcLv);
        // alcoholの効果を与える
        new Alcohol(plugin).run();
    }

}
