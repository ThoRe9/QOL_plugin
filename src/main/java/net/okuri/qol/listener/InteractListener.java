package net.okuri.qol.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.Commands;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.alcohol.YeastGotcha;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;

public class InteractListener implements Listener {
    // プレイヤーが右クリックしたとき
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {

        // interactしたプレイヤーのハンドがメインハンドか確認
        if (event.getHand() != org.bukkit.inventory.EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }
        SuperItemStack item = new SuperItemStack(player.getInventory().getItemInMainHand());
        // itemがnullなら終了
        if (item.getType() == Material.AIR) {
            return;
        }
        ItemMeta meta = item.getItemMeta();

        //以下ツールの処理
        // metaのPersistentDataContainerにtypeKeyがあるか確認
        if (!PDCC.has(meta, PDCKey.TYPE)) return;
        SuperItemType type = SuperItemType.valueOf(PDCC.get(meta, PDCKey.TYPE));
        switch (type) {
            case ENV_TOOL:
                // ブロックを右クリックしたとき
                if (event.getClickedBlock() != null) {
                    // ブロックの座標を取得
                    int x = event.getClickedBlock().getX();
                    int y = event.getClickedBlock().getY();
                    int z = event.getClickedBlock().getZ();
                    World world = event.getClickedBlock().getWorld();
                    ChatGenerator chat = Commands.getEnv(x, y, z, world);
                    chat.sendMessage(player);
                    event.setCancelled(true);
                }
                break;
            case MATURATION_TOOL:
                if (event.getClickedBlock() != null) {
                    // ブロックの座標を取得
                    int x = event.getClickedBlock().getX();
                    int y = event.getClickedBlock().getY();
                    int z = event.getClickedBlock().getZ();
                    World world = event.getClickedBlock().getWorld();
                    if (world.getBlockAt(x, y, z).getType().name().endsWith("SIGN")) {
                        // 看板の３行目に現在の日付から-daysした分の日付、時間を表示する
                        Sign sign = (Sign) player.getWorld().getBlockAt(x, y, z).getState();

                        String line0 = PlainTextComponentSerializer.plainText().serialize(sign.getSide(Side.FRONT).line(0));
                        String line1 = PlainTextComponentSerializer.plainText().serialize(sign.getSide(Side.FRONT).line(1));
                        String line2 = PlainTextComponentSerializer.plainText().serialize(sign.getSide(Side.FRONT).line(2));
                        if (line0.equals("[QOL]") && line1.equals("[Maturation]")) {
                            int m = PDCC.get(item.getItemMeta(), PDCKey.MATURATION_TOOL_AMOUNT);
                            LocalDateTime start = LocalDateTime.parse(line2);
                            LocalDateTime date = start.minusDays(m);
                            sign.getSide(Side.FRONT).line(2, Component.text(date.toString()));
                            sign.update();
                            new ChatGenerator().addInfo("Successfully set the date!").sendMessage(player);
                            player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
                        } else {
                            new ChatGenerator().addWarning("There is no maturation").sendMessage(player);
                        }
                    } else {
                        new ChatGenerator().addWarning("There is no sign!").sendMessage(player);
                    }
                    event.setCancelled(true);
                }
                break;
            case YEAST_GOTCHA:
                YeastGotcha.get(player);
                PlayerInventory inv = player.getInventory();
                inv.getItemInMainHand().setAmount(item.getAmount() - 1);
                break;
            default:
                break;
        }
    }
}
