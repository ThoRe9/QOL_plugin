package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.event.SuperCraftEvent;
import net.okuri.qol.producerInfo.ProducerInfo;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class SuperCraftController implements Listener {
    private static final SuperCraftController listener = new SuperCraftController();

    private final ArrayList<SuperCraftRecipe> superCraftRecipes = new ArrayList<>();
    private final ArrayList<ShapelessSuperCraftRecipe> shapelessSuperCraftRecipes = new ArrayList<>();
    private final ArrayList<DistributionCraftRecipe> distributionCraftRecipes = new ArrayList<>();
    private boolean superCraftFlag = false;
    private boolean shapelessCraftFlag = false;
    private boolean distributionFlag = false;
    private SuperItemStack distributableItem;
    private SuperRecipe recipe;

    private SuperCraftController() {
    }

    public static SuperCraftController getListener() {
        return listener;
    }

    public void addSuperCraftRecipe(SuperCraftRecipe superCraftRecipe) {
        superCraftRecipes.add(superCraftRecipe);
    }
    public void addShapelessSuperCraftRecipe(ShapelessSuperCraftRecipe shapelessSuperCraftRecipe) {
        shapelessSuperCraftRecipes.add(shapelessSuperCraftRecipe);
    }
    public void addDistributionCraftRecipe(DistributionCraftRecipe distributionCraftRecipe) {
        distributionCraftRecipes.add(distributionCraftRecipe);
    }

    @EventHandler
    public void PrepareItemCraftEvent(PrepareItemCraftEvent event) {
        //Bukkit.getLogger().info("Prepare called");
        // superCraftRecipes に登録されているレシピをチェック
        // レシピが一致したら、logに出力
        //Bukkit.getServer().getLogger().info("PrepareItemCraftEvent");

        //　初期化処理
        this.superCraftFlag = false;
        this.shapelessCraftFlag = false;
        this.distributionFlag = false;
        this.distributableItem = null;
        this.recipe = null;

        Player player = (Player) event.getView().getPlayer();
        CraftingInventory inventory = event.getInventory();
        ItemStack[] matrix = event.getInventory().getMatrix();
        SuperItemStack[] superMatrix = new SuperItemStack[matrix.length];
        // 深いコピーを行う
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) {
                superMatrix[i] = null;
                continue;
            }
            if (matrix[i].getType() == Material.AIR) {
                superMatrix[i] = null;
                continue;
            }
            if (matrix[i].getItemMeta() == null) {
                superMatrix[i] = null;
                continue;
            } else {
                superMatrix[i] = new SuperItemStack(matrix[i]);
            }
        }

        // レシピの判定
        for (SuperCraftRecipe superCraftRecipe : superCraftRecipes) {
            if (superCraftRecipe.checkSuperRecipe(superMatrix)) {
                Bukkit.getLogger().info("SuperCraftRecipe matched!");
                SuperCraftable result = superCraftRecipe.getResultClass();
                SuperItemStack resultItem = result.getSuperItem();
                resultItem.setProducerInfo(getProducerInfo(superMatrix, resultItem.getSuperItemData(), player));
                inventory.setResult(resultItem);
                this.superCraftFlag = true;
                this.recipe = superCraftRecipe;
                return;
            }
        }
        // 不定形レシピの判定
        for (ShapelessSuperCraftRecipe shapelessSuperCraftRecipe : shapelessSuperCraftRecipes) {
            if (shapelessSuperCraftRecipe.checkSuperRecipe(superMatrix)) {
                Bukkit.getLogger().info("ShapelessSuperCraftRecipe matched!");
                SuperCraftable result = shapelessSuperCraftRecipe.getResultClass();
                SuperItemStack resultItem = result.getSuperItem();
                resultItem.setProducerInfo(getProducerInfo(superMatrix, resultItem.getSuperItemData(), player));
                inventory.setResult(resultItem);
                inventory.setResult(resultItem);
                this.shapelessCraftFlag = true;
                this.recipe = shapelessSuperCraftRecipe;
                return;
            }
        }
        // Distributionの判定
        for (DistributionCraftRecipe d : distributionCraftRecipes){
            if (d.checkSuperRecipe(superMatrix)) {
                Bukkit.getLogger().info("DistributionCraftRecipe matched!");
                d.process();
                Distributable distribution = d.getDistribution();
                DistributionReceiver receiver = d.getReceiver();

                SuperItemStack receiverItem = receiver.getSuperItem();

                inventory.setResult(receiverItem);
                this.distributionFlag = true;
                this.distributableItem = distribution.getSuperItem();
                ProducerInfo info = getProducerInfo(superMatrix, receiverItem.getSuperItemData(), player);
                receiverItem.setProducerInfo(info);
                distributableItem.setProducerInfo(info.getChild(distributableItem.getSuperItemData()));

                this.recipe = d;

                return;
            }
        }
    }

    private ProducerInfo getProducerInfo(SuperItemStack[] matrix, SuperItemData data, Player player) {
        ProducerInfo result = new ProducerInfo(player, 0.0, data);
        for (SuperItemStack item : matrix) {
            if (item == null) {
                continue;
            }
            if (item.getItemMeta() == null) {
                continue;
            }
            if (PDCC.has(item.getItemMeta(), PDCKey.PRODUCER_INFO)) {
                ProducerInfo info = PDCC.getProducerInfo(item.getItemMeta());
                result.addChild(info);
            }
        }
        return result;
    }

    @EventHandler
    private void CraftItemEvent(SuperCraftEvent event){
        //Bukkit.getLogger().info("Craft called");
        // distributionの場合は先に容量を減らした瓶をプレイヤーに渡す

        if (this.distributionFlag){
            //Bukkit.getLogger().info("Flag is true");

            if (this.distributableItem != null){
                Player player = (Player) event.getWhoClicked();
                player.getInventory().addItem(this.distributableItem);
                this.distributableItem = null;
            }
        }

        // もしflagが立っているなら、グリッド内のアイテムをクラフトした分だけ減らす。
        if (this.superCraftFlag || this.shapelessCraftFlag || this.distributionFlag){
            CraftingInventory inv = event.getInventory();
            for(ItemStack item : inv.getMatrix()){
                if (item == null) {continue;}
                if (item.getType() == Material.AIR) {continue;}
                item.setAmount(item.getAmount()-1);
            }
            this.superCraftFlag = false;
            this.shapelessCraftFlag = false;
            this.distributionFlag = false;
        }
    }
    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event){
        // TODO バケツなどの返却処理と、shift時の処理
        if (event.getInventory() instanceof CraftingInventory){
            if (event.getSlotType() == InventoryType.SlotType.RESULT){
                ItemStack result = event.getCurrentItem();
                if (result != null && result.getItemMeta() != null){
                    if (PDCC.has(result.getItemMeta(), PDCKey.TYPE)){
                        if (event.getCursor().getType() != Material.AIR){
                            event.setCancelled(true);
                            return;
                        }
                        //Bukkit.getLogger().info(event.getCurrentItem().toString());
                        //Bukkit.getLogger().info(event.getCursor().toString());
                        SuperCraftEvent craftEvent = new SuperCraftEvent(this.recipe, event.getView(), InventoryType.SlotType.RESULT, event.getRawSlot(), event.getClick(), event.getAction());
                        Bukkit.getPluginManager().callEvent(craftEvent);

                        event.setCursor(event.getCurrentItem());
                        event.setCancelled(true);
                        CraftingInventory inv =(CraftingInventory) event.getInventory();
                        inv.setResult(null);
                        InventoryView view = event.getView();
                        PrepareItemCraftEvent pEvent = new PrepareItemCraftEvent(inv, view, false);
                        Bukkit.getPluginManager().callEvent(pEvent);
                    }
                }
            }
        }
    }

}

