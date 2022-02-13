package net.mrscauthd.beyond_earth.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.events.forgeevents.ItemGravityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemGravity {

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ItemEntity w = (ItemEntity) ((Object) this);

        if (GravityCheckItem(w)) {
            if (Methods.isWorld(w.level, Methods.moon)) {
                itemGravityMath(w,0.05);
            }
            else if (Methods.isWorld(w.level, Methods.mars)) {
                itemGravityMath(w,0.06);
            }
            else if (Methods.isWorld(w.level, Methods.mercury)) {
                itemGravityMath(w,0.05);
            }
            else if (Methods.isWorld(w.level, Methods.venus)) {
                itemGravityMath(w,0.06);
            }
            else if (Methods.isWorld(w.level, Methods.glacio)) {
                itemGravityMath(w,0.06);
            }
            else if (Methods.isOrbitWorld(w.level)) {
                itemGravityMath(w,0.05);
            }
        }
    }

    private static boolean GravityCheckItem(ItemEntity entity) {
        return !entity.isInWater() && !entity.isInLava() && !entity.isNoGravity();
    }

    private static void itemGravityMath(ItemEntity entity, double gravity) {
        if (MinecraftForge.EVENT_BUS.post(new ItemGravityEvent(entity, gravity))) {
            return;
        }

        entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y / 0.98 + 0.08 - gravity, entity.getDeltaMovement().z);
    }
}