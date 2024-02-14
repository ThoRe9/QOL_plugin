package net.okuri.qol.qolCraft.resource;

import net.okuri.qol.ChatGenerator;
import net.okuri.qol.alcohol.resources.LiquorResource;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.joml.Random;

import java.util.ArrayList;

public class ResourceController implements Listener {
    // Resourceを管理を司るシングルトンインスタンス。

    private static ResourceController listener = new ResourceController();
    private final ArrayList<LiquorResource> resources = new ArrayList<>();

    private ResourceController() {
    }

    public static ResourceController getListener() {
        return listener;
    }

    public void addResource(LiquorResource r) {
        this.resources.add(r);
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();
        Material blockType = block.getType();

        // SilkTouchのエンチャントを持つツールをプレイヤーが持っていた場合はここで終了
        if (event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.SILK_TOUCH)) {
            return;
        }
        //手に何も持っていないならここで終了
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }
        SuperItemStack item = new SuperItemStack(player.getInventory().getItemInMainHand());
        Random rand = new Random();
        int n = rand.nextInt(100);
        //壊したツールがResourceGetterの場合
        if (new SuperItemData(SuperItemType.RESOURCE_GETTER).isSimilar(item.getSuperItemData())) {
            n = 100;
            ItemStack i = player.getInventory().getItemInMainHand();
            Damageable meta = (Damageable) i.getItemMeta();
            meta.setDamage(1000);
        }

        // 壊したツールがFarmerのツール/Minerのツールでない場合はここで終了
        if (!item.isFarmerTool() && !item.isMinerTool()) {
            return;
        }

        // SuperResourceの判定
        for (LiquorResource r : this.resources) {
            if (blockType == r.getBlockMaterial()) {
                if (Tag.CROPS.isTagged(blockType)) {
                    if (((Ageable) block.getState().getBlockData()).getAge() < 7) {
                        continue;
                    }
                    if (!item.isFarmerTool()) {
                        continue;
                    }
                }
                if (Tag.LEAVES.isTagged(blockType)) {
                    if (!item.isFarmerTool()) {
                        continue;
                    }
                }
                if (Tag.MINEABLE_PICKAXE.isTagged(blockType)) {
                    if (!item.isMinerTool()) {
                        continue;
                    }
                }

                new ChatGenerator().addDebug(String.valueOf(n)).sendMessage(player);
                if (n < r.getProbability() * 100) {
                    new ChatGenerator().addSuccess("You got " + r.getSuperItemType().name() + " !!").sendMessage(player);

                    double temp = player.getLocation().getBlock().getTemperature();
                    double humid = player.getLocation().getBlock().getHumidity();
                    Biome biome = player.getLocation().getBlock().getBiome();
                    int biomeID = biome.ordinal();
                    // TODO quality の計算
                    r.setResVariables(block.getX(), block.getY(), block.getZ(), temp, humid, biomeID, player);
                    player.getInventory().addItem(r.getSuperItem());
                }
            }
        }
    }


}
