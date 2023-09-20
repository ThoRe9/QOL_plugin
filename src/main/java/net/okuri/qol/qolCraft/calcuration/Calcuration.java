package net.okuri.qol.qolCraft.calcuration;

import java.util.ArrayList;

public interface Calcuration {
    void setVariable(double... variables);
    void calcuration();
    ArrayList<Double> getAns();
}
