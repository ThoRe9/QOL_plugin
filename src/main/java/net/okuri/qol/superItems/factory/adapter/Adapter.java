package net.okuri.qol.superItems.factory.adapter;

import net.kyori.adventure.text.Component;
import net.okuri.qol.superItems.SuperItemType;
import net.okuri.qol.superItems.factory.SuperItem;
import net.okuri.qol.superItems.itemStack.SuperItemStack;

public abstract class Adapter extends SuperItem {
    private final AdapterID adapterID;
    private Component header;
    private Component displayName;

    public Adapter(SuperItemType type, AdapterID adapterID) {
        super(type);
        this.adapterID = adapterID;
        this.displayName = Component.text(adapterID.getName()).append(Component.text(" Adapter"));
        this.header = adapterID.getHeader();
    }

    @Override
    public SuperItemStack getSuperItem() {
        SuperItemStack result = new SuperItemStack(this.getSuperItemType());
        result.setDisplayName(this.displayName);
        result.setConsumable(false);
        return result;
    }

    public Component getHeader() {
        return header;
    }

    protected void setHeader(Component header) {
        this.header = header;
    }

    public String getAdapterName() {
        return this.adapterID.getName();
    }

    public AdapterID getAdapterID() {
        return this.adapterID;
    }
}