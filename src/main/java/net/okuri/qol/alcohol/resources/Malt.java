package net.okuri.qol.alcohol.resources;

import net.okuri.qol.alcohol.taste.BarleyTaste;
import net.okuri.qol.alcohol.taste.MaltTaste;
import net.okuri.qol.alcohol.taste.Taste;
import net.okuri.qol.qolCraft.maturation.Maturable;
import net.okuri.qol.superItems.SuperItemStack;
import net.okuri.qol.superItems.SuperItemType;
import org.bukkit.Material;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class Malt extends LiquorResource implements Maturable {
    public Malt() {
        super("麦芽", Material.BEDROCK, SuperItemType.MALT, 0.00, MaltTaste.instance, 115);
    }

    @Override
    public void setMaturationVariable(ArrayList<SuperItemStack> ingredients, LocalDateTime start, LocalDateTime end, double temp, double humid) {
        super.setMatrix(new SuperItemStack[]{ingredients.get(0)}, "malt");
        double days = Duration.between(start, end).toDays() * super.getFermentationRate();
        for (Map.Entry<Taste, Double> entry : super.getTastes().entrySet()) {
            if (entry.getKey().equals(BarleyTaste.instance)) {
                double value = entry.getValue() * growFunc(days);
                super.getTastes().put(entry.getKey(), entry.getValue() - value);
                super.getTastes().put(MaltTaste.instance, value);
            }
        }
    }

    private double growFunc(double x) {
        /**
         * a : 無限の時の値
         * b : 1になるx
         * c : 速度
         */
        double a = 2;
        double b = 1;
        double c = 0.7;
        return ((((-1) * a * b * (a - 1)) / (Math.pow(x, c) + b * (a - 1))) + a) * 0.5;
    }
}
