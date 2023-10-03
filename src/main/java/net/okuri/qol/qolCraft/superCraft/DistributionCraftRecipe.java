package net.okuri.qol.qolCraft.superCraft;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    private SuperItemType bigBottleType;
    private ArrayList<Material> otherIngredients = new ArrayList<>();
    private double smallBottleAmount;
    private int smallBottleCount;
    private Material bottle = Material.GLASS_BOTTLE;
    private SuperItemType superSmallBottleType;
    private boolean isSuperSmallBottle = false;
    private Distributable distribution;
    private DistributionReceiver receiver;
    private ItemStack[] matrix = new ItemStack[9];
    public DistributionCraftRecipe(String id){
        this.id = id;
    }
    @Override
    public boolean checkSuperRecipe(ItemStack[] matrix) {
        // まず、matrixの中にbigBottleTypeを持つものが在るかチェック
        // あれば、その個数を数える。2つ以上あった時点でfalseを返す。
        // 同時にbigBottleType,bottle以外のアイテムがあればfalseを返す。
        // bottleの数も数える
        // 上記のどれでもない場合、OtherIngredientの物があるか確認。もしあったら、それを返す。

        ArrayList<Material> lastOtherIngredients = new ArrayList<>();
        ArrayList<ItemStack> superBottles = new ArrayList<>();
        lastOtherIngredients.addAll(otherIngredients);
        int smallBottleCount = 0;
        int bigBottleCount = 0;
        ItemStack distributionItem = null;
        for (ItemStack itemStack : matrix) {
            // bigBottleの処理
            if (itemStack == null) {
                Bukkit.getLogger().info("null");
                continue;
            }
            ItemMeta meta = itemStack.getItemMeta();
            if (PDCC.has(meta,PDCKey.TYPE)){
                SuperItemType type = SuperItemType.valueOf(PDCC.get(meta,PDCKey.TYPE));
                if (type == bigBottleType){
                    //Bukkit.getLogger().info("bigBottle");
                    bigBottleCount++;
                    distributionItem = itemStack;
                    if (bigBottleCount >= 2){
                        //Bukkit.getLogger().info("bigBottleCount >= 2");
                        return false;
                    }
                    continue;
                }
            }

            // bottleの処理
            if (isSuperSmallBottle){
                if (PDCC.has(meta, PDCKey.TYPE)){
                    SuperItemType type = SuperItemType.valueOf(PDCC.get(meta,PDCKey.TYPE));
                    if(type == this.superSmallBottleType){
                        smallBottleCount++;
                        superBottles.add(itemStack);
                        continue;
                    }
                }
            } else{
                if (itemStack.getType() == bottle){
                    //Bukkit.getLogger().info("bottle");
                    smallBottleCount++;
                    continue;
                }
            }
            // otherIngredientの処理
            if (lastOtherIngredients.contains(itemStack.getType())){
                lastOtherIngredients.remove(itemStack.getType());
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
        this.matrix = new ItemStack[9];
        this.matrix[0] = distributionItem;
        if (isSuperSmallBottle){
            // matrixの余っている枠にsuperBottleを追加
            for (int i=0 ; i < superBottles.size() ; i++){
                this.matrix[i+1] = superBottles.get(i);
            }
        }

        distribution.setMatrix(this.matrix,id);
        if (distribution.isDistributable(smallBottleAmount,smallBottleCount)){
            //Bukkit.getLogger().info("isDistributable");
            return true;
        }
        //Bukkit.getLogger().info("is not Distributable");
        return false;
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
        distribution.setMatrix(matrix,id);
        receiver.setMatrix(matrix,id);
        distribution.distribute(smallBottleAmount,smallBottleCount);
        receiver.receive(smallBottleCount);
    }
    public void setDistribution(Distributable distribution){
        this.distribution = distribution;
        this.bigBottleType = distribution.getType();
    }
    public void setBottle(Material bottle){
        this.bottle = bottle;
        this.superSmallBottleType = null;
        this.isSuperSmallBottle = false;
    }
    public void setBottle(SuperItemType bottle){
        this.bottle = null;
        this.superSmallBottleType = bottle;
        this.isSuperSmallBottle = true;
    }
    public void addOtherIngredient(Material material){
        otherIngredients.add(material);
    }
    @Override
    public String getId(){
        return id;
    }
    @Override
    public ItemStack getResult(){
        return receiver.getSuperItem();
    }

}
