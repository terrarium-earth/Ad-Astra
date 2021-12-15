package net.mrscauthd.astrocraft.events.forgeevents;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;

public class ItemGravityEvent extends ItemEvent {

    public ItemGravityEvent(ItemEntity itemEntity) {
        super(itemEntity);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

}