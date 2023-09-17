package net.okuri.qol;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class ProtectedBlock {

    // protectedBlockを設定するには、setProtectedBlockを呼び出す。
    public static NamespacedKey protectKey = new NamespacedKey("qol", "qol_protected");

    private static PersistentDataContainer getContainer(BlockState blockState){
        if (blockState instanceof PersistentDataHolder){
            return ((PersistentDataHolder) blockState).getPersistentDataContainer();
        }
        return null;
    }

    private static void applyLockedChest(Container chest){
        if (chest.getPersistentDataContainer().has(protectKey, PersistentDataType.BOOLEAN)) {
            if (chest.getPersistentDataContainer().get(protectKey, PersistentDataType.BOOLEAN)) {
                chest.setLock("qol");
            } else {
                chest.setLock("");
            }
        } else {
            chest.setLock("");
        }
        chest.update();
    }

    public static boolean isProtectedBlock(Block block) {
        BlockState blockState = block.getState();
        PersistentDataContainer container = getContainer(blockState);
        if (container == null) {
            return false;
        }
        if (container.has(protectKey, PersistentDataType.BOOLEAN)) {
            return container.get(protectKey, PersistentDataType.BOOLEAN);
        }
        return false;
    }
    public static boolean isProtectedBlock(Sign sign){
        if (sign.getPersistentDataContainer().has(protectKey, PersistentDataType.BOOLEAN)) {
            return sign.getPersistentDataContainer().get(protectKey, PersistentDataType.BOOLEAN);
        }
        return false;
    }

    public static void setProtectedBlock(Block block, boolean isProtected) {
        BlockState blockState = block.getState();
        setProtectedBlock(blockState, isProtected);
    }

    public static void setProtectedBlock(BlockState blockState, boolean isProtected){
        PersistentDataContainer container = getContainer(blockState);
        if (container == null) {
            Bukkit.getServer().getLogger().info("Container is null");
            return;
        }

        container.set(protectKey, PersistentDataType.BOOLEAN, isProtected);
        blockState.update();
        if (blockState.getType() == Material.CHEST || blockState.getType() == Material.TRAPPED_CHEST || blockState.getType() == Material.BARREL) {
            applyLockedChest((Container) blockState);
        }
    }

}
