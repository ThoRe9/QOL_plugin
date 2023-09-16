package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.okuri.qol.qolCraft.maturation.Maturation;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;

public class SignFunction implements Listener {
    // 看板に[~~~]と書いている場合、特別な処理を行う。

    // 看板の内容を変更したときのイベント
    @EventHandler
    public void onSign(SignChangeEvent event){
        Player player = event.getPlayer();
        // 看板の内容を取得する
        String[] lines = event.getLines();
        if (lines[0].equals("[QOL]")){
            if (lines[1].equals("[Maturation]")){
                if (!MaturationEvent(event)){
                    event.setCancelled(true);
                    new ChatGenerator().addWarning("You did it in the wrong way!").sendMessage(player);
                }
            }
            if (lines[1].equals("[Distillation]")){
                if (!DistillationEvent(event)){
                    event.setCancelled(true);
                    new ChatGenerator().addWarning("You did it in the wrong way!").sendMessage(player);
                }
            }
        }
    }

    // 看板がMaturationのときの処理
    public boolean MaturationEvent(SignChangeEvent event){
        // この看板が向いている方向を取得する
        if (event.getBlock().getBlockData() instanceof Directional) {
            Block sign = event.getBlock();
            // この看板が向いている方向の逆のブロックを取得する
            Block barrel = sign.getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace());
            Player player = event.getPlayer();
            // そのブロックが樽であるかどうかを確認する
            if (barrel.getType() == Material.BARREL){
                Barrel barrelData = (Barrel)barrel.getState();
                // 中にひとつだけアイテムが入っているかどうかを確認する
                int count = 0;
                for (ItemStack item : barrelData.getInventory().getContents()){
                    if (item != null){
                        count++;
                    }
                }
                if (count == 1){
                    // そのアイテムが熟成可能なアイテムであるかどうかを確認する
                    ItemStack item = barrelData.getInventory().getContents()[0];
                    ItemMeta meta = item.getItemMeta();
                    if (meta.getPersistentDataContainer().has(SuperItemType.typeKey, PersistentDataType.STRING)){
                        if (Maturation.isMaturationable(SuperItemType.valueOf(meta.getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING)))){

                            event.line(0, Component.text("[QOL]"));
                            event.line(1, Component.text("[Maturation]"));
                            event.line(2, Component.text(LocalDateTime.now().toString()));
                            event.line(3, Component.text(meta.getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING)));
                            ProtectedBlock pb = new ProtectedBlock();
                            pb.setProtectedBlock(barrel, true);
                            pb.setProtectedBlock(sign, true);
                            new ChatGenerator().addSuccess("Maturation started!").sendMessage(player);
                            return true;
                        } else {
                            new ChatGenerator().addWarning("This item is not maturationable!").sendMessage(player);
                        }
                    } else{
                        new ChatGenerator().addWarning("This item is not maturationable!").sendMessage(player);
                    }
                } else {
                    new ChatGenerator().addWarning("There is something wrong in this barrel!").sendMessage(player);
                }

            } else {
                new ChatGenerator().addWarning("This block is not barrel!").sendMessage(player);
            }
        }
        return false;
    }

    // 看板がDistillationのときの処理
    public boolean DistillationEvent(SignChangeEvent event){
        // この看板が向いている方向を取得する
        if (event.getBlock().getBlockData() instanceof Directional){
            Block sign = event.getBlock();
            // この看板が向いている方向の逆のブロックを取得する
            Block furnece = sign.getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace());
            Player player = event.getPlayer();
            // そのブロックがかまどであるかどうかを確認する
            if (furnece.getType() == Material.FURNACE){
                event.line(0, Component.text("[QOL]"));
                event.line(1, Component.text("[Distillation]"));
                event.line(2, Component.text("Activated!"));
                ProtectedBlock pb = new ProtectedBlock();
                pb.setProtectedBlock(furnece, true);

            } else {
                new ChatGenerator().addWarning("This block is not furnace!").sendMessage(player);
            }
        }


        return false;
    }


}
