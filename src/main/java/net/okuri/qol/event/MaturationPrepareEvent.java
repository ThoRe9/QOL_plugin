package net.okuri.qol.event;

import org.bukkit.block.Barrel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.block.Sign;

public class MaturationPrepareEvent extends Event {

    // Maturationの基本形(看板と樽)を持っているものを作成されたときに呼び出されるイベント
    // これが呼び出されたからと言って、Maturationが始まるわけではない。

    private static final HandlerList HANDLERS = new HandlerList();
    private final Sign sign;
    private final Barrel barrel;
    private final Player player;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public MaturationPrepareEvent(Sign sign, Barrel barrel, Player player) {
        this.sign = sign;
        this.barrel = barrel;
        this.player = player;
    }
    public Sign getSign() {
        return sign;
    }
    public Barrel getBarrel() {
        return barrel;
    }
    public Player getPlayer() {
        return player;
    }
    public String[] getLines() {
        return sign.getLines();
    }
}
