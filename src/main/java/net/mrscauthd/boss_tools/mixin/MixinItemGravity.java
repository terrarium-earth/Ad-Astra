package net.mrscauthd.boss_tools.mixin;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;
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
            if (Methodes.isWorld(w.world, new ResourceLocation(BossToolsMod.ModId, "moon"))) {
                itemGravityMath(w,0.05);
            }

            if (Methodes.isWorld(w.world, new ResourceLocation(BossToolsMod.ModId, "mars"))) {
                itemGravityMath(w,0.06);
            }

            if (Methodes.isWorld(w.world, new ResourceLocation(BossToolsMod.ModId, "mercury"))) {
                itemGravityMath(w,0.05);
            }

            if (Methodes.isWorld(w.world, new ResourceLocation(BossToolsMod.ModId, "venus"))) {
                itemGravityMath(w,0.06);
            }

            if (Methodes.isOrbitWorld(w.world)) {
                itemGravityMath(w,0.05);
            }
        }

    }

    private static boolean GravityCheckItem(ItemEntity entity) {
        if (!entity.isInWater() && !entity.isInLava() && !entity.hasNoGravity()) {
            return true;
        }

        return false;
    }

    private static void itemGravityMath(ItemEntity entity, double gravity) {
        if (MinecraftForge.EVENT_BUS.post(new ItemGravityEvent(entity))) {
            return;
        }

        entity.setMotion(entity.getMotion().getX(), entity.getMotion().getY() / 0.98 + 0.08 - gravity, entity.getMotion().getZ());
    }

}