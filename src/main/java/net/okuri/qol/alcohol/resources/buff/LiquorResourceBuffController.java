package net.okuri.qol.alcohol.resources.buff;

import net.okuri.qol.alcohol.resources.LiquorResource;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LiquorResourceBuffController {
    public static final LiquorResourceBuffController instance = new LiquorResourceBuffController();
    private final ArrayList<LiquorResourceBuff> buffs = new ArrayList<>();

    private LiquorResourceBuffController() {
    }

    public void registerBuff(LiquorResourceBuff buff) {
        this.buffs.add(buff);
    }

    public void applyBuffs(LiquorResource res, int posX, int posY, int posZ, double temp, double humid, int biomeId, Player producer) {
        for (LiquorResourceBuff buff : buffs) {
            if (buff.check(res.getSuperItemType(), posX, posY, posZ, temp, humid, biomeId, producer)) {
                buff.setBuff(res);
            }
        }
    }
}
