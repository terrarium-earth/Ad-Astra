package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.config.SpaceSuitConfig;
import earth.terrarium.ad_astra.common.config.VehiclesConfig;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import earth.terrarium.ad_astra.common.item.armor.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.common.item.armor.JetSuit.FlyingType;
import earth.terrarium.ad_astra.common.util.ModKeyBindings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    private int ad_astra$shiftDownDuration = 0;
    private boolean ad_astra$clickingJetSuitTogglePower = false;
    private boolean ad_astra$clickingJetSuitToggleHover = false;

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void ad_astra$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Player player = ((Player) (Object) this);
        if (SpaceSuitConfig.netheriteSpaceSuitHasFireResistance) {
            if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
                if (NetheriteSpaceSuit.hasFullSet(player)) {
                    player.setRemainingFireTicks(0);
                    cir.setReturnValue(false);
                }
            }
        }
        if (!VehiclesConfig.RocketConfig.takeDamageInRocket && player.getVehicle() instanceof Rocket) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void ad_astra$tick(CallbackInfo ci) {
        Player player = ((Player) (Object) this);
        if (SpaceSuitConfig.enableJetSuitFlight) {
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof JetSuit jetSuit) {
                if (!player.level.isClientSide) {
                    boolean clickingJetSuitTogglePower = ModKeyBindings.jetSuitTogglePowerKeyDown(player);
                    boolean clickingJetSuitToggleHover = ModKeyBindings.jetSuitToggleHoverKeyDown(player);

                    if (!this.ad_astra$clickingJetSuitTogglePower && clickingJetSuitTogglePower) {
                        boolean newPowerEnabled = !jetSuit.isPowerEnabled(chest);
                        jetSuit.setPowerEnabled(chest, newPowerEnabled);
                        player.displayClientMessage(Component.translatable("info.ad_astra.jet_suit.power", Component.translatable("gui.ad_astra.text." + (newPowerEnabled ? "enabled" : "disabled")).withStyle(newPowerEnabled ? ChatFormatting.GREEN : ChatFormatting.RED)), true);
                    }

                    if (!this.ad_astra$clickingJetSuitToggleHover && clickingJetSuitToggleHover) {
                        boolean newHoverEnabled = !jetSuit.isHoverEnabled(chest);
                        jetSuit.setHoverEnabled(chest, newHoverEnabled);
                        player.displayClientMessage(Component.translatable("info.ad_astra.jet_suit.hover", Component.translatable("gui.ad_astra.text." + (newHoverEnabled ? "enabled" : "disabled")).withStyle(newHoverEnabled ? ChatFormatting.GREEN : ChatFormatting.RED)), true);
                    }

                    this.ad_astra$clickingJetSuitTogglePower = clickingJetSuitTogglePower;
                    this.ad_astra$clickingJetSuitToggleHover = clickingJetSuitToggleHover;
                }

                if (!player.isPassenger() && !player.getAbilities().flying && JetSuit.hasFullSet(player) && jetSuit.isPowerEnabled(chest)) {
                    jetSuit.updateFlying(player, chest);
                } else {
                    jetSuit.setFlyingType(chest, FlyingType.NONE);
                    if (!player.level.isClientSide) {
                        chest.getOrCreateTag().putBoolean("SpawnParticles", false);
                    }
                }
            }
        }
        if (player.isShiftKeyDown() && player.getVehicle() instanceof Vehicle vehicle && vehicle.cautionForDismount(player)) {
            if (this.ad_astra$shiftDownDuration == 0 && !player.getLevel().isClientSide()) {
                player.displayClientMessage(Component.translatable("message.ad_astra.vehicle.dismount_caution"), false);
            }
            this.ad_astra$shiftDownDuration++;
        } else {
            this.ad_astra$shiftDownDuration = 0;
        }
    }

    @Inject(method = "wantsToStopRiding", at = @At("TAIL"), cancellable = true)
    public void ad_astra$wantsToStopRiding(CallbackInfoReturnable<Boolean> ci) {
        Player player = ((Player) (Object) this);
        if (player.getVehicle() instanceof Vehicle vehicle && vehicle.cautionForDismount(player)) {
            if (ci.getReturnValueZ() && this.ad_astra$shiftDownDuration < 40) {
                ci.setReturnValue(false);
            }
        }
    }
}