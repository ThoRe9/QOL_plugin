package net.okuri.qol.superItems.factory.adapter;

import net.okuri.qol.superItems.SuperItemType;

public class FishermanAdapter extends LiquorAdapter {

    public FishermanAdapter() {
        super(SuperItemType.FISHERMAN_ADAPTER, AdapterID.FISHERMAN);
        super.setTasteAmplifier(1.10);
        super.setSmellAmplifier(1.10);
        super.setCompatibilityAmplifier(1.10);
        super.setAlcPercentAmplifier(1.5);
        super.setAmountAddition(-50);
    }
}
