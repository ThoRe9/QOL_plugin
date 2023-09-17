package net.okuri.qol.listener;

import net.okuri.qol.ChatGenerator;
import net.okuri.qol.event.MaturationPrepareEvent;
import net.okuri.qol.event.QOLSignCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class QOLSignListener implements Listener {
    private final JavaPlugin plugin;
    // 看板に[~~~]と書いている場合、特別な処理を行う。

    public QOLSignListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    // 看板の内容を変更したときのイベント
    @EventHandler
    public void onSign(SignChangeEvent event){
        Player player = event.getPlayer();
        // 看板の内容を取得する
        String[] lines = event.getLines();
        // eventの座標にある看板を取得する
        Sign sign =(Sign) event.getBlock().getState();


        if (lines[0].equals("[QOL]")){
            // QOLSignCreateEventをSyncで呼び出す
            QOLSignCreateEvent qolSignCreateEvent = new QOLSignCreateEvent(sign, lines, player);
            Bukkit.getPluginManager().callEvent(qolSignCreateEvent);
            event.line(0, sign.line(0));
            event.line(1, sign.line(1));
            event.line(2, sign.line(2));
            event.line(3, sign.line(3));

        }
    }

    // QOLSign の内容によって分岐
    @EventHandler
    public void qolSign(QOLSignCreateEvent event){
        Player player = event.getPlayer();
        String[] lines = event.getLines();
        Sign sign = event.getSign();

        if (lines[1].equals("[Maturation]")){
            // この看板が向いている方向と逆のブロックを取得する
            Barrel barrel = (Barrel)sign.getBlock().getRelative(((Directional) sign.getBlock().getBlockData()).getFacing().getOppositeFace()).getState();
            // そのブロックが樽であるかどうかを確認する
            if (barrel.getType() == Material.BARREL){
                MaturationPrepareEvent maturationPrepareEvent = new MaturationPrepareEvent(sign, barrel, player);
                maturationPrepareEvent.callEvent();
            } else{
                new ChatGenerator().addWarning("This block is not barrel!").sendMessage(player);
                event.setCancelled(true);
            }
        }
        // 今後, 看板を用いる処理を追加する場合は以下に追加する
    }
}
