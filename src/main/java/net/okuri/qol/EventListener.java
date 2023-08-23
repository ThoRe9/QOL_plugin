package net.okuri.qol;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.okuri.qol.drinks.maturation.Maturation;
import net.okuri.qol.foods.Food;
import net.okuri.qol.superItems.SuperCoal;
import net.okuri.qol.superItems.SuperWheat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

                new ChatGenerator().addInfo("Barrel is no longer protected.").addSuccess("You opened barrel!").sendMessage(player);
                player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1.0f, 1.0f);
            }
            event.setCancelled(true);
        }

        // 保護ブロックの処理
        // 保護ブロックは、プレイヤーが何もできないようにされる
        ProtectedBlock protectedBlock = new ProtectedBlock();
        if (protectedBlock.isProtectedBlock(sign)){
            new ChatGenerator().addWarning("You cannot open it!").sendMessage(player);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void InventoryMoveItemEvent(InventoryMoveItemEvent event){
        // HopperMinecraftの場合
        if (event.getDestination().getHolder() instanceof HopperMinecart) {
            HopperMinecart hopperMinecart = (HopperMinecart) event.getDestination().getHolder();
            Block sourceInventoryBlock = event.getSource().getLocation().getBlock();
            Block destinationInventoryBlock = event.getDestination().getLocation().getBlock();
            // 保護ブロックの処理
            // 保護ブロックは、プレイヤーが何もできないようにされる
            ProtectedBlock protectedBlock = new ProtectedBlock();
            if (protectedBlock.isProtectedBlock(sourceInventoryBlock) || protectedBlock.isProtectedBlock(destinationInventoryBlock)){
                hopperMinecart.setVelocity(hopperMinecart.getVelocity().multiply(-1));
                event.setCancelled(true);
            }
        // Hopperの場合
        } else if (event.getDestination().getHolder() instanceof Hopper || event.getSource().getHolder() instanceof Hopper) {
            Block sourceInventoryBlock = event.getSource().getLocation().getBlock();
            Block destinationInventoryBlock = event.getDestination().getLocation().getBlock();
            // 保護ブロックの処理
            // 保護ブロックは、プレイヤーが何もできないようにされる
            ProtectedBlock protectedBlock = new ProtectedBlock();
            if (protectedBlock.isProtectedBlock(sourceInventoryBlock) || protectedBlock.isProtectedBlock(destinationInventoryBlock)){
                event.setCancelled(true);
            }
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
                new ChatGenerator().addWarning("You cannot use it!").sendMessage(player);
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
        if (meta.getPersistentDataContainer().has(Food.eatableKey, PersistentDataType.BOOLEAN)){
            if (meta.getPersistentDataContainer().get(Food.eatableKey, PersistentDataType.BOOLEAN)){
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
            new ChatGenerator().addDebug(String.valueOf(n)).sendMessage(player);

            if (n < 10) {
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
