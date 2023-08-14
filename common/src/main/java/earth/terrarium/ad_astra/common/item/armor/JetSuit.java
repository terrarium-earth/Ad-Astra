package earth.terrarium.ad_astra.common.item.armor;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import dev.architectury.injectables.annotations.PlatformOnly;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.PlayerOverlayScreen;
import earth.terrarium.ad_astra.common.config.SpaceSuitConfig;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.util.ModKeyBindings;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.EnergyItem;
import earth.terrarium.botarium.api.energy.ItemEnergyContainer;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class JetSuit extends NetheriteSpaceSuit implements EnergyItem {

    public static enum FlyingType {
        NONE, UPWARD, FALL, HOVER
    }

    public JetSuit(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    public CompoundTag getCapabilityTag(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("JetSuit");
        return tag != null ? tag : new CompoundTag();
    }

    public CompoundTag getOrCreateCapabilityTag(ItemStack stack) {
        return stack.getOrCreateTagElement("JetSuit");
    }

    public boolean isPowerEnabled(ItemStack stack) {
        CompoundTag tag = this.getCapabilityTag(stack);
        return !tag.contains("PowerEnabled") || tag.getBoolean("PowerEnabled");
    }

    public void setPowerEnabled(ItemStack stack, boolean powerEnabled) {
        this.getOrCreateCapabilityTag(stack).putBoolean("PowerEnabled", powerEnabled);
    }

    public boolean isHoverEnabled(ItemStack stack) {
        return this.getCapabilityTag(stack).getBoolean("HoverEnabled");
    }

    public void setHoverEnabled(ItemStack stack, boolean hoverEnabled) {
        this.getOrCreateCapabilityTag(stack).putBoolean("HoverEnabled", hoverEnabled);
    }

    @NotNull
    public FlyingType getFlyingType(ItemStack stack) {
        FlyingType[] values = FlyingType.values();
        int ordinal = this.getCapabilityTag(stack).getInt("FlyingType");
        return (0 <= ordinal && ordinal < values.length) ? values[ordinal] : FlyingType.NONE;
    }

    public void setFlyingType(ItemStack stack, @NotNull FlyingType flyingType) {
        this.getOrCreateCapabilityTag(stack).putInt("FlyingType", flyingType.ordinal());
    }

    public void spawnParticles(Level level, LivingEntity entity, ItemStack stack, HumanoidModel<LivingEntity> model) {
        if (!SpaceSuitConfig.spawnJetSuitParticles || this.getFlyingType(stack) == FlyingType.NONE)
            return;

        spawnParticles(level, entity, model.rightArm.xRot + 0.05, entity.isFallFlying() ? 0.0 : 0.8, -0.45);
        spawnParticles(level, entity, model.leftArm.xRot + 0.05, entity.isFallFlying() ? 0.0 : 0.8, 0.45);

        spawnParticles(level, entity, model.rightLeg.xRot + 0.05, entity.isFallFlying() ? 0.1 : 0.0, -0.1);
        spawnParticles(level, entity, model.leftLeg.xRot + 0.05, entity.isFallFlying() ? 0.1 : 0.0, 0.1);
    }

    // Spawns particles at the limbs of the player
    private void spawnParticles(Level level, LivingEntity entity, double pitch, double yOffset, double zOffset) {
        double yaw = entity.yBodyRot;
        double xRotator = Math.cos(yaw * Math.PI / 180.0) * zOffset;
        double zRotator = Math.sin(yaw * Math.PI / 180.0) * zOffset;
        double xRotator1 = Math.cos((yaw - 90) * Math.PI / 180.0) * pitch;
        double zRotator1 = Math.sin((yaw - 90) * Math.PI / 180.0) * pitch;

        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, entity.getX() + xRotator + xRotator1, entity.getY() + yOffset, entity.getZ() + zRotator1 + zRotator, 0.0, -0.1, 0.0);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (ItemStack stack : entity.getArmorSlots()) {
            if (!(stack.getItem() instanceof JetSuit)) {
                return false;
            }
        }
        return true;
    }

    public static void updateBatteryOverlay(ItemStack suit) {
        var energy = EnergyHooks.getItemEnergyManager(suit);
        PlayerOverlayScreen.batteryRatio = energy.getStoredEnergy() / (double) energy.getCapacity();
    }

    @Override
    public long getTankSize() {
        return SpaceSuitConfig.jetSuitTankSize;
    }

    // Display status, energy
    @Override
    public void appendHoverText(ItemStack stack, Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        if (stack.is(ModItems.JET_SUIT.get())) {
            boolean powerEnabled = this.isPowerEnabled(stack);
            boolean hoverEnabled = this.isHoverEnabled(stack);
            tooltip.add(Component.translatable("tooltip.ad_astra.jet_suit.power", Component.translatable("gui.ad_astra.text." + (powerEnabled ? "enabled" : "disabled")).withStyle(powerEnabled ? ChatFormatting.GREEN : ChatFormatting.RED)));
            tooltip.add(Component.translatable("tooltip.ad_astra.jet_suit.hover", Component.translatable("gui.ad_astra.text." + (hoverEnabled ? "enabled" : "disabled")).withStyle(hoverEnabled ? ChatFormatting.GREEN : ChatFormatting.RED)));
        }
        super.appendHoverText(stack, level, tooltip, context);
        if (stack.is(ModItems.JET_SUIT.get())) {
            long energy = EnergyHooks.getItemEnergyManager(stack).getStoredEnergy();
            tooltip.add(Component.translatable("gauge_text.ad_astra.storage", energy, SpaceSuitConfig.jetSuitMaxEnergy).setStyle(Style.EMPTY.withColor(energy > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
        }
    }

    public void updateFlying(Player player, ItemStack stack) {
        FlyingType prevFlyingType = this.getFlyingType(stack);
        this.setFlyingType(stack, FlyingType.NONE);

        if (!player.isPassenger() && !player.getAbilities().flying && JetSuit.hasFullSet(player) && this.isPowerEnabled(stack)) {
            this.updateFlying(player, stack, prevFlyingType);
        }
    }

    private void updateFlying(Player player, ItemStack stack, FlyingType prevFlyingType) {
        // Don't fly the Jet Suit in creative
        ItemStackHolder stackHolder = new ItemStackHolder(stack);

        // Don't fly if the Jet Suit has no energy
        if (EnergyHooks.getItemEnergyManager(stack).getStoredEnergy() <= 0) {
            return;
        }

        double upFlySpeed = SpaceSuitConfig.jetSuitUpwardsSpeed;
        double fallFlySpeed = SpaceSuitConfig.jetSuitSpeed;
        boolean hoverEnabled = player.tickCount > 1 && this.isHoverEnabled(stack);

        if (player.isShiftKeyDown() && hoverEnabled && !player.isOnGround()) {
            if (ModKeyBindings.jumpKeyDown(player)) {
                // Shift+Space, Keep current y-pos
                this.hoverFly(player, stack, 0.0D);
            } else {
                // Shift, Hover down
                this.hoverFly(player, stack, -upFlySpeed);
            }
        } else if (ModKeyBindings.jumpKeyDown(player)) {
            if (ModKeyBindings.sprintKeyDown(player)) {
                // Ctrl+Space, Fall fly
                this.fallFly(player, stack, fallFlySpeed);
            } else {
                // Space, Fly upward
                this.flyUpward(player, stack, upFlySpeed);
            }
        } else if (hoverEnabled) {
            if (ModKeyBindings.sprintKeyDown(player)) {
                if (!player.isOnGround()) {
                    // Ctrl, Fall fly on hovering
                    this.fallFly(player, stack, fallFlySpeed);
                } else if (prevFlyingType == FlyingType.FALL) {
                    // Ctrl, Land on ground
                    // If this is not, player will swim in ground
                    this.hoverFly(player, stack, -upFlySpeed);
                }
            } else if (!player.isOnGround()) {
                // No key input, Keep current y-pos
                this.hoverFly(player, stack, 0.0D);
            }
        }

        FlyingType newFlyingType = this.getFlyingType(stack);

        if (newFlyingType != FlyingType.NONE) {
            var energy = EnergyHooks.getItemEnergyManager(stackHolder.getStack());
            long tickEnergy = SpaceSuitConfig.jetSuitEnergyPerTick;
            if (!player.isCreative()) {
                energy.extract(stackHolder, tickEnergy, false);
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            if (stackHolder.isDirty())
                player.setItemSlot(EquipmentSlot.CHEST, stackHolder.getStack());

            if (newFlyingType == FlyingType.FALL) {
                if (!player.isFallFlying()) {
                    player.startFallFlying();
                }

            } else {
                if (player.isFallFlying()) {
                    player.stopFallFlying();
                }
            }
            if (newFlyingType == FlyingType.UPWARD || newFlyingType == FlyingType.FALL) {
                ModUtils.sendUpdatePacket(serverPlayer);
            }
        }
    }

    public void flyUpward(Player player, ItemStack stack, double upSpeed) {
        player.fallDistance /= 2;
        this.setFlyingType(stack, FlyingType.UPWARD);

        player.setDeltaMovement(player.getDeltaMovement().add(0.0, upSpeed, 0.0));
        if (player.getDeltaMovement().y() > upSpeed) {
            player.setDeltaMovement(player.getDeltaMovement().x(), upSpeed, player.getDeltaMovement().z());
        }
    }

    public void fallFly(Player player, ItemStack stack, double flySpeed) {
        if (player.isOnGround()) {
            player.fallDistance /= 2;
        }
        this.setFlyingType(stack, FlyingType.FALL);
        double speed = flySpeed - (ModUtils.getEntityGravity(player) * 0.25);
        Vec3 rotationVector = player.getLookAngle().scale(speed);
        Vec3 velocity = player.getDeltaMovement();
        player.setDeltaMovement(velocity.add(rotationVector.x() * 0.1 + (rotationVector.x() * 1.5 - velocity.x()) * 0.5, rotationVector.y() * 0.1 + (rotationVector.y() * 1.5 - velocity.y()) * 0.5, rotationVector.z() * 0.1 + (rotationVector.z() * 1.5 - velocity.z()) * 0.5));
    }

    public void hoverFly(Player player, ItemStack stack, double hoverSpeed) {
        player.fallDistance = 0.0F;
        this.setFlyingType(stack, FlyingType.HOVER);
        Vec3 velocity = player.getDeltaMovement();
        player.setDeltaMovement(velocity.multiply(1.0D, 0.0D, 1.0D).add(0.0D, hoverSpeed, 0.0D));
    }

    @Override
    public ItemEnergyContainer getEnergyStorage(ItemStack itemStack) {
        if (!itemStack.is(ModItems.JET_SUIT.get())) return new ItemEnergyContainer(itemStack, 0);
        return new ItemEnergyContainer(itemStack, SpaceSuitConfig.jetSuitMaxEnergy) {
            @Override
            public long maxInsert() {
                return 512;
            }

            @Override
            public long maxExtract() {
                return 256;
            }
        };
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (!slot.equals(EquipmentSlot.CHEST)) {
            return new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armour/jet_suit/jet_suit_5.png").toString();
        }
        if (stack.getItem() instanceof JetSuit) {
            var energy = EnergyHooks.getItemEnergyManager(stack);
            return new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armour/jet_suit/jet_suit_" + (energy.getStoredEnergy() <= 0 ? 0 : ((int) Math.min((energy.getStoredEnergy() * 5 / Math.max(1, energy.getCapacity())) + 1, 5))) + ".png").toString();
        }
        return new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armour/jet_suit/jet_suit_5.png").toString();
    }

    @PlatformOnly("forge")
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level.isClientSide) {
            ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof JetSuit) {
                int nextFlightTick = flightTicks + 1;
                if (nextFlightTick % 10 == 0) {
                    if (nextFlightTick % 20 == 0) {
                        stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
                    }
                    entity.gameEvent(GameEvent.ELYTRA_GLIDE);
                }
            }
        }
        return true;
    }

    @PlatformOnly("forge")
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return this.getFlyingType(stack) == FlyingType.FALL;
    }
}