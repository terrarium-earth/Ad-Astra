package net.mrscauthd.beyond_earth.events.forgeevents;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;

public class ItemTickEvent extends ItemEvent {

    public ItemTickEvent(ItemEntity itemEntity) {
        super(itemEntity);
    }
}