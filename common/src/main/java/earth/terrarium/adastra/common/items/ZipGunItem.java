package earth.terrarium.adastra.common.items;

import dev.architectury.injectables.annotations.PlatformOnly;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZipGunItem extends Item implements BotariumFluidItem<WrappedItemFluidContainer> {

    public ZipGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (FluidUtils.hasFluid(stack)) {
            player.startUsingItem(usedHand);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, entity, stack, remainingUseDuration);

        ItemStack mainHandItem = entity.getMainHandItem();
        ItemStack offhandItem = entity.getOffhandItem();

        boolean mainHandBoost = false;
        if (mainHandItem.getItem() instanceof ZipGunItem) {
            ItemStackHolder holder = new ItemStackHolder(entity.getMainHandItem());
            FluidHolder extracted = FluidUtils.extract(holder, FluidHooks.newFluidHolder(FluidUtils.getFluid(stack), FluidHooks.buckets(0.005f), null));
            if (extracted.getFluidAmount() > 0) {
                mainHandBoost = true;
            } else if ((offhandItem.getItem() instanceof ZipGunItem && !FluidUtils.hasFluid(offhandItem)) && extracted.getFluidAmount() == 0 && entity instanceof Player player) {
                player.stopUsingItem();
                return;
            }
        }

        boolean offhandBoost = false;
        if (offhandItem.getItem() instanceof ZipGunItem) {
            ItemStackHolder holder = new ItemStackHolder(offhandItem);
            FluidHolder extracted = FluidUtils.extract(holder, FluidHooks.newFluidHolder(FluidUtils.getFluid(offhandItem), FluidHooks.buckets(0.005f), null));
            if (extracted.getFluidAmount() > 0 && mainHandBoost) {
                offhandBoost = true;
            } else if ((mainHandItem.getItem() instanceof ZipGunItem && !FluidUtils.hasFluid(mainHandItem)) && extracted.getFluidAmount() == 0 && entity instanceof Player player) {
                player.stopUsingItem();
                return;
            }
        }

        double maxSpeed = 0.5;
        double particleSpeed = 1.5;
        double propelForce = 0.2;
        double propelYForce = 0.2;
        if (PlanetApi.API.isSpace(level)) {
            propelForce *= 1.5;
            propelYForce *= 3.0;
            maxSpeed *= 2.0;
        } else {
            propelYForce *= 0.2;
        }

        if (offhandBoost) {
            propelForce *= 1.5;
            propelYForce *= 1.7;
            maxSpeed *= 2.0;
            particleSpeed *= 1.5;
            entity.fallDistance *= 0.9f;
        }

        var lookAngle = entity.getLookAngle();
        if (entity.getDeltaMovement().length() < maxSpeed) {
            var propelRot = lookAngle.multiply(propelForce, propelYForce, propelForce);
            entity.addDeltaMovement(propelRot);
        }

        if (level.random.nextInt(offhandBoost ? 1 : 3) == 0) {
            level.addParticle(
                ParticleTypes.SNOWFLAKE,
                entity.getX(),
                entity.getY() + 1.0,
                entity.getZ(),
                lookAngle.x * particleSpeed + level.random.nextGaussian() * 0.03,
                lookAngle.y * particleSpeed + level.random.nextGaussian() * 0.03,
                lookAngle.z * particleSpeed + level.random.nextGaussian() * 0.03
            );
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72_000;
    }

    @SuppressWarnings("deprecation")
    @Override
    public WrappedItemFluidContainer getFluidContainer(ItemStack holder) {
        return new WrappedItemFluidContainer(
            holder,
            new SimpleFluidContainer(
                FluidHooks.buckets(10),
                1,
                (t, f) -> f.getFluid().is(ModFluidTags.ZIP_GUN_PROPELLANTS)));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(ComponentUtils.getFluidComponent(
            FluidUtils.getTank(stack),
            FluidUtils.getTankCapacity(stack),
            ModFluids.HYDROGEN.get()));
        ComponentUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.ZIP_GUN_INFO);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return FluidUtils.hasFluid(stack);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        var fluidContainer = getFluidContainer(stack);
        return (int) (((double) fluidContainer.getFluids().get(0).getFluidAmount() / fluidContainer.getTankCapacity(0)) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return ClientFluidHooks.getFluidColor(FluidUtils.getTank(stack));
    }

    // Fabric disabling of nbt change animation
    @SuppressWarnings("unused")
    @PlatformOnly(PlatformOnly.FABRIC)
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    // Forge disabling of nbt change animation
    @SuppressWarnings("unused")
    @PlatformOnly(PlatformOnly.FORGE)
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
