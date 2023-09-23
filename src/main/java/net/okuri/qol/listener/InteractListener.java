package net.okuri.qol.listener;

import net.okuri.qol.ChatGenerator;
import net.okuri.qol.Commands;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.foods.Food;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class InteractListener implements Listener {
    // プレイヤーが右クリックしたとき
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event){

        // interactしたプレイヤーのハンドがメインハンドか確認
        if (event.getHand() != org.bukkit.inventory.EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        // itemがnullなら終了
        if (item.getType() == Material.AIR){
            return;
        }
        ItemMeta meta = item.getItemMeta();
        // 手に持ったitemがeatableなら処理をする
        // metaのPersistentDataContainerにeatableKeyがあるか確認
        if (PDCC.has(meta, PDCKey.EATABLE)){
            if (!(boolean) PDCC.get(meta, PDCKey.EATABLE)){
                // playerが満腹なら食べない
                if (player.getFoodLevel() <= 20){
                    new ChatGenerator().addWarning("You are full!").sendMessage(player);
                    event.setCancelled(true);
                    return;
                }
                // 食べる処理
                Food food = new Food();
                food.whenEat(player, item);
                // 消費する
                item.setAmount(item.getAmount() - 1);
                event.setCancelled(true);
            }
        }

        //以下ツールの処理
        // metaのPersistentDataContainerにtypeKeyがあるか確認
        if (!PDCC.has(meta, PDCKey.TYPE)) return;
        SuperItemType type = SuperItemType.valueOf(PDCC.get(meta, PDCKey.TYPE));
        switch (type){
            case ENV_TOOL:
                // ブロックを右クリックしたとき
                if (event.getClickedBlock() != null){
                    // ブロックの座標を取得
                    int x = event.getClickedBlock().getX();
                    int y = event.getClickedBlock().getY();
                    int z = event.getClickedBlock().getZ();
                    World world = event.getClickedBlock().getWorld();
                    ChatGenerator chat = Commands.getEnv(x, y, z, world);
                    chat.sendMessage(player);
                    event.setCancelled(true);
                }
                break;
            default:
                break;
        }
    }
}
