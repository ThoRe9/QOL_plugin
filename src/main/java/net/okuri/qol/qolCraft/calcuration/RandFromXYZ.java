package net.okuri.qol.qolCraft.calcuration;

import org.bukkit.Bukkit;

import java.util.ArrayList;

public class RandFromXYZ implements Calcuration {
    private int x;
    private int y;
    private int z;
    private int divLine;
    private double scale;
    private ArrayList<Double> answer = new ArrayList<>();

    @Override
    public void setVariable(double... variable) {
        if (variable.length != 5) {
            throw new IllegalArgumentException("Variables are not long enough.");
        }
        this.x = (int) variable[0];
        this.y = (int) variable[1];
        this.z = (int) variable[2];
        this.divLine = (int) variable[3];
        this.scale = variable[4];
    }

    @Override
    public void calcuration() {
        answer = new ArrayList<>();
        double dx = (((double) (Math.abs(x + y)) % divLine) / divLine) * scale;
        double dy = (((double) (Math.abs(y + z)) % divLine) / divLine) * scale;
        double dz = (((double) (Math.abs(z + x)) % divLine) / divLine) * scale;
        answer.add(dx - dz);
        answer.add(dy - dx);
        answer.add(dz - dy);
    }

    @Override
    public ArrayList<Double> getAns() {
        return this.answer;
    }
}

