package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    private Class<? extends Distributable> distribution;
    private Class<? extends DistributionReceiver> receiver;
    private Distributable distributionInstance;
    private DistributionReceiver receiverInstance;
    private SuperItemStack[] matrix = new SuperItemStack[9];
    private final ArrayList<SuperItemStack> result = new ArrayList<>();
    private SuperItemStack returnBottle;
    private boolean isGetRawMatrix = false;
    private SuperItemStack[] rawMatrix;
    public DistributionCraftRecipe(String id){
        this.id = id;
    }
    @Override
    public boolean checkSuperRecipe(SuperItemStack[] matrix) {
        try {
            Constructor<? extends Distributable> constructor1 = this.distribution.getConstructor();
            this.distributionInstance = constructor1.newInstance();
            Constructor<? extends DistributionReceiver> constructor2 = this.receiver.getConstructor();
            this.receiverInstance = constructor2.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        // まず、matrixの中にbigBottleTypeを持つものが在るかチェック
        // あれば、その個数を数える。2つ以上あった時点でfalseを返す。
        // 同時にbigBottleType,bottle以外のアイテムがあればfalseを返す。
        // bottleの数も数える
        // 上記のどれでもない場合、OtherIngredientの物があるか確認。もしあったら、それを返す。
        ArrayList<ItemStack> superBottles = new ArrayList<>();
        SuperItemStack[] otherIngredientStacks = new SuperItemStack[this.otherIngredients.size()];
        ArrayList<SuperItemData> lastOtherIngredients = new ArrayList<>(otherIngredients);
        int smallBottleCount = 0;
        int bigBottleCount = 0;
        double bigBottleAmount = 0;
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
            if (this.bottle.isSimilar(itemStack.getSuperItemData())) {
                //Bukkit.getLogger().info("bottle" + id);
                smallBottleCount++;
                superBottles.add(itemStack);
                continue;
            }
            // otherIngredientの処理
            boolean flag = false;
            for (SuperItemData otherIngredient : lastOtherIngredients) {
                if (itemStack.isSimilar(otherIngredient)) {
                    //Bukkit.getLogger().info("otherIngredient");
                    lastOtherIngredients.remove(otherIngredient);
                    otherIngredientStacks[otherIngredients.indexOf(otherIngredient)] = itemStack;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //Bukkit.getLogger().info("not otherIngredient");
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


        distributionInstance.setAmount(distributionItem);
        //Bukkit.getLogger().info("isDistributable");

        if (isGetRawMatrix) {
            this.rawMatrix = matrix;
            //Bukkit.getLogger().info("rawMatrix");
            this.receiverInstance.setMatrix(matrix, "distribution_receiver");
            this.smallBottleAmount = receiverInstance.getAmount();
        } else {
            receiverInstance.setMatrix(this.matrix, "distribution_receiver");
        }
        //Bukkit.getLogger().info("distributionInstance.setMatrix");
        distributionInstance.setMatrix(this.matrix, "distribution");
        return distributionInstance.isDistributable(smallBottleAmount, smallBottleCount);
        //Bukkit.getLogger().info("is not Distributable");
    }
    public Distributable getDistribution(){
        return distributionInstance;
    }

    public void setDistribution(Distributable distribution){
        this.distribution = distribution.getClass();
        this.bigBottleData = ((SuperItem) distribution).getSuperItemData();
        this.distributionInstance = distribution;
    }
    public int getSmallBottleCount(){
        return smallBottleCount;
    }
    public double getSmallBottleAmount(){
        return smallBottleAmount;
    }

    public DistributionReceiver getReceiver(){
        return receiverInstance;
    }

    public void setReceiver(DistributionReceiver receiver) {
        this.receiver = receiver.getClass();
        this.smallBottleAmount = receiver.getAmount();
        this.receiverInstance = receiver;
    }

    public void process(){
        //Bukkit.getLogger().info(smallBottleAmount + " "+ smallBottleCount);
        distributionInstance.distribute(smallBottleAmount, smallBottleCount);
        //Bukkit.getLogger().info("distribute");
        receiverInstance.receive(smallBottleCount);
        //Bukkit.getLogger().info("receive");
        this.returnBottle = this.distributionInstance.getSuperItem();
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
        return this.getReceiver().getSuperItem();
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

    public void setGetRawMatrix(boolean isGetRawMatrix) {
        this.isGetRawMatrix = isGetRawMatrix;
    }

}
