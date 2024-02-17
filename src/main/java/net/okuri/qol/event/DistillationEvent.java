package net.okuri.qol.event;

import net.okuri.qol.qolCraft.distillation.Distillable;
import net.okuri.qol.superItems.SuperItemStack;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class DistillationEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Distillable resultClass;
    private SuperItemStack resultItem;
    private final FurnaceSmeltEvent furnaceSmeltEvent;

    public DistillationEvent(FurnaceSmeltEvent event, Distillable resultClass, SuperItemStack resultItem) {
        SuperItemStack ingredient = new SuperItemStack(event.getSource());
        event.setResult(resultItem);
        this.furnaceSmeltEvent = event;
        this.resultClass = resultClass;
        this.resultItem = resultItem;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Distillable getResultClass() {
        return resultClass;
    }

    public SuperItemStack getResultItem() {
        return resultItem;
    }

    public void setResult(SuperItemStack resultItem) {
        this.furnaceSmeltEvent.setResult(resultItem);
        this.resultItem = resultItem;
    }


    public FurnaceSmeltEvent getFurnaceSmeltEvent() {
        return furnaceSmeltEvent;
    }
}
