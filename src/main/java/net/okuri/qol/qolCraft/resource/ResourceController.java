package net.okuri.qol.qolCraft.resource;

import net.okuri.qol.ChatGenerator;
import net.okuri.qol.producerInfo.ProducerInfo;
import net.okuri.qol.superItems.factory.resources.SuperResource;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.joml.Random;

import java.util.ArrayList;

public class ResourceController implements Listener {
    // Resourceを管理を司るシングルトンインスタンス。

    private static ResourceController listener = new ResourceController();
    private final ArrayList<SuperResource> resources = new ArrayList<>();

    private ResourceController() {
    }

    public static ResourceController getListener() {
        return listener;
    }

    public void addResource(SuperResource r) {
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

        // SuperResourceの判定
        for (SuperResource r : this.resources) {
            if (blockType == r.getBlockMaterial()) {
                if (Tag.CROPS.isTagged(blockType)) {
                    if (((Ageable) block.getState().getBlockData()).getAge() < 7) {
                        continue;
                    }
                }
                Random rand = new Random();
                int n = rand.nextInt(100);
                new ChatGenerator().addDebug(String.valueOf(n)).sendMessage(player);
                if (n < r.getProbability()) {
                    new ChatGenerator().addSuccess("You got " + r.getSuperItemType().name() + " !!").sendMessage(player);

                    double temp = player.getLocation().getBlock().getTemperature();
                    double humid = player.getLocation().getBlock().getHumidity();
                    Biome biome = player.getLocation().getBlock().getBiome();
                    int biomeID = biome.ordinal();
                    // TODO quality の計算
                    r.setResVariables(block.getX(), block.getY(), block.getZ(), temp, humid, biomeID, 1.0, player);
                    SuperResourceStack resultItem = r.getSuperItem();
                    ProducerInfo producerInfo = new ProducerInfo(player, 1.0, resultItem.getSuperItemData());
                    resultItem.setProducerInfo(producerInfo);
                    player.getInventory().addItem(resultItem);
                }
            }
        }
    }


}
