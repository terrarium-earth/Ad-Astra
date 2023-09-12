package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;

import java.util.Objects;
import java.util.function.Predicate;

public class TransferUtils {

    public static void pushEnergyNearby(BlockEntity entity, long amount, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        Level level = Objects.requireNonNull(entity.getLevel());
        EnergyContainer container = EnergyApi.getBlockEnergyContainer(entity, null);
        if (container == null) return;
        if (container.getStoredEnergy() < amount) return;

        int toDistribute = sideConfig.sides().entrySet().stream()
            .filter(e -> e.getValue().canPush())
            .filter(e -> filter.test(e.getKey()))
            .filter(e -> {
                Direction direction = ModUtils.relative(entity, e.getKey());
                BlockEntity nearbyEntity = level.getBlockEntity(entity.getBlockPos().relative(direction));
                if (nearbyEntity == null) return false;
                return EnergyApi.isEnergyBlock(nearbyEntity, direction);
            })
            .mapToInt(entry -> 1)
            .sum();
        if (toDistribute == 0) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(entity, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = level.getBlockEntity(entity.getBlockPos().relative(direction));
            if (nearbyEntity == null) continue;
            EnergyContainer nearbyContainer = EnergyApi.getBlockEnergyContainer(nearbyEntity, direction);
            if (nearbyContainer == null) continue;
            EnergyApi.moveEnergy(container, nearbyContainer, amount / toDistribute, false);
        }
    }

    public static void pullEnergyNearby(BlockEntity entity, long amount, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        Level level = Objects.requireNonNull(entity.getLevel());
        EnergyContainer container = EnergyApi.getBlockEnergyContainer(entity, null);
        if (container == null) return;

        int toDistribute = sideConfig.sides().entrySet().stream()
            .filter(e -> e.getValue().canPull())
            .filter(e -> filter.test(e.getKey()))
            .filter(e -> {
                Direction direction = ModUtils.relative(entity, e.getKey());
                BlockEntity nearbyEntity = level.getBlockEntity(entity.getBlockPos().relative(direction));
                if (nearbyEntity == null) return false;
                EnergyContainer energyContainer = EnergyApi.getBlockEnergyContainer(nearbyEntity, direction);
                return energyContainer != null && energyContainer.getStoredEnergy() > 0;
            })
            .mapToInt(entry -> 1)
            .sum();
        if (toDistribute == 0) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(entity, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = level.getBlockEntity(entity.getBlockPos().relative(direction));
            if (nearbyEntity == null) continue;
            EnergyContainer nearbyContainer = EnergyApi.getBlockEnergyContainer(nearbyEntity, direction);
            if (nearbyContainer == null) continue;
            EnergyApi.moveEnergy(nearbyContainer, container, amount / toDistribute, false);
        }
    }

    public static void pushFluidNearby(BlockEntity entity, WrappedBlockFluidContainer container, long amount, int tank, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        Level level = Objects.requireNonNull(entity.getLevel());
        if (container == null) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(entity, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = level.getBlockEntity(entity.getBlockPos().relative(direction));
            if (nearbyEntity == null) continue;
            FluidContainer nearbyContainer = FluidApi.getBlockFluidContainer(nearbyEntity, direction);
            if (nearbyContainer == null) continue;
            FluidHolder holder = container.getFluids().get(tank);
            FluidApi.moveFluid(container, nearbyContainer, FluidHooks.newFluidHolder(holder.getFluid(), amount, null), false);
        }
    }

    public static void pullFluidNearby(BlockEntity entity, WrappedBlockFluidContainer container, long amount, int tank, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        Level level = Objects.requireNonNull(entity.getLevel());
        if (container == null) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(entity, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = level.getBlockEntity(entity.getBlockPos().relative(direction));
            if (nearbyEntity == null) continue;
            FluidContainer nearbyContainer = FluidApi.getBlockFluidContainer(nearbyEntity, direction);
            if (nearbyContainer == null) continue;
            FluidHolder holder = nearbyContainer.getFluids().get(tank);
            FluidApi.moveFluid(nearbyContainer, container, FluidHooks.newFluidHolder(holder.getFluid(), amount, null), false);
        }
    }

    public static <T extends BlockEntity & Container> void pushItemsNearby(T entity, int[] slots, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        pushItemsNearby(entity, entity, slots, sideConfig, filter);
    }

    public static void pushItemsNearby(BlockEntity entity, Container container, int[] slots, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        Level level = Objects.requireNonNull(entity.getLevel());
        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(entity, entry.getKey());
            if (!filter.test(direction)) continue;
            Container nearbyContainer = HopperBlockEntity.getContainerAt(level, entity.getBlockPos().relative(direction));
            if (nearbyContainer == null) continue;
            ItemUtils.push(container, nearbyContainer, slots, direction);
        }
    }

    public static <T extends BlockEntity & Container> void pullItemsNearby(T entity, int[] slots, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        pullItemsNearby(entity, entity, slots, sideConfig, filter);
    }

    public static void pullItemsNearby(BlockEntity entity, Container container, int[] slots, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        Level level = Objects.requireNonNull(entity.getLevel());
        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(entity, entry.getKey());
            if (!filter.test(direction)) continue;
            Container nearbyContainer = HopperBlockEntity.getContainerAt(level, entity.getBlockPos().relative(direction));
            if (nearbyContainer == null) continue;
            ItemUtils.pull(nearbyContainer, container, slots, direction);
        }
    }
}

