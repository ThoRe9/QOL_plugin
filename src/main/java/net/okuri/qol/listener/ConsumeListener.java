package net.okuri.qol.listener;

import net.okuri.qol.Alcohol;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
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
        // itemのalcAmount * alcPerをalcLvに加算
        double alcAmount = PDCC.get(meta, PDCKey.ALCOHOL_AMOUNT);
        double alcPer = PDCC.get(meta, PDCKey.ALCOHOL_PERCENTAGE);
        alcLv += (alcAmount * alcPer / 350);
        PDCC.set(player, PDCKey.ALCOHOL_LEVEL, alcLv);
        // alcoholの効果を与える
        new Alcohol(plugin).run();
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
