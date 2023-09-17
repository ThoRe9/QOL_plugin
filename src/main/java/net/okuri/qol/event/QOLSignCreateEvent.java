package net.okuri.qol.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QOLSignCreateEvent extends Event implements Cancellable {

    // 1行目に"[QOL]"と書かれた看板を作成したときに呼び出されるイベント

    private static final HandlerList HANDLERS = new HandlerList();
    private final Sign sign;
    private final String[] lines;
    private final Player player;
    private boolean isCancelled;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public QOLSignCreateEvent(Sign sign, String[] lines, Player player) {
        this.sign = sign;
        this.lines = lines;
        this.player = player;
    }

    public Sign getSign() {
        return sign;
    }
    public String[] getLines() {
        return lines;
    }
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
