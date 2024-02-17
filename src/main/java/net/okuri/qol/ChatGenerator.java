package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChatGenerator {
    // ChatGenerator: playerにメッセージを送信するためのクラス
    // このクラスを使うときは、インスタンスをそれぞれ生成し、最後にsendMessage(player)を呼び出す。
    private enum ChatType {
        TITLE,
        INFO,
        ERROR,
        WARNING,
        SUCCESS,
        DEBUG
    }

    private final ArrayList<Component> components = new ArrayList<>();
    private final ArrayList<Component> debugComponents = new ArrayList<>();
    public static final Component prefix = Component.text("[").color(NamedTextColor.DARK_GRAY).decorate(TextDecoration.BOLD)
            .append(Component.text("QOL").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD))
            .append(Component.text("] ").color(NamedTextColor.DARK_GRAY).decorate(TextDecoration.BOLD));


    public void sendMessage(Player player){
        for (Component component : components) {
            player.sendMessage(prefix.append(component));
        }
        // opがある場合 debugを送信
        if (player.isOp()) {
            for (Component component : debugComponents) {
                player.sendMessage(prefix.append(component));
            }
        }
    }


    public ChatGenerator addInfo(String message){
        components.add(Component.text(message).color(NamedTextColor.GRAY));
        return this;
    }
    public ChatGenerator addError(String message){
        components.add(Component.text(message).color(NamedTextColor.RED));
        return this;
    }
    public ChatGenerator addWarning(String message){
        components.add(Component.text(message).color(NamedTextColor.YELLOW));
        return this;
    }
    public ChatGenerator addSuccess(String message){
        components.add(Component.text(message).color(NamedTextColor.GREEN));
        return this;
    }
    public ChatGenerator addDebug(String message){
        debugComponents.add(Component.text(message).color(NamedTextColor.WHITE));
        return this;
    }
    public ChatGenerator addTitle(String message){
        components.add(Component.text("-----<").color(NamedTextColor.GRAY)
                .append(Component.text(message).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD))
                .append(Component.text(">-----").color(NamedTextColor.GRAY)));
        return this;
    }

}
