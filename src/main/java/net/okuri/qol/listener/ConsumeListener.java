package net.okuri.qol.listener;

import net.okuri.qol.Alcohol;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConsumeListener implements Listener {

    @EventHandler
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        // 材料ポーションなど使用できないアイテムを使用したときの処理
        if (this.unconsumableEvent(event, player, meta)) {
            return;
        }
        // alcoholを使用したとき
        this.alcoholEvent(player, meta);

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
        alcLv += alcAmount * alcPer / 350;
        PDCC.set(player, PDCKey.ALCOHOL_LEVEL, alcLv);
        // alcoholの効果を与える
        new Alcohol().run();
    }

    private boolean unconsumableEvent(PlayerItemConsumeEvent event, Player player, ItemMeta meta) {
        if (PDCC.has(meta, PDCKey.CONSUMABLE)) {
            if (!(boolean) PDCC.get(meta, PDCKey.CONSUMABLE)) {
                new ChatGenerator().addWarning("You cannot use it!").sendMessage(player);
                event.setCancelled(true);
                return true;
            }
        }
        return false;
    }

}
