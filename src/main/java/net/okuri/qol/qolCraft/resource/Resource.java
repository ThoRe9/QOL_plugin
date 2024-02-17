package net.okuri.qol.qolCraft.resource;

import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Resource extends SuperItem {
    private final double probability;
    private final Material blockMaterial;
    private double minTemp = -10;
    private double maxTemp = 10;
    private double minHumid = -10;
    private double maxHumid = 10;


    public Resource(Material blockMaterial, SuperItemType superItemType, double probability) {
        super(superItemType);
        this.blockMaterial = blockMaterial;
        this.probability = probability;
        assert probability >= 0.0 && probability <= 1.0;
    }

    public abstract void setResVariables(int posX, int posY, int posZ, double temp, double humid, int biomeId, Player producer);


    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinHumid() {
        return minHumid;
    }

    public void setMinHumid(double minHumid) {
        this.minHumid = minHumid;
    }

    public double getMaxHumid() {
        return maxHumid;
    }

    public void setMaxHumid(double maxHumid) {
        this.maxHumid = maxHumid;
    }

    public double getProbability() {
        return probability;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }
}
