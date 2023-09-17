package net.okuri.qol.listener;

import net.okuri.qol.ChatGenerator;
import net.okuri.qol.superItems.SuperCoal;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.joml.Random;

public class GetSuperItemListener implements Listener {
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockType = block.getType();

        // SilkTouchのエンチャントを持つツールをプレイヤーが持っていた場合はここで終了
        if (event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.SILK_TOUCH)) {
            return;
        }
        // SuperItemをドロップさせる最初の処理
        // elseifで追加していく
        if (blockType == Material.WHEAT) {
            this.superWheatEvent(event);
        } else if (blockType == Material.COAL_ORE){
            this.superCoalEvent(event);
        }
    }

    private void superWheatEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        // 成熟しているかどうか
        if (block.getBlockData().getAsString().contains("age=7")){

            // 10%の確率でSuperWheatをドロップさせる
            Random rand = new Random();
            int n = rand.nextInt(100);
            // debug
            new ChatGenerator().addDebug(String.valueOf(n)).sendMessage(player);

            if (n < 1) {
                // SuperWheat を与える
                new ChatGenerator().addSuccess("You got a SuperWheat!").sendMessage(player);

                double temp =  player.getLocation().getBlock().getTemperature();
                Bukkit.getServer().getLogger().info("temp: " + temp);
                Biome biome = player.getLocation().getBlock().getBiome();
                int biomeID = biome.ordinal();
                // TODO qualityはJOBのレベルによって変える
                ItemStack superWheat = new SuperWheat(block.getX(), block.getY(), block.getZ(), player.getName(), temp, biomeID, 1.0 + n/30.0).getSuperItem();
                player.getInventory().addItem(superWheat);
            }
        }
    }

    private void superCoalEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Random rand = new Random();
        int n = rand.nextInt(100);
        new ChatGenerator().addDebug(String.valueOf(n)).sendMessage(player);
        // CoalOre(Block)が自然生成されたものか確認する。もしそうでないなら何も起きない。

        if (n < 2){
            // SuperCoal を与える
            player.sendMessage("You got a SuperCoal!");
            // TODO qualityはJOBのレベルによって変える
            ItemStack superCoal = new SuperCoal(block.getY(), 1.0).getSuperItem();
            player.getInventory().addItem(superCoal);
        }
    }
}