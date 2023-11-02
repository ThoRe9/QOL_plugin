package net.okuri.qol.superItems;

import java.util.HashSet;

public enum SuperItemTag {
    RESOURCE,
    // RESOURCE: 資源のSuperItem。x,y,z のパラメータを x+y+z~1 になるように持つ。
    FOOD,
    // FOOD: 食料のSuperItem。食べられるものすべてに付与。
    DRINK,
    // DRINK: 飲料のSuperItem。飲めるもので、酒以外に付与。
    LIQUOR,
    // LIQUOR: 酒のSuperItem。飲めるもので、酒に付与。
    TOOL;
    // TOOL: 道具のSuperItem。右クリックで何かするものすべてに付与。
    private final HashSet<SuperItemType> superItemTypes = new HashSet<>();

    void addSuperItemType(SuperItemType superItemType) {
        this.superItemTypes.add(superItemType);
    }

    public HashSet<SuperItemType> getSuperItemTypes() {
        return this.superItemTypes;
    }
}
