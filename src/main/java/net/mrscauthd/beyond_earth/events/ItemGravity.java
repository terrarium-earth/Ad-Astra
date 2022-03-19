package net.mrscauthd.beyond_earth.events;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.forgeevents.ItemGravityEvent;

public class ItemGravity {

    public static void itemGravity(ItemEntity itemEntity) {
        if (gravityCheckItem(itemEntity)) {
            if (Methods.isWorld(itemEntity.level, Methods.moon)) {
                itemGravityMath(itemEntity,0.05);
            }
            else if (Methods.isWorld(itemEntity.level, Methods.mars)) {
                itemGravityMath(itemEntity,0.06);
            }
            else if (Methods.isWorld(itemEntity.level, Methods.mercury)) {
                itemGravityMath(itemEntity,0.05);
            }
            else if (Methods.isWorld(itemEntity.level, Methods.venus)) {
                itemGravityMath(itemEntity,0.06);
            }
            else if (Methods.isWorld(itemEntity.level, Methods.glacio)) {
                itemGravityMath(itemEntity,0.06);
            }
            else if (Methods.isOrbitWorld(itemEntity.level)) {
                itemGravityMath(itemEntity,0.05);
            }
        }
    }

    private static boolean gravityCheckItem(ItemEntity entity) {
        return !entity.isInWater() && !entity.isInLava() && !entity.isNoGravity();
    }

    private static void itemGravityMath(ItemEntity entity, double gravity) {
        if (MinecraftForge.EVENT_BUS.post(new ItemGravityEvent(entity, gravity))) {
            return;
        }
        entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y / 0.98 + 0.08 - gravity, entity.getDeltaMovement().z);
    }
}
