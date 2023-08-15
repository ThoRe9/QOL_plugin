package net.okuri.qol;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.okuri.qol.drinks.Maturation.Maturation;
import net.okuri.qol.superItems.SuperCoal;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Barrel;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Random;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class EventListener implements Listener {

    @EventHandler
    public void PlayerOpenSignEvent(PlayerOpenSignEvent event){
        Player player = event.getPlayer();
        Sign sign = event.getSign();
        // Maturation開封の処理
        if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)){
            // Maturationの処理
            // Componentの配列をStringの配列にする
            List<Component> compLines = sign.getSide(Side.FRONT).lines();
            String[] lines = new String[compLines.size()];
            for (Component compLine : compLines) {
                lines[compLines.indexOf(compLine)] = LegacyComponentSerializer.legacySection().serialize(compLine);
            }
            if (Objects.equals(lines[0], "[QOL]") && Objects.equals(lines[1], "[Maturation]")){
                Block barrel = sign.getBlock().getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace());
                Barrel barrelData = (Barrel) barrel.getState();
                // linesの3行目にある日付,時間を取得
                LocalDateTime start = LocalDateTime.parse(lines[2]);
                Maturation maturation = new Maturation(start, barrelData);
                ItemStack result = maturation.getResult();
                Inventory inv = barrelData.getSnapshotInventory();
                inv.clear();
                inv.addItem(result);
                // 樽、看板のprotectedを解除
                ProtectedBlock protectedBlock = new ProtectedBlock();
                protectedBlock.setProtectedBlock(barrelData, false);
                protectedBlock.setProtectedBlock(sign, false);
                barrelData.update();


                player.sendMessage("You opened the maturation!");

            }
            event.setCancelled(true);
        }

        // 保護ブロックの処理
        // 保護ブロックは、プレイヤーが何もできないようにされる
        ProtectedBlock protectedBlock = new ProtectedBlock();
        if (protectedBlock.isProtectedBlock(sign)){
            player.sendMessage("You cannot open it!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    // 材料ポーションなど使用できないアイテムを使用したときの処理
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey("qol", "qol_consumable");

        if (meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN)){
            if (!meta.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN)){
                player.sendMessage("You cannot use it!");
                event.setCancelled(true);
            }
        }

    }


    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockType = block.getType();
        // 保護ブロックの処理
        // 保護ブロックは、プレイヤーが何もできないようにされる
        ProtectedBlock protectedBlock = new ProtectedBlock();
        if (protectedBlock.isProtectedBlock(block)){
            player.sendMessage("You cannot break it!");
            event.setCancelled(true);
        }

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

    // おまけ
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Welcome to the server!");
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
            player.sendMessage("n: " + n);

            if (n < 10) {
                // SuperWheat を与える
                player.sendMessage("You got a SuperWheat!");

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
        player.sendMessage(String.valueOf(block.getState().isCollidable()));
        // CoalOre(Block)が自然生成されたものか確認する。もしそうでないなら何も起きない。

        if (n < 10){
            // SuperCoal を与える
            player.sendMessage("You got a SuperCoal!");
            // TODO qualityはJOBのレベルによって変える
            ItemStack superCoal = new SuperCoal(block.getY(), 1.0).getSuperItem();
            player.getInventory().addItem(superCoal);
        }
    }
}
