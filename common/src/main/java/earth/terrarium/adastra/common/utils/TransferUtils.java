package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;

import java.util.function.Predicate;

public class TransferUtils {

    public static void pushEnergyNearby(ContainerMachineBlockEntity machine, BlockPos pos, long amount, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        EnergyContainer container = EnergyContainer.of(machine, null);
        if (container == null) return;
        if (container.getStoredEnergy() == 0) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            EnergyContainer nearbyContainer = EnergyContainer.of(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            EnergyApi.moveEnergy(container, nearbyContainer, amount, false);
        }
    }

    public static void pullEnergyNearby(ContainerMachineBlockEntity machine, BlockPos pos, long amount, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        EnergyContainer container = EnergyContainer.of(machine, null);
        if (container == null) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            EnergyContainer nearbyContainer = EnergyContainer.of(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            EnergyApi.moveEnergy(nearbyContainer, container, amount, false);
        }
    }

    public static void pushFluidNearby(ContainerMachineBlockEntity machine, BlockPos pos, WrappedBlockFluidContainer container, long amount, int tank, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        if (container.isEmpty()) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            if (!FluidContainer.holdsFluid(nearbyEntity, direction)) continue;
            FluidContainer nearbyContainer = FluidContainer.of(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            FluidHolder holder = container.getFluids().get(tank);
            if (holder.isEmpty()) continue;
            if (FluidApi.moveFluid(container, nearbyContainer, FluidHolder.ofMillibuckets(holder.getFluid(), amount), false) > 0) {
                machine.sync();
            }
        }
    }

    public static void pullFluidNearby(ContainerMachineBlockEntity machine, BlockPos pos, WrappedBlockFluidContainer container, long amount, int tank, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            if (!FluidContainer.holdsFluid(nearbyEntity, direction)) continue;
            FluidContainer nearbyContainer = FluidContainer.of(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            FluidHolder holder = nearbyContainer.getFluids().get(tank);
            if (holder.isEmpty()) continue;
            if (FluidApi.moveFluid(nearbyContainer, container, FluidHolder.ofMillibuckets(holder.getFluid(), amount), false) > 0) {
                machine.sync();
            }
        }
    }

    public static void pushItemsNearby(ContainerMachineBlockEntity machine, BlockPos pos, int[] slots, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        if (machine.isEmpty()) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            Container nearbyContainer = HopperBlockEntity.getContainerAt(machine.level(), pos.relative(direction));
            if (nearbyContainer == null) continue;
            ItemUtils.push(machine, nearbyContainer, slots, direction);
        }
    }

    public static void pullItemsNearby(ContainerMachineBlockEntity machine, BlockPos pos, int[] slots, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            Container nearbyContainer = HopperBlockEntity.getContainerAt(machine.level(), pos.relative(direction));
            if (nearbyContainer == null) continue;
            ItemUtils.pull(nearbyContainer, machine, slots, direction);
        }
    }
}
