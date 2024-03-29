package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class Commands implements CommandExecutor {

    private final Plugin plugin;

    public Commands(Plugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("getenv")) { //親コマンドの判定
            if (sender instanceof Player) {
                Player player = (Player) sender;
                // プレイヤーの座標を取得
                ChatGenerator chat = getEnv(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), player.getWorld());
                chat.sendMessage(player);
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("matsign")) {
            if (args.length == 4) {
                // /matsign <x> <y> <z> <days>
                // x,y,z座標に看板があるか確認する
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    int x = Integer.parseInt(args[0]);
                    int y = Integer.parseInt(args[1]);
                    int z = Integer.parseInt(args[2]);
                    if (player.getWorld().getBlockAt(x, y, z).getType().name().endsWith("SIGN")) {
                        // 看板の３行目に現在の日付から-daysした分の日付、時間を表示する
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime date = now.minusDays(Integer.parseInt(args[3]));
                        Sign sign = (Sign) player.getWorld().getBlockAt(x, y, z).getState();
                        sign.getSide(Side.FRONT).line(2, Component.text(date.toString()));
                        sign.update();
                        new ChatGenerator().addInfo("Successfully set the date!").sendMessage(player);

                        return true;
                    } else {
                        new ChatGenerator().addWarning("There is no sign!").sendMessage(player);
                        return true;
                    }
                }
            }
        } else if (command.getName().equalsIgnoreCase("givesuperitem")) {
            if (sender instanceof Player) {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    String typeStr = args[0];
                    //type が SuperItemType に存在するか確認する
                    try {
                        SuperItemType.valueOf(typeStr);
                    } catch (IllegalArgumentException e) {
                        new ChatGenerator().addWarning("Invalid type!").sendMessage(player);
                        return true;
                    }
                    SuperItemStack item = SuperItemType.getSuperItemClass(SuperItemType.valueOf(typeStr)).getDebugItem();
                    player.getInventory().addItem(item);
                    new ChatGenerator().addInfo("Successfully gave the item!").sendMessage(player);
                    return true;
                }
            }
        } else if (command.getName().equalsIgnoreCase("superwheat")) {
            if (sender instanceof Player) {
                if (args.length == 2) {
                    Player player = (Player) sender;
                    int type = Integer.parseInt(args[0]);
                    int temp = Integer.parseInt(args[1]);
                    SuperItemStack item = SuperItemType.getSuperItemClass(SuperItemType.WHEAT).getDebugItem(type, temp);
                    player.getInventory().addItem(item);
                    new ChatGenerator().addInfo("Successfully gave the item!").sendMessage(player);
                    return true;
                }
            }
        } else if(command.getName().equalsIgnoreCase("alc")){
            if (sender instanceof Player) {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    if (PDCC.has(player, PDCKey.HAS_ALC_BAR)) {
                        if (PDCC.get(player, PDCKey.HAS_ALC_BAR)) {
                            PDCC.set(player, PDCKey.HAS_ALC_BAR, false);
                            new ChatGenerator().addInfo("You turned off the alc bar!").sendMessage(player);
                        } else {
                            PDCC.set(player, PDCKey.HAS_ALC_BAR, true);
                            new ChatGenerator().addInfo("You turned on the alc bar!").sendMessage(player);
                            AlcBar alcBar = new AlcBar(player);
                            alcBar.runTaskTimer(this.plugin, 0, 20);
                        }
                    } else {
                        PDCC.set(player, PDCKey.HAS_ALC_BAR, true);
                        new ChatGenerator().addInfo("You turned on the alc bar!").sendMessage(player);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static ChatGenerator getEnv(int x, int y, int z, World world) {
        ChatGenerator chat = new ChatGenerator();
        Location location = new Location(world, x, y, z);
        chat.addTitle("Environment Getter");
        chat.addInfo("x: " + x);
        chat.addInfo("y: " + y);
        chat.addInfo("z: " + z);
        chat.addInfo("temperature: " + location.getBlock().getTemperature());
        chat.addInfo("humidity: " + location.getBlock().getHumidity());
        chat.addInfo("biome: " + location.getBlock().getBiome());
        chat.addInfo("Blocklight: " + location.getBlock().getLightFromBlocks());
        chat.addInfo("Skylight: " + location.getBlock().getLightFromSky());
        chat.addInfo("LightLevel: " + location.getBlock().getLightLevel());
        return chat;
    }
}