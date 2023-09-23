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
    private static void applyLockedChest(Container chest){
        if (PDCC.has(chest, PDCKey.PROTECTED)) {
            if (PDCC.get(chest, PDCKey.PROTECTED)) {
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
        if (! (blockState instanceof TileState)) return false;
        TileState tileState = (TileState) blockState;
        if (PDCC.has(tileState, PDCKey.PROTECTED)) {
            return PDCC.get(tileState, PDCKey.PROTECTED);
        }
        return false;
    }
    public static boolean isProtectedBlock(Sign sign){
        if (PDCC.has(sign, PDCKey.PROTECTED)) {
            return PDCC.get(sign, PDCKey.PROTECTED);
        }
        return false;
    }

    public static void setProtectedBlock(Block block, boolean isProtected) {
        BlockState blockState = block.getState();
        if (! (blockState instanceof TileState)) throw new IllegalArgumentException("Block is not TileState");
        setProtectedBlock((TileState) blockState, isProtected);
    }

    public static void setProtectedBlock(TileState state, boolean isProtected){
        PDCC.set(state, PDCKey.PROTECTED, isProtected);
        if (state.getType() == Material.CHEST || state.getType() == Material.TRAPPED_CHEST || state.getType() == Material.BARREL) {
            applyLockedChest((Container) state);
        }
    }

}
