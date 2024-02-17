package net.okuri.qol.alcohol;

import net.okuri.qol.PDCC;
import net.okuri.qol.PDCKey;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

public class LiquorGlass extends Liquor {
    private boolean craftable = true;
    private boolean isAdd = false;
    private SuperItemStack liquor;
    private SuperItemStack glass;
    public LiquorGlass() {
        super(SuperItemType.LIQUOR_GLASS);
    }

    @Override
    public void setMatrix(SuperItemStack[] matrix, String id) {
        this.craftable = true;
        this.isAdd = false;
        int liquorPos = 0;
        this.liquor = null;
        int glassPos = 0;
        this.glass = null;
        boolean glassFlag = false;
        double beforeAmount = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) {
                continue;
            } else if (matrix[i].getType() == Material.AIR) {
                continue;
            } else if (matrix[i].getSuperItemType() == SuperItemType.LIQUOR) {
                liquorPos = i;
                liquor = matrix[i];
            } else if (matrix[i].getSuperItemType() == SuperItemType.GLASS) {
                if (glassFlag) {
                    this.craftable = false;
                    return;
                }
                glassPos = i;
                glassFlag = true;
                glass = matrix[i];
            } else if (matrix[i].getSuperItemType() == SuperItemType.LIQUOR_GLASS) {
                if (glassFlag) {
                    this.craftable = false;
                    return;
                }
                glassPos = i;
                glassFlag = true;
                beforeAmount = PDCC.get(matrix[i].getItemMeta(), PDCKey.LIQUOR_AMOUNT);
                this.glass = matrix[i];
            }
        }


        double maxAmount = PDCC.get(glass.getItemMeta(), PDCKey.GLASS_VOLUME);
        if (beforeAmount != 0) {
            this.setRecentAmount(beforeAmount);
            super.setMatrix(new SuperItemStack[]{glass}, "initialize");
            this.isAdd = true;
        } else {
            super.setMatrix(new SuperItemStack[]{liquor}, "initialize");
        }

        this.setRecentAmount(maxAmount * 0.125 * Math.abs(liquorPos - glassPos));
        this.maxAmount = maxAmount;
    }

    @Override
    public double getAmount() {
        if (!this.craftable) {
            return 1000000000;
        }
        return super.getRecentAmount();
    }

    @Override
    public void receive(int count) {
        this.setCount(count);
        if (this.isAdd) {
            Liquor addingLiquor = new Liquor(this.liquor);
            addingLiquor.setRecentAmount(this.getRecentAmount());
            addingLiquor.adjustTasteValue();
            super.setMatrix(new SuperItemStack[]{addingLiquor.getSuperItem()}, "add");
        } else {
            this.adjustTasteValue();
        }
    }


}
