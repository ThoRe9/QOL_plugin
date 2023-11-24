package net.okuri.qol.qolCraft.maturation;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.okuri.qol.ChatGenerator;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.ProtectedBlock;
import net.okuri.qol.event.MaturationEndEvent;
import net.okuri.qol.event.MaturationPrepareEvent;
import net.okuri.qol.producerInfo.ProducerInfo;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Barrel;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MaturationController implements Listener {
    private static final MaturationController listener = new MaturationController();
    ArrayList<MaturationRecipe> maturationRecipes = new ArrayList<>();

    private MaturationController() {
    }

    public static MaturationController getListener() {
        return listener;
    }


    public void addMaturationRecipe(MaturationRecipe maturationRecipe) {
        maturationRecipes.add(maturationRecipe);
    }

    // Maturation の開始処理
    @EventHandler
    public void prepareMaturation(MaturationPrepareEvent event) {
        // maturationRecipes に登録されているレシピをチェック
        // レシピが一致したら、logに出力
        //Bukkit.getServer().getLogger().info("PrepareItemCraftEvent");
        Barrel barrel = event.getBarrel();

        // レシピの判定
        for (MaturationRecipe maturationRecipe : this.maturationRecipes) {
            if (checkRecipe(maturationRecipe, barrel)) {
                // 一致した場合、maturationの処理開始。看板と樽を保護し、3行目に開始時刻を書き込み、4行目にRecipeNameを書き込む。
                Sign sign = event.getSign();
                sign.line(0, Component.text("[QOL]"));
                sign.line(1, Component.text("[Maturation]"));
                sign.line(2, Component.text(LocalDateTime.now().toString()));
                sign.line(3, Component.text(maturationRecipe.getRecipeName()));
                sign.update();
                sign.update(true);
                //Bukkit.getLogger().info("Maturation started!");
                //Bukkit.getLogger().info(sign.toString());

                ProtectedBlock.setProtectedBlock(sign, true);
                ProtectedBlock.setProtectedBlock(barrel, true);
                new ChatGenerator().addSuccess("Maturation started!").sendMessage(event.getPlayer());
                return;
            }
        }
        new ChatGenerator().addWarning("Wrong recipe!").sendMessage(event.getPlayer());
    }

    // Maturationの終了処理
    @EventHandler
    public void PlayerOpenSignEvent(PlayerOpenSignEvent event) {
        Player player = event.getPlayer();
        Sign sign = event.getSign();
        // Maturation開封の処理
        if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
            // Componentの配列をStringの配列にする
            List<Component> compLines = sign.getSide(Side.FRONT).lines();
            String[] lines = new String[compLines.size()];
            for (Component compLine : compLines) {
                lines[compLines.indexOf(compLine)] = LegacyComponentSerializer.legacySection().serialize(compLine);
            }

            if (Objects.equals(lines[0], "[QOL]") && Objects.equals(lines[1], "[Maturation]") && ProtectedBlock.isProtectedBlock(sign)) {
                // Maturationの処理
                Barrel barrel = (Barrel) sign.getBlock().getRelative(((Directional) sign.getBlockData()).getFacing().getOppositeFace()).getState();
                Maturable result = getRecipe(barrel).getResultClass();
                ArrayList<ItemStack> ingredients = new ArrayList<>();
                // 空欄のアイテムを除いてingredientsに追加する
                for (ItemStack itemStack : barrel.getInventory().getContents()) {
                    if (itemStack != null) {
                        if (itemStack.getType() != Material.AIR) {
                            ingredients.add(itemStack);
                        }
                    }
                }
                LocalDateTime start = LocalDateTime.parse(lines[2]);
                LocalDateTime end = LocalDateTime.now();
                double temp = barrel.getWorld().getTemperature(barrel.getX(), barrel.getY(), barrel.getZ());
                double humid = barrel.getWorld().getHumidity(barrel.getX(), barrel.getY(), barrel.getZ());

                MaturationEndEvent maturationEndEvent = new MaturationEndEvent(player, result, ingredients, barrel, sign, start, end, temp, humid);
                Bukkit.getServer().getPluginManager().callEvent(maturationEndEvent);

            }
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void finishMaturation(MaturationEndEvent event) {
        Maturable result = event.getResult();
        Sign sign = event.getSign();
        Barrel barrel = event.getBarrel();
        Player player = event.getPlayer();
        ArrayList<SuperItemStack> ingredients = new ArrayList<>();
        for (ItemStack itemStack : event.getIngredients()) {
            SuperItemStack superItemStack = new SuperItemStack(itemStack);
            if (superItemStack.getSuperItemType() == SuperItemType.DEFAULT) continue;
            ingredients.add(superItemStack);
        }

        result.setMaturationVariable(event.getIngredients(), event.getStart(), event.getEnd(), event.getTemp(), event.getHumid());
        // 樽の中身にresultを入れる
        Inventory barrelInventory = event.getBarrel().getSnapshotInventory();

        SuperItemStack resultStack = ((SuperItem) result).getSuperItem();
        ProducerInfo producerInfo = new ProducerInfo(player, 1.0, resultStack.getSuperItemData());
        for (SuperItemStack ingredient : ingredients) {
            player.getInventory().addItem(ingredient);
            if (ingredient.hasProducerInfo()) {
                producerInfo.addChild(ingredient.getProducerInfo());
            }
        }
        resultStack.setProducerInfo(producerInfo);
        barrelInventory.clear();
        barrelInventory.addItem(resultStack);
        sign.update();
        // 樽、看板の保護を解除
        ProtectedBlock.setProtectedBlock(sign, false);
        ProtectedBlock.setProtectedBlock(barrel, false);
        // 看板の3行目、4行目を削除
        sign.line(2, Component.empty());
        sign.line(3, Component.empty());
        sign.update();
        new ChatGenerator().addSuccess("Maturation finished!").sendMessage(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1.0f, 1.0f);
    }

    private MaturationRecipe getRecipe(Barrel barrel) {
        // barrelの中身を確認する
        // barrelの中身がrecipeの材料と一致するかどうかを確認する
        ItemStack[] ingredients = barrel.getInventory().getContents();
        for (MaturationRecipe recipe : maturationRecipes) {
            if (checkRecipe(recipe, barrel)) {
                return recipe;
            }
        }
        return null;
    }

    private boolean checkRecipe(MaturationRecipe recipe, Barrel barrel) {
        // barrelの中身を確認する
        // barrelの中身がrecipeの材料と一致するかどうかを確認する
        ArrayList<ItemStack> ingredients = new ArrayList<>();
        // barrelの中身がnullになるまでingredientsに追加する
        for (ItemStack itemStack : barrel.getInventory().getContents()) {
            if (itemStack != null) {
                ingredients.add(itemStack);
            }
        }

        if (ingredients.size() != recipe.getIngredientSize()) {
            return false;
        }
        // barrelの中身が全てSuperItemかどうかを確認する。もしそうなら、SuperItemDataの配列を作成する
        ArrayList<SuperItemData> superItemDatas = new ArrayList<>();
        for (ItemStack ingredient : ingredients) {
            if (ingredient == null) {
                return false;
            }
            if (ingredient.hasItemMeta() && PDCC.has(ingredient, PDCKey.TYPE)) {
                superItemDatas.add(new SuperItemData(SuperItemType.valueOf(PDCC.get(ingredient, PDCKey.TYPE))));
            } else {
                return false;
            }
        }
        // recipeの材料と一致するかどうかを確認する(順番も一致させる)
        ArrayList<SuperItemData> recipeIngredients = recipe.getIngredients();
        for (int i = 0; i < recipeIngredients.size(); i++) {
            if (!recipeIngredients.get(i).isSimilar(superItemDatas.get(i))) {
                return false;
            }
        }
        return true;
    }
}
