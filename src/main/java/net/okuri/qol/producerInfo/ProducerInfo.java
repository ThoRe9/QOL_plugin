package net.okuri.qol.producerInfo;

import net.okuri.qol.superItems.SuperItemData;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ProducerInfo {
    private final ArrayList<ProducerInfo> children = new ArrayList<>();
    private final String playerID;
    private final double playerQuality;
    private final SuperItemData itemData;
    private ProducerInfo parent;

    public ProducerInfo(Player player, double playerQuality, SuperItemData itemType) {
        this.parent = null;
        this.playerID = player.getUniqueId().toString();
        this.playerQuality = playerQuality;
        this.itemData = itemType;
    }

    public ProducerInfo(String playerID, double playerQuality, SuperItemData itemType) {
        this.parent = null;
        this.playerID = playerID;
        this.playerQuality = playerQuality;
        this.itemData = itemType;
    }

    public void addChild(ProducerInfo child) {
        children.add(child);
        child.parent = this;
    }

    public void removeChild(ProducerInfo child) {
        children.remove(child);
        child.parent = null;
    }

    public int getDepth() {
        if (parent == null) {
            return 0;
        } else {
            return parent.getDepth() + 1;
        }
    }

    public ArrayList<ProducerInfo> getChildren() {
        return children;
    }

    public ProducerInfo getChild(SuperItemData data) {
        for (ProducerInfo child : children) {
            if (child.getItemData().equals(data)) {
                return child;
            }
        }
        return null;
    }

    public String[] getProducerInfo() {
        ArrayList<String> info = new ArrayList<>();
        String text = "ItemType: " + itemData.getType().toString() + ", Quality: " + playerQuality + ", PlayerID: " + playerID;
        info.add(text);
        if (children.size() != 0) {
            for (ProducerInfo child : children) {
                String[] childInfo = child.getProducerInfo();
                for (String s : childInfo) {
                    info.add("  " + s);
                }
            }
        }
        return info.toArray(new String[0]);
    }

    public ProducerInfo getParent() {
        return parent;
    }

    public String getPlayerID() {
        return playerID;
    }

    public double getPlayerQuality() {
        return playerQuality;
    }

    public SuperItemData getItemData() {
        return itemData;
    }

}
