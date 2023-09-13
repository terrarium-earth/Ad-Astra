package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.base.EnergySnapshot;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class ContainerMachineExtensionBlockEntity extends BlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, BasicContainer, WorldlyContainer {
    private WrappedBlockEnergyContainer energyContainer;
    private ContainerMachineBlockEntity baseEntity;

    public ContainerMachineExtensionBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.MACHINE_EXTENSION.get(), pos, blockState);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(this, new SimpleEnergyContainer(0) {
            @Override
            public long insertEnergy(long maxAmount, boolean simulate) {
                return getEnergyContainer().insertEnergy(maxAmount, simulate);
            }

            @Override
            public long extractEnergy(long maxAmount, boolean simulate) {
                return getEnergyContainer().extractEnergy(maxAmount, simulate);
            }

            @Override
            public long getStoredEnergy() {
                return getEnergyContainer().getStoredEnergy();
            }

            @Override
            public long getMaxCapacity() {
                return getEnergyContainer().getMaxCapacity();
            }

            @Override
            public long maxInsert() {
                return getEnergyContainer().maxInsert();
            }

            @Override
            public long maxExtract() {
                return getEnergyContainer().maxExtract();
            }

            @Override
            public boolean allowsInsertion() {
                return getEnergyContainer().allowsInsertion();
            }

            @Override
            public boolean allowsExtraction() {
                return getEnergyContainer().allowsExtraction();
            }

            @Override
            public EnergySnapshot createSnapshot() {
                return getEnergyContainer().createSnapshot();
            }

            @Override
            public void clearContent() {
                getEnergyContainer().clearContent();
            }
        });
    }

    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (baseEntity == null) baseEntity = getBaseEntity();
        if (this.energyContainer == null) energyContainer = getEnergyStorage();
        baseEntity.tickSideInteractions(pos, Predicate.not(Direction.DOWN::equals));
    }

    public WrappedBlockEnergyContainer getEnergyContainer() {
        if (baseEntity != null) return baseEntity.getEnergyStorage();
        return getBaseEntity().getEnergyStorage();
    }

    public ContainerMachineBlockEntity getBaseEntity() {
        if (baseEntity != null) return baseEntity;
        return ((ContainerMachineBlockEntity) Objects.requireNonNull(level).getBlockEntity(getBlockPos().below()));
    }

    @Override
    public NonNullList<ItemStack> items() {
        return baseEntity.items();
    }

    @Override
    public void update() {
        baseEntity.update();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return baseEntity.stillValid(player);
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return baseEntity.getSlotsForFace(side);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return baseEntity.canPlaceItemThroughFace(index, itemStack, direction);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return baseEntity.canTakeItemThroughFace(index, stack, direction);
    }
}
