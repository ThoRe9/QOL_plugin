package net.okuri.qol.superItems.factory.adapter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum AdapterID {
    FISHERMAN(1, FishermanAdapter.class, "Fisherman's Liquor", Component.text("Fisherman's ").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD));

    private final int id;
    private final Class<? extends LiquorAdapter> adapterClass;
    private final String name;
    private final Component header;

    AdapterID(int id, Class<? extends LiquorAdapter> adapterClass, String name, Component header) {
        this.id = id;
        this.adapterClass = adapterClass;
        this.name = name;
        this.header = header;
    }

    public static AdapterID getAdapterID(int id) {
        for (AdapterID adapterID : AdapterID.values()) {
            if (adapterID.getID() == id) {
                return adapterID;
            }
        }
        return null;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Component getHeader() {
        return this.header;
    }

    public LiquorAdapter getAdapterClass() {
        try {
            return this.adapterClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
