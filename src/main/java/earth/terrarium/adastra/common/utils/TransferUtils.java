package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;

import java.util.function.Predicate;

public class TransferUtils {

    public static void pushEnergyNearby(ContainerMachineBlockEntity machine, BlockPos pos, long amount, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        ValueStorage container = EnergyApi.BLOCK.find(machine, null);
        if (container == null) return;
        if (container.getStoredAmount() == 0) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            ValueStorage nearbyContainer = EnergyApi.BLOCK.find(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            EnergyUtils.moveEnergy(container, nearbyContainer, amount, false);
        }
    }

    public static void pullEnergyNearby(ContainerMachineBlockEntity machine, BlockPos pos, long amount, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        ValueStorage container = EnergyApi.BLOCK.find(machine, null);
        if (container == null) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            ValueStorage nearbyContainer = EnergyApi.BLOCK.find(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            EnergyUtils.moveEnergy(nearbyContainer, container, amount, false);
        }
    }

    public static void pushFluidNearby(ContainerMachineBlockEntity machine, BlockPos pos, CommonStorage<FluidResource> container, long amount, int tank, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        if (container.getContents(0).isEmpty()) return;

        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPush()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            if (!FluidApi.BLOCK.isPresent(nearbyEntity, direction)) continue;
            CommonStorage<FluidResource> nearbyContainer = FluidApi.BLOCK.find(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            if (tank >= container.size()) continue;
            ResourceStack<FluidResource> holder = container.getContents(tank);
            if (holder.isEmpty()) continue;

            if (FluidUtils.moveFluid(container, nearbyContainer, holder.withCount(FluidAmounts.toMillibuckets(holder.amount())), false) > 0) {
                machine.sync();
            }
        }
    }

    public static void pullFluidNearby(ContainerMachineBlockEntity machine, BlockPos pos, CommonStorage<FluidResource> container, long amount, int tank, ConfigurationEntry sideConfig, Predicate<Direction> filter) {
        for (var entry : sideConfig.sides().entrySet()) {
            Configuration configuration = entry.getValue();
            if (!configuration.canPull()) continue;
            Direction direction = ModUtils.relative(machine, entry.getKey());
            if (!filter.test(direction)) continue;
            BlockEntity nearbyEntity = machine.level().getBlockEntity(pos.relative(direction));
            if (nearbyEntity == null) continue;
            if (!FluidApi.BLOCK.isPresent(nearbyEntity, direction)) continue;
            CommonStorage<FluidResource> nearbyContainer = FluidApi.BLOCK.find(nearbyEntity, direction.getOpposite());
            if (nearbyContainer == null) continue;
            if (tank >= container.size()) continue;
            ResourceStack<FluidResource> holder = container.getContents(tank);
            if (holder.isEmpty()) continue;
            if (FluidUtils.moveFluid(container, nearbyContainer, holder.withCount(FluidAmounts.toMillibuckets(holder.amount())), false) > 0) {
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
