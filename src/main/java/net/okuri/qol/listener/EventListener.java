package net.okuri.qol.listener;

import net.okuri.qol.Alcohol;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener {
    private JavaPlugin plugin;
    public EventListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // 死んだときにalcLvを0にする
    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event){
        Player player = event.getEntity();
        PDCC.remove(player, PDCKey.ALCOHOL_LEVEL);
    }


    // おまけ
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Welcome to the server!");
    }
}
