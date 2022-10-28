package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.items.armour.JetSuit;
import earth.terrarium.ad_astra.items.armour.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.util.ModKeyBindings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void adastra_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        if (AdAstra.CONFIG.spaceSuit.netheriteSpaceSuitHasFireResistance) {
            PlayerEntity player = ((PlayerEntity) (Object) this);
            if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
                if (NetheriteSpaceSuit.hasFullSet(player)) {
                    player.setFireTicks(0);
                    ci.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        if (AdAstra.CONFIG.spaceSuit.enableJetSuitFlight) {
            PlayerEntity player = ((PlayerEntity) (Object) this);
            if (!player.hasVehicle()) {
                ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
                if (chest.getItem() instanceof JetSuit jetSuit) {
                    if (ModKeyBindings.jumpKeyDown(player)) {
                        if (JetSuit.hasFullSet(player)) {
                            jetSuit.fly(player, chest);
                        }
                    } else {
                        jetSuit.isFallFlying = false;
                        if (!player.world.isClient) {
                            chest.getOrCreateNbt().putBoolean("spawn_particles", false);
                        }
                    }
                }
            }
        }
    }
}