package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.okuri.qol.drinks.WhiskyIngredient;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SignFunction implements Listener {
    // 看板に[~~~]と書いている場合、特別な処理を行う。
    @EventHandler
    public void onSign(SignChangeEvent event){
        Player player = event.getPlayer();
        // 看板の内容を取得する
        String[] lines = event.getLines();
        if (lines[0].equals("[QOL]")){
            if (lines[1].equals("[Maturation]")){
                if (!MaturationEvent(event)){
                    event.setCancelled(true);
                    player.sendMessage("You did it in the wrong way!");
                }
            }
            if (lines[1].equals("[Distillation]")){
                if (!DistillationEvent(event)){
                    event.setCancelled(true);
                    player.sendMessage("You did it in the wrong way!");
                }
            }
        }
    }

    public boolean MaturationEvent(SignChangeEvent event){
        // この看板が向いている方向を取得する
        if (event.getBlock().getBlockData() instanceof Directional) {
            //event.getPlayer().sendMessage(((Directional) event.getBlock().getBlockData()).getFacing().toString());
            Block sign = event.getBlock();
            // この看板が向いている方向の逆のブロックを取得する
            Block barrel = sign.getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace());
            Player player = event.getPlayer();
            //event.getPlayer().sendMessage(barrel.getType().toString());
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
                player.sendMessage(String.valueOf(barrelData.getInventory().getSize()));
                if (count == 1){
                    // そのアイテムが熟成可能なアイテムであるかどうかを確認する
                    ItemStack item = barrelData.getInventory().getContents()[0];
                    ItemMeta meta = item.getItemMeta();
                    if (meta.getPersistentDataContainer().has(SuperItemType.typeKey, PersistentDataType.STRING)){
                        if (SuperItemType.valueOf(meta.getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING)).isMaturationable()){

                            event.line(0, Component.text("[QOL]"));
                            event.line(1, Component.text("[Maturation]"));
                            event.line(2, Component.text(LocalDateTime.now().toString()));
                            event.line(3, Component.text(meta.getPersistentDataContainer().get(SuperItemType.typeKey, PersistentDataType.STRING)));
                            ProtectedBlock pb = new ProtectedBlock();
                            pb.setProtectedBlock(barrel, true);
                            pb.setProtectedBlock(sign, true);
                            player.sendMessage("Maturation started!");
                            return true;
                        } else {
                            player.sendMessage("1 This item is not maturationable!");
                        }
                    } else{
                        player.sendMessage("2 This item is not maturationable!");
                    }
                } else {
                    player.sendMessage("There are too many items in the barrel!");
                }

            } else {
                player.sendMessage("This block is not barrel!");
            }
        }
        return false;
    }

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
                player.sendMessage("This block is not furnace!");
            }
        }


        return false;
    }


}
