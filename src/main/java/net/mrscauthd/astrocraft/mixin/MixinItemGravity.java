package net.mrscauthd.astrocraft.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.events.Methodes;
import net.mrscauthd.astrocraft.events.forgeevents.ItemGravityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemGravity {
    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ItemEntity w = (ItemEntity) ((Object) this);

        if (GravityCheckItem(w)) {
            if (Methodes.isWorld(w.level, new ResourceLocation(AstroCraftMod.MODID, "moon"))) {
                itemGravityMath(w,0.05);
            }

            if (Methodes.isWorld(w.level, new ResourceLocation(AstroCraftMod.MODID, "mars"))) {
                itemGravityMath(w,0.06);
            }

            if (Methodes.isWorld(w.level, new ResourceLocation(AstroCraftMod.MODID, "mercury"))) {
                itemGravityMath(w,0.05);
            }

            if (Methodes.isWorld(w.level, new ResourceLocation(AstroCraftMod.MODID, "venus"))) {
                itemGravityMath(w,0.06);
            }

            if (Methodes.isOrbitWorld(w.level)) {
                itemGravityMath(w,0.05);
            }
        }

    }

    private static boolean GravityCheckItem(ItemEntity entity) {
        if (!entity.isInWater() && !entity.isInLava() && !entity.isNoGravity()) {
            return true;
        }

        return false;
    }

    private static void itemGravityMath(ItemEntity entity, double gravity) {
        if (MinecraftForge.EVENT_BUS.post(new ItemGravityEvent(entity))) {
            return;
        }

        entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y / 0.98 + 0.08 - gravity, entity.getDeltaMovement().z);
    }

}