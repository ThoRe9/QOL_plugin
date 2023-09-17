package net.okuri.qol.listener;

import net.okuri.qol.Alcohol;
import net.okuri.qol.ChatGenerator;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ConsumeListener implements Listener {

    @EventHandler
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        // 材料ポーションなど使用できないアイテムを使用したときの処理
        this.unconsumableEvent(event, player, meta);

        // alcoholを使用したとき
        this.alcoholEvent(player, meta);

    }
    private void alcoholEvent(Player player, ItemMeta meta){
        // metaのPersistentDataContainerにalcoholKeyがあるか確認
        if (!meta.getPersistentDataContainer().has(Alcohol.alcKey, PersistentDataType.BOOLEAN)){
            return;
        }
        if (!meta.getPersistentDataContainer().get(Alcohol.alcKey, PersistentDataType.BOOLEAN)){
            return;
        }
        // プレイヤーのAlcoholLevelを取得
        // ない場合は作成
        if (!player.getPersistentDataContainer().has(Alcohol.alcLvKey, PersistentDataType.DOUBLE)){
            player.getPersistentDataContainer().set(Alcohol.alcLvKey, PersistentDataType.DOUBLE, 0.00);
        }
        double alcLv = player.getPersistentDataContainer().get(Alcohol.alcLvKey, PersistentDataType.DOUBLE);
        // itemのalcAmount * alcPerをalcLvに加算
        double alcAmount = meta.getPersistentDataContainer().get(Alcohol.alcAmountKey, PersistentDataType.DOUBLE);
        double alcPer = meta.getPersistentDataContainer().get(Alcohol.alcPerKey, PersistentDataType.DOUBLE);
        alcLv += alcAmount * alcPer / 350;
        player.getPersistentDataContainer().set(Alcohol.alcLvKey, PersistentDataType.DOUBLE, alcLv);
        // alcoholの効果を与える
        new Alcohol().run();
    }
    private void unconsumableEvent(PlayerItemConsumeEvent event, Player player, ItemMeta meta){
        NamespacedKey key = new NamespacedKey("qol", "qol_consumable");
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN)){
            if (!meta.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN)){
                new ChatGenerator().addWarning("You cannot use it!").sendMessage(player);
                event.setCancelled(true);
            }
        }
    }

}
