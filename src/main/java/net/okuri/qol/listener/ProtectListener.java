package net.okuri.qol.listener;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.ProtectedBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class ProtectListener implements Listener {

    // 保護ブロックの処理
    // 保護ブロックは、プレイヤーが何もできないようにされる
    @EventHandler
    public void PlayerOpenSignEvent(PlayerOpenSignEvent event) {
        Player player = event.getPlayer();
        Sign sign = event.getSign();
        ProtectedBlock protectedBlock = new ProtectedBlock();
        if (ProtectedBlock.isProtectedBlock(sign)){
            new ChatGenerator().addWarning("You cannot open it!").sendMessage(player);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void InventoryMoveItemEvent(InventoryMoveItemEvent event){
        if (event.getDestination().getHolder() instanceof HopperMinecart) {
            // HopperMinecraftの場合
            HopperMinecart hopperMinecart = (HopperMinecart) event.getDestination().getHolder();
            Block sourceInventoryBlock = event.getSource().getLocation().getBlock();
            Block destinationInventoryBlock = event.getDestination().getLocation().getBlock();
            ProtectedBlock protectedBlock = new ProtectedBlock();
            if (ProtectedBlock.isProtectedBlock(sourceInventoryBlock) || ProtectedBlock.isProtectedBlock(destinationInventoryBlock)){
                hopperMinecart.setVelocity(hopperMinecart.getVelocity().multiply(-1));
                event.setCancelled(true);
            }
        } else if (event.getDestination().getHolder() instanceof Hopper || event.getSource().getHolder() instanceof Hopper) {
            // Hopperの場合
            Block sourceInventoryBlock = event.getSource().getLocation().getBlock();
            Block destinationInventoryBlock = event.getDestination().getLocation().getBlock();
            ProtectedBlock protectedBlock = new ProtectedBlock();
            if (ProtectedBlock.isProtectedBlock(sourceInventoryBlock) || ProtectedBlock.isProtectedBlock(destinationInventoryBlock)){
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
        if (ProtectedBlock.isProtectedBlock(block)){
            player.sendMessage("You cannot break it!");
            event.setCancelled(true);
        }

    }
}
