package net.okuri.qol.superItems;

import java.util.HashSet;

public enum SuperItemTag {
    RESOURCE,
    // RESOURCE: 資源のSuperItem。x,y,z のパラメータを x+y+z~1 になるように持つ。
    INGREDIENT,
    // INGREDIENT: x,y,zのパラメータ, もしくはtaste, smell, compatibilityを持つ材料。
    LIQUOR_INGREDIENT,
    // LIQUOR_INGREDIENT: Liquorとほぼ同じだが、Consumableがfalseに設定されている中間素材。
    LIQUOR,
    // LIQUOR: 酒のSuperItem。飲めるもので、酒に付与。x,y,zのパラメータを持つ。
    FOOD,
    // FOOD: 食料のSuperItem。食べられるものすべてに付与。
    DRINK,
    // DRINK: 飲料のSuperItem。飲めるもので、酒以外に付与。
    LIQUOR_ADAPTOR,
    // LIQUOR_ADAPTOR: 様々なバフを与える。LiquorIngredientと一緒にクラフトすると様々な効果が得られる
    TOOL;
    // TOOL: 道具のSuperItem。右クリックで何かするものすべてに付与。
    private final HashSet<SuperItemType> superItemTypes = new HashSet<>();

    void addSuperItemType(SuperItemType superItemType) {
        this.superItemTypes.add(superItemType);
    }

    public HashSet<SuperItemType> getSuperItemTypes() {
        return this.superItemTypes;
    }

    public boolean hasXYZ() {
        return this == LIQUOR || this == RESOURCE || this == INGREDIENT;
    }
}
