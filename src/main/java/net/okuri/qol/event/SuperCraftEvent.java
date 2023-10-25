package net.okuri.qol.event;

import net.okuri.qol.qolCraft.superCraft.SuperRecipe;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class SuperCraftEvent extends CraftItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SuperRecipe recipe;

    public SuperCraftEvent(@NotNull SuperRecipe recipe, @NotNull InventoryView what, InventoryType.@NotNull SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action) {
        super(recipe, what, type, slot, click, action);
        this.recipe = recipe;
    }

    @Override
    public @NotNull SuperRecipe getRecipe() {
        return recipe;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
