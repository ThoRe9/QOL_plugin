package net.okuri.qol.superItems.factory;

import net.okuri.qol.LoreGenerator;
import net.okuri.qol.qolCraft.superCraft.SuperCraftable;
import net.okuri.qol.superItems.itemStack.SuperItemStack;
import net.okuri.qol.superItems.itemStack.SuperResourceStack;
import org.bukkit.Bukkit;

public class FusionCraft implements SuperCraftable {
    private SuperResourceStack resource1;
    private SuperResourceStack resource2;

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.resource1 = new SuperResourceStack(matrix[0]);
        this.resource2 = new SuperResourceStack(matrix[1]);
        Bukkit.getLogger().info("resource1: " + resource1.getSuperItemType().getStringType());
        Bukkit.getLogger().info("resource2: " + resource2.getSuperItemType().getStringType());
    }

    @Override
    public SuperItemStack getSuperItem() {
        // resource1とresource2のx,y,zを足して 1.9で割ったものをパラメータとしてもつものを返す
        SuperResourceStack result = new SuperResourceStack(resource1);
        double newX = (resource1.getX() + resource2.getX()) / 1.9;
        double newY = (resource1.getY() + resource2.getY()) / 1.9;
        double newZ = (resource1.getZ() + resource2.getZ()) / 1.9;
        double newQuality = (resource1.getQuality() + resource2.getQuality()) / 1.9;
        int newRarity = SuperItem.getRarity(newX, newY, newZ);
        result.setX(newX);
        result.setY(newY);
        result.setZ(newZ);
        result.setAmount(1);
        LoreGenerator loreGenerator = new LoreGenerator();
        loreGenerator.setParams(newX, newY, newZ);
        loreGenerator.setQuality(newQuality);
        loreGenerator.setRarity(newRarity);
        loreGenerator.addInfo("Blended " + result.getSuperItemType().getStringType());
        result.setLore(loreGenerator);
        return result;
    }
}
