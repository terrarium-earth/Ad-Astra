package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.client.screen.PlayerOverlayScreen;
import earth.terrarium.ad_astra.client.sound.PlanetSoundPlayer;
import earth.terrarium.ad_astra.client.sound.PlanetWeatherSoundPlayer;
import earth.terrarium.ad_astra.common.entity.vehicle.Lander;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidItemHandler;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow
    @Final
    private List<AmbientSoundHandler> ambientSoundHandlers;

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    public void adastra_LocalPlayer(Minecraft minecraft, ClientLevel level, ClientPacketListener networkHandler, StatsCounter stats, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting, CallbackInfo ci) {
        this.ambientSoundHandlers.add(new PlanetWeatherSoundPlayer((LocalPlayer) (Object) (this), minecraft.getSoundManager()));
        this.ambientSoundHandlers.add(new PlanetSoundPlayer((LocalPlayer) (Object) (this), minecraft.getSoundManager()));

    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {

        LocalPlayer player = ((LocalPlayer) (Object) this);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);

        if (SpaceSuit.hasFullSet(player)) {
            PlayerOverlayScreen.shouldRenderOxygen = true;
            if (!chest.isEmpty() && chest.getItem() instanceof SpaceSuit suit) {
                PlatformFluidItemHandler oxygen = FluidHooks.getItemFluidManager(chest);

                // Render oxygen info
                PlayerOverlayScreen.oxygenRatio = Mth.clamp(oxygen.getFluidInTank(0).getFluidAmount() / (double) suit.getTankSize(), 0.0, 1.0);
                PlayerOverlayScreen.doesNotNeedOxygen = OxygenUtils.entityHasOxygen(player.level, player) && !player.isUnderWater();
            }
        } else {
            PlayerOverlayScreen.shouldRenderOxygen = false;
        }

        if (JetSuit.hasFullSet(player)) {
            PlayerOverlayScreen.shouldRenderBattery = true;
            if (!chest.isEmpty() && chest.getItem() instanceof JetSuit) {
                JetSuit.updateBatteryOverlay(chest);
            }
        } else {
            PlayerOverlayScreen.shouldRenderBattery = false;
        }

        if (player.getVehicle() instanceof Vehicle vehicle) {
            // Rocket
            if (vehicle.renderPlanetBar()) {
                PlayerOverlayScreen.shouldRenderBar = true;
                if (vehicle instanceof Rocket rocket) {
                    if (rocket.isFlying()) {
                        PlayerOverlayScreen.countdownSeconds = rocket.getCountdownSeconds();
                    }
                }
            }

            // Show the warning screen when falling in a lander
            if (vehicle instanceof Lander lander) {

                double speed = lander.getDeltaMovement().y();
                if (speed != 0.0 && lander.level.getBlockState(lander.getOnPos().below()).isAir()) {
                    PlayerOverlayScreen.shouldRenderWarning = true;
                    PlayerOverlayScreen.speed = speed * 55;
                } else {
                    PlayerOverlayScreen.disableAllVehicleOverlays();
                }
            }
        } else {
            PlayerOverlayScreen.disableAllVehicleOverlays();
        }
    }
}
