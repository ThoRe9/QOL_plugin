package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DistributionCraftRecipe implements SuperRecipe {
    // 分配レシピの登録方法
    // 1. DistributionCraftRecipeをインスタンス化(Stringのidを引数に)
    // 2. setDistributionを用いて、Distributableをセット
    // 3. setReceiverを用いて、DistributionReceiverをセット
    // 4. setBottleを用いて、bottleのMaterialをセット
    // 5. addOtherIngredientを用いて、bottle以外の材料をセット(任意)
    // 6. addRecipeを用いて、レシピを登録

    private final String id;
    private final ArrayList<SuperItemData> otherIngredients = new ArrayList<>();
    private SuperItemData bigBottleData;
    private double smallBottleAmount;
    private int smallBottleCount;
    private SuperItemData bottle = new SuperItemData(Material.GLASS_BOTTLE);
    private Distributable distribution;
    private DistributionReceiver receiver;
    private SuperItemStack[] matrix = new SuperItemStack[9];
    private final ArrayList<SuperItemStack> result = new ArrayList<>();
    private SuperItemStack returnBottle;
    public DistributionCraftRecipe(String id){
        this.id = id;
    }
    @Override
    public boolean checkSuperRecipe(SuperItemStack[] matrix) {
        // まず、matrixの中にbigBottleTypeを持つものが在るかチェック
        // あれば、その個数を数える。2つ以上あった時点でfalseを返す。
        // 同時にbigBottleType,bottle以外のアイテムがあればfalseを返す。
        // bottleの数も数える
        // 上記のどれでもない場合、OtherIngredientの物があるか確認。もしあったら、それを返す。
        ArrayList<SuperItemData> lastOtherIngredients = new ArrayList<>();
        ArrayList<ItemStack> superBottles = new ArrayList<>();
        SuperItemStack[] otherIngredientStacks = new SuperItemStack[this.otherIngredients.size()];
        lastOtherIngredients.addAll(otherIngredients);
        int smallBottleCount = 0;
        int bigBottleCount = 0;
        SuperItemStack distributionItem = null;

        for (SuperItemStack itemStack : matrix) {
            if (itemStack == null) {
                //Bukkit.getLogger().info("null");
                continue;
            }
            ItemMeta meta = itemStack.getItemMeta();

            // bigBottleの処理
            if (itemStack.isSimilar(bigBottleData)) {
                //Bukkit.getLogger().info("bigBottle");
                bigBottleCount++;
                distributionItem = itemStack;
                if (bigBottleCount >= 2) {
                    //Bukkit.getLogger().info("bigBottleCount >= 2");
                    return false;
                }
                continue;
            }

            // smallBottleの処理
            if (itemStack.isSimilar(this.bottle)) {
                smallBottleCount++;
                superBottles.add(itemStack);
                continue;
            }
            // otherIngredientの処理
            if (lastOtherIngredients.contains(itemStack.getSuperItemType())) {
                otherIngredientStacks[lastOtherIngredients.indexOf(itemStack.getSuperItemType())] = itemStack;
                lastOtherIngredients.remove(itemStack.getSuperItemType());
                //Bukkit.getLogger().info("otherIngredient");
            } else{
                //Bukkit.getLogger().info("anything not found");
                return false;
            }
        }
        if (bigBottleCount == 0 || smallBottleCount == 0 || !lastOtherIngredients.isEmpty()){
            //Bukkit.getLogger().info("anything wrong");
            //Bukkit.getLogger().info("bigBottleCount: " + bigBottleCount);
            //Bukkit.getLogger().info("smallBottleCount: " + smallBottleCount);
            //Bukkit.getLogger().info("lastOtherIngredients: " + lastOtherIngredients.isEmpty());
            return false;
        }
        this.smallBottleCount = smallBottleCount;
        // ここまでで、matrixの中にはbigBottleが1つ、bottleが1つ以上あることが確定
        // あとは、isDistributionがtrueを返すかどうかを確認する
        this.matrix = new SuperItemStack[9];
        this.matrix[0] = distributionItem;
        // matrixの後の枠に otherIngredientStacksとsmallBottleを入れる
        int index = 1;
        for (SuperItemStack itemStack : otherIngredientStacks) {
            if (itemStack == null) continue;
            this.matrix[index] = itemStack;
            index++;
        }
        for (ItemStack itemStack : superBottles) {
            this.matrix[index] = new SuperItemStack(itemStack);
            index++;
        }

        distribution.setMatrix(this.matrix,id);
        //Bukkit.getLogger().info("isDistributable");
        return distribution.isDistributable(smallBottleAmount, smallBottleCount);
        //Bukkit.getLogger().info("is not Distributable");
    }
    public Distributable getDistribution(){
        return distribution;
    }
    public DistributionReceiver getReceiver(){
        return receiver;
    }
    public int getSmallBottleCount(){
        return smallBottleCount;
    }
    public double getSmallBottleAmount(){
        return smallBottleAmount;
    }
    public void setReciver(DistributionReceiver receiver){
        this.receiver = receiver;
        this.smallBottleAmount = receiver.getAmount();
    }
    public void process(){
        distribution.setMatrix(matrix, "distribution");
        receiver.setMatrix(matrix, "distribution_receiver");
        distribution.distribute(smallBottleAmount,smallBottleCount);
        receiver.receive(smallBottleCount);
        this.returnBottle = this.distribution.getSuperItem();
    }
    public void setDistribution(Distributable distribution){
        this.distribution = distribution;
        this.bigBottleData = ((SuperItem) distribution).getSuperItemData();
    }
    public void setBottle(Material bottle){
        this.bottle = new SuperItemData(bottle);
    }

    public void setBottle(SuperItemType bottle) {
        this.bottle = new SuperItemData(bottle);
    }
    public void addOtherIngredient(Material material){
        this.addOtherIngredient(new SuperItemData(material));
    }

    public void addOtherIngredient(SuperItemType type) {
        this.addOtherIngredient(new SuperItemData(type));
    }

    public void addOtherIngredient(SuperItemData data) {
        if (otherIngredients.size() >= 7) {
            throw new IllegalArgumentException("すでにotherIngredientsの数が7を超えています。");
        }
        otherIngredients.add(data);
    }
    @Override
    public String getId(){
        return id;
    }
    @Override
    public @NotNull SuperItemStack getResult() {
        return ((SuperItem) receiver).getSuperItem();
    }

    @Override
    public ArrayList<SuperItemStack> getReturnItems() {
        ArrayList<SuperItemStack> returnItems = new ArrayList<>();
        returnItems.add(returnBottle);
        returnItems.addAll(result);
        return returnItems;
    }

    public void addReturnItem(SuperItemStack itemStack) {
        result.add(itemStack);
    }

}
