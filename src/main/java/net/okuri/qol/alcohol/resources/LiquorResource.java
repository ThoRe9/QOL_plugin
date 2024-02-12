package net.okuri.qol.alcohol.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.loreGenerator.ResourceLore;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * 酒類の原料となるアイテムを生成するためのベースとなるクラスです。
 */
public class LiquorResource extends SuperItem {
    private final String display;
    private final double probability;
    private final Material blockMaterial;
    private final Taste baseTaste;
    // 以下パラメータ計算用変数
    private double[][] factors = new double[2][3];
    private double sinFactor = 0.5;
    //
    private int posX;
    private int posY;
    private int posZ;
    private double temp;
    private double humid;
    private int biomeId;
    private Player producer;
    //以下パラメータ
    private double baseTasteValue;
    private double delicacy = 0.5;

    /**
     * 酒類の原料となるアイテムを生成するためのベースとなるクラスです。
     *
     * @param name          表示名
     * @param blockMaterial ブロックのマテリアル
     * @param superItemType スーパーアイテムタイプ
     * @param probability   生成確率(0.0~1.0)
     * @param baseTaste     基本の味
     */
    public LiquorResource(String name, Material blockMaterial, SuperItemType superItemType, double probability, Taste baseTaste, int seed) {
        super(superItemType);
        assert superItemType.hasTag(SuperItemTag.RESOURCE);
        this.display = name;
        this.blockMaterial = blockMaterial;
        assert probability >= 0.0 && probability <= 1.0;
        this.probability = probability;
        this.baseTaste = baseTaste;
        setFactors(seed);
    }

    public LiquorResource(SuperItemStack item) {
        super(item.getSuperItemType());
        assert item.getSuperItemType().hasTag(SuperItemTag.RESOURCE);
        ItemMeta meta = item.getItemMeta();
        this.display = ((LiquorResource) SuperItemType.getSuperItemClass(this.getSuperItemType())).getDisplay();
        this.blockMaterial = ((LiquorResource) SuperItemType.getSuperItemClass(this.getSuperItemType())).getBlockMaterial();
        this.probability = ((LiquorResource) SuperItemType.getSuperItemClass(this.getSuperItemType())).getProbability();
        this.baseTaste = ((LiquorResource) SuperItemType.getSuperItemClass(this.getSuperItemType())).baseTaste;

    }

    private void setFactors(int seed) {
        long[] rands = new long[7];
        rands[0] = (48271L * seed) % 2147483647;
        for (int i = 1; i < 7; i++) {
            rands[i] = (48271 * rands[i - 1]) % 2147483647;
        }
        for (int i = 0; i < 7; i++) {
            rands[i] /= 100000;
        }
        for (int i = 0; i < 3; i++) {
            factors[0][i] = rands[i] / 2500.0;
            factors[1][i] = rands[i + 3] / 2500.0;
        }
        sinFactor = rands[6] / 25000.0;
    }

    public void setResVariables(int posX, int posY, int posZ, double temp, double humid, int biomeId, Player producer) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.temp = temp;
        this.humid = humid;
        this.biomeId = biomeId;
        this.producer = producer;
        this.baseTasteValue = calcParam(posX / 16, posY / 16);
    }

    private double calcParam(int x, int y) {
        double a = sinFactor * Math.sin(x * factors[0][0] + y * factors[0][1] + factors[0][2]);
        double b = (1 - sinFactor) * Math.cos(x * factors[1][0] + y * factors[1][1] + factors[1][2]);
        return (a + b + 2) * 0.4;
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(this.getSuperItemType(), 1);
        item.setDisplayName(Component.text(display).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD));
        ItemMeta meta = item.getItemMeta();

        ResourceLore lore = new ResourceLore();
        lore.addTaste(baseTaste, baseTasteValue);
        lore.setDelicacy(delicacy);
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addLore(lore);
        loreGenerator.setLore(meta);

        PDCC.setLiquorResource(meta, this);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        this.setResVariables(0, 0, 0, 0.0, 0.0, 0, null);
        return this.getSuperItem();
    }

    public Map<Taste, Double> getTastes() {
        Map<Taste, Double> tastes = new HashMap<>();
        tastes.put(baseTaste, baseTasteValue);
        return tastes;
    }

    public String getDisplay() {
        return display;
    }

    public double getProbability() {
        return probability;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumid() {
        return humid;
    }

    public int getBiomeId() {
        return biomeId;
    }

    public Player getProducer() {
        return producer;
    }

    public double getBaseTasteValue() {
        return baseTasteValue;
    }

    public double getDelicacy() {
        return delicacy;
    }
}
