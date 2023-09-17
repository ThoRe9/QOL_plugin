package net.okuri.qol.event;

import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.qolCraft.distillation.DistillationRecipe;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DistillationEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Distillable result;
    private final FurnaceSmeltEvent furnaceSmeltEvent;

    public DistillationEvent(FurnaceSmeltEvent event, Distillable result){
        event.setResult(result.getSuperItem());
        this.furnaceSmeltEvent = event;
        this.result = result;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
