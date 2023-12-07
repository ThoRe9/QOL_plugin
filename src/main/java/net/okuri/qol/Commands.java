package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.okuri.qol.producerInfo.ProducerInfo;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class Commands implements CommandExecutor {
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
                    ProducerInfo producerInfo = new ProducerInfo(player, 1.0, item.getSuperItemData());
                    item.setProducerInfo(producerInfo);
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
                    ProducerInfo producerInfo = new ProducerInfo(player, 1.0, item.getSuperItemData());
                    player.getInventory().addItem(item);
                    new ChatGenerator().addInfo("Successfully gave the item!").sendMessage(player);
                    return true;
                }
            }
        } else if(command.getName().equalsIgnoreCase("alc")){
            if (sender instanceof Player) {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    if (!PDCC.has(player, PDCKey.ALCOHOL_LEVEL)) {
                        new ChatGenerator().addTitle("Your Alcohol Level").addInfo("You don't have alcohol level!").sendMessage(player);
                        return true;
                    }
                    new ChatGenerator().addTitle("Your Alcohol Level").addInfo("Alcohol Percentage: " + PDCC.get(player, PDCKey.ALCOHOL_LEVEL)).sendMessage(player);
                    return true;
                }
            }
        } else if (command.getName().equalsIgnoreCase(("producer"))) {
            if (sender instanceof Player) {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    // プレイヤーがメインハンドに持っているアイテムのproducerInfoを表示する
                    ItemStack item = player.getInventory().getItemInMainHand();
                    SuperItemStack superItemStack = new SuperItemStack(item);
                    if (superItemStack.getSuperItemType() != SuperItemType.DEFAULT) {
                        if (superItemStack.hasProducerInfo()) {
                            ProducerInfo producerInfo = superItemStack.getProducerInfo();
                            String[] stringInfo = producerInfo.getProducerInfo();
                            ChatGenerator chat = new ChatGenerator();
                            chat.addTitle("Producer Info");
                            for (String info : stringInfo) {
                                chat.addInfo(info);
                            }
                            chat.sendMessage(player);

                            return true;
                        } else {
                            new ChatGenerator().addWarning("This item doesn't have producer info!").sendMessage(player);
                            return true;
                        }
                    } else {
                        new ChatGenerator().addWarning("This item is not a super item!").sendMessage(player);
                        return true;

                    }
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