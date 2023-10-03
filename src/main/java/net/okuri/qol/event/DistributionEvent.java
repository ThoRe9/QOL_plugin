package net.okuri.qol.event;

import net.okuri.qol.qolCraft.superCraft.Distributable;
import net.okuri.qol.qolCraft.superCraft.DistributionCraftRecipe;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

public class DistributionEvent extends CraftItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public DistributionEvent(@NotNull Recipe recipe, @NotNull InventoryView what, InventoryType.@NotNull SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action) {
        super(recipe, what, type, slot, click, action);
    }
    public DistributionEvent(@NotNull DistributionCraftRecipe recipe, @NotNull InventoryView what, InventoryType.@NotNull SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action) {
        super(recipe, what, type, slot, click, action);
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
