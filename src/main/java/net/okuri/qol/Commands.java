package net.okuri.qol;

import net.kyori.adventure.text.Component;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("getenv")) { //親コマンドの判定
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ChatGenerator chat = new ChatGenerator();
                chat.addInfo("temperature: " + player.getLocation().getBlock().getTemperature());
                chat.addInfo("humidity: " + player.getLocation().getBlock().getHumidity());
                chat.addInfo("biome: " + player.getLocation().getBlock().getBiome());
                chat.addInfo("Blocklight: " + player.getLocation().getBlock().getLightFromBlocks());
                chat.addInfo("Skylight: " + player.getLocation().getBlock().getLightFromSky());
                chat.addInfo("LightLevel: " + player.getLocation().getBlock().getLightLevel());
                chat.sendMessage(player);
                return true;
            }
        } else if(command.getName().equalsIgnoreCase("matsign")) {
            if (args.length == 4){
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
            } else if(command.getName().equalsIgnoreCase("givesuperitem")){
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String typeStr = args[0];
                    //type が SuperItemType に存在するか確認する
                    try {
                        SuperItemType.valueOf(typeStr);
                    } catch (IllegalArgumentException e) {
                        new ChatGenerator().addWarning("Invalid type!").sendMessage(player);
                        return true;
                    }
                    //type が SuperItemType に存在するなら、そのtypeのSuperItemを作成してプレイヤーに渡す
                    SuperItemType type = SuperItemType.valueOf(typeStr);

                    return true;
                }
            }
        }

        return false;
    }
}