package net.okuri.qol.help;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.ChatGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HelpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            // 登録されているpage 一覧を表示する
            sendMessage(sender, "");
            sendMessage(sender, "登録されているページ一覧 (クリックで表示)");
            for (Page page : Help.getPages().values()) {
                Component c = Component.text(" ・").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD)
                        .append(Component.text(page.getTitle()).color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false)
                                .clickEvent(ClickEvent.runCommand("/qolhelp " + page.getTitle()))
                                .hoverEvent(HoverEvent.showText(Component.text("クリックで表示 : ").color(NamedTextColor.GRAY)
                                        .append(Component.text(page.getTitle()).color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false)))))
                        .append(Component.text(" : ").color(NamedTextColor.GRAY))
                        .append(Component.text(page.getDescription()).color(NamedTextColor.GRAY));
                sendMessage(sender, c);
            }

            return true;
        } else if (args.length == 1) {
            // page(0ページ目) の内容を表示する
            Page page = Help.getPage(args[0]);
            showHelp(sender, page, 0);
            return true;
        } else {
            // page(1ページ目以降) の内容を表示する
            Page page = Help.getPage(args[0]);
            int pageInt;
            try {
                pageInt = Integer.parseInt(args[1]) - 1;
            } catch (NumberFormatException e) {
                sendMessage(sender, "ページ数は数字で指定してください!");
                return true;
            }
            showHelp(sender, page, pageInt);
            return true;
        }
    }

    private void showHelp(CommandSender sender, Page page, int pageInt) {
        if (page == null) {
            sendMessage(sender, "そのページは存在しません!");
            return;
        } else if (pageInt >= page.getPages() || pageInt < 0) {
            sendMessage(sender, "そのページ数は存在しません!");
            return;
        }
        Component returnButton = Component.text("■").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)
                .clickEvent(ClickEvent.runCommand("/qolhelp"))
                .hoverEvent(HoverEvent.showText(Component.text("クリックで戻る : ").color(NamedTextColor.GRAY)));
        Component[] pageMove = getPageMoveComponent(page.getTitle(), pageInt, page.getPages());
        Component title = Component.empty()
                .append(returnButton)
                .append(Component.text("----------").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false)
                .append(pageMove[0])
                .append(Component.text(" " + page.getTitle() + " :").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false))
                .append(Component.text(" " + (pageInt + 1) + "/" + page.getPages() + " ").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, true)
                        .append(pageMove[1])
                        .append(Component.text("----------").color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false))));
        sendMessage(sender, title);
        ArrayList<Component> contents = page.getContents(pageInt);
        for (Component content : contents) {
            sendMessage(sender, content);
        }
    }

    private Component[] getPageMoveComponent(String title, int currentPage, int maxPage) {
        Component left = Component.text("◁").color(NamedTextColor.GRAY);
        Component right = Component.text("▷").color(NamedTextColor.GRAY);
        if (currentPage != 0) {
            left = Component.text("◁").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)
                    .clickEvent(ClickEvent.runCommand("/qolhelp " + title + " " + (currentPage)))
                    .hoverEvent(HoverEvent.showText(Component.text("クリックで前のページを表示 : ").color(NamedTextColor.GRAY)
                            .append(Component.text(currentPage).color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false))));
        }
        if (currentPage != maxPage - 1) {
            right = Component.text("▷").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)
                    .clickEvent(ClickEvent.runCommand("/qolhelp " + title + " " + (currentPage + 2)))
                    .hoverEvent(HoverEvent.showText(Component.text("クリックで次のページを表示 : ").color(NamedTextColor.GRAY)
                            .append(Component.text(currentPage + 2).color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, false))));
        }
        return new Component[]{left, right};
    }

    private void sendMessage(CommandSender sender, String message) {
        Component c = ChatGenerator.prefix.append(Component.text(message).color(NamedTextColor.GRAY));
        sender.sendMessage(c);
    }

    private void sendMessage(CommandSender sender, Component message) {
        Component c = ChatGenerator.prefix.append(message);
        sender.sendMessage(c);
    }

}
