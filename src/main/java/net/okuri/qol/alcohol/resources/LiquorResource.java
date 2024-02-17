package net.okuri.qol.alcohol.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.alcohol.resources.buff.LiquorResourceBuffController;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.loreGenerator.FermentationLore;
import net.okuri.qol.loreGenerator.LoreGenerator;
import net.okuri.qol.loreGenerator.ResourceLore;
import net.okuri.qol.qolCraft.resource.Resource;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.SuperItemData;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemTag;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * 酒類の原料となるアイテムを生成するためのベースとなるクラスです。
 */
public class LiquorResource extends Resource implements SuperCraftable {
    private final String display;
    private final Taste baseTaste;
    private final Map<Taste, Double> additionalTastes = new HashMap<>();
    /**
     * baseTempAmp : BaseTasteを弱めたり強めたりできる
     */
    private double baseTasteAmp = 1;
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
    // effectRate : 1 = Durにすべてのパラメータが使われる 0 = Ampにすべてのパラメータが使われる
    private double effectRate = 0.5;
    private double fermentationRate;
    Map<Taste, Double> tastes = new HashMap<>();
    ResourceLore lore = new ResourceLore();
    private Component header = Component.text("");

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
        super(blockMaterial, superItemType, probability);
        assert superItemType.hasTag(SuperItemTag.RESOURCE);
        this.display = name;
        this.baseTaste = baseTaste;
        setFactors(seed);
        super.setConsumable(false);
    }

    private void initialize(SuperItemStack stack) {
        this.temp = 0;
        this.humid = 0;
        this.biomeId = PDCC.get(stack.getItemMeta(), PDCKey.BIOME_ID);
        this.effectRate = 1;
        this.tastes.clear();
        this.fermentationRate = 0;
        this.add(stack);
    }

    private void add(SuperItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        int[] pos = PDCC.get(meta, PDCKey.RESOURCE_POS);
        this.posX += pos[0];
        this.posY += pos[1];
        this.posZ += pos[2];
        this.temp += (double) PDCC.get(meta, PDCKey.TEMP);
        this.humid += (double) PDCC.get(meta, PDCKey.HUMID);
        this.biomeId += (int) PDCC.get(meta, PDCKey.BIOME_ID);
        this.biomeId /= 2;
        this.effectRate *= (double) PDCC.get(meta, PDCKey.LIQUOR_EFFECT_RATIO);
        // produce TODO
        this.tastes.putAll(PDCC.getTastes(meta));
        if (PDCC.has(meta, PDCKey.FERMENTATION_RATE)) {
            this.fermentationRate += (double) PDCC.get(meta, PDCKey.FERMENTATION_RATE);
        }
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
        this.baseTasteValue = calcParam(posX / 16, posZ / 16) * baseTasteAmp;
        this.effectRate = calcEffectRate(temp, humid);
        this.delicacy = this.baseTasteValue * (1 - this.effectRate);
        this.tastes.put(baseTaste, baseTasteValue);
        for (Map.Entry<Taste, Double> entry : additionalTastes.entrySet()) {
            this.tastes.put(entry.getKey(), baseTasteValue * entry.getValue());
        }
        this.lore = new ResourceLore();
        LiquorResourceBuffController.instance.applyBuffs(this, posX, posY, posZ, temp, humid, biomeId, producer);
    }

    private double calcParam(int x, int y) {
        double a = sinFactor * Math.sin(x * factors[0][0] + y * factors[0][1] + factors[0][2]);
        double b = (1 - sinFactor) * Math.cos(x * factors[1][0] + y * factors[1][1] + factors[1][2]);
        return (a + b + 1) * 0.4;
    }

    private double calcEffectRate(double temp, double humid) {
        return Math.exp((-1) * (Math.pow(temp, 2) + Math.pow(humid, 2)));
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        boolean flag = false;
        this.lore = new ResourceLore();
        if (id.equals("initialize")) {
            initialize(matrix[0]);
            return;
        }
        for (SuperItemStack stack : matrix) {
            if (new SuperItemData(SuperItemTag.LIQUOR_RESOURCE).isSimilar(stack.getSuperItemData())) {
                if (!flag) {
                    initialize(stack);
                    flag = true;
                } else {
                    add(stack);
                }
            }
            if (new SuperItemData(SuperItemType.YEAST).isSimilar(stack.getSuperItemData())) {
                this.fermentationRate = (double) PDCC.get(stack.getItemMeta(), PDCKey.FERMENTATION_RATE);
            }
        }
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack item = new SuperItemStack(this.getSuperItemType(), 1);
        item.setDisplayName(this.header.append(Component.text(display).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD)));
        ItemMeta meta = item.getItemMeta();

        for (Map.Entry<Taste, Double> entry : tastes.entrySet()) {
            lore.addTaste(entry.getKey(), entry.getValue());
        }

        lore.setDelicacy(delicacy);
        lore.setEffectRate(effectRate);
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.addLore(lore);

        if (fermentationRate != 0) {
            PDCC.set(meta, PDCKey.FERMENTATION_RATE, fermentationRate);
            FermentationLore fLore = new FermentationLore(fermentationRate, 0);
            loreGenerator.addLore(fLore);
        }
        loreGenerator.setLore(meta);


        PDCC.setLiquorResource(meta, this);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public SuperItemStack getDebugItem(int... args) {
        if (args.length == 2) {
            this.setResVariables(0, 0, 0, args[0], args[1], 0, null);
        } else {
            this.setResVariables(0, 0, 0, 0, 0, 0, null);
        }
        return this.getSuperItem();
    }

    public Map<Taste, Double> getTastes() {
        return tastes;
    }

    public String getDisplay() {
        return display;
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

    public double getEffectRate() {
        return effectRate;
    }

    public double getFermentationRate() {
        return fermentationRate;
    }


    /**
     * 味を追加します。
     *
     * @param taste 味
     * @param value baseTasteValueに対する割合
     */
    public void setAdditionalTaste(Taste taste, double value) {
        additionalTastes.put(taste, value);
    }

    public void setBaseTasteAmp(double baseTasteAmp) {
        this.baseTasteAmp = baseTasteAmp;
    }

    public void addHeader(Component header) {
        this.header = this.header.append(header);
    }
}
