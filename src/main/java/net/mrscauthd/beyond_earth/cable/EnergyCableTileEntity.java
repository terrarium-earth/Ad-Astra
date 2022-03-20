package net.mrscauthd.beyond_earth.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnergyCableTileEntity extends BlockEntity implements BlockEntityTicker {

    //protected List<Block> network = new ArrayList<>();
    public static final int ENERGY_PER_TICK = 1;

    public EnergyCableTileEntity(BlockPos pos, BlockState state) {
        super(ModInit.ENERGY_CABLE.get(), pos, state);
    }

    @Override
    public void tick(Level p_155253_, BlockPos p_155254_, BlockState p_155255_, BlockEntity p_155256_) {
        if(!this.level.isClientSide) {
            if (this.level.getBlockState(this.getBlockPos()).getBlock() instanceof EnergyCable) {
                EnergyCable ec = (EnergyCable) this.level.getBlockState(this.getBlockPos()).getBlock();
                if (EnergyCable.CABLENETWORKS.get(this.getBlockPos()) != null) {
                    List<BlockPos> RECIEVE = new ArrayList<>();
                    List<BlockPos> EXTRACT = new ArrayList<>();

                    for (BlockPos p:EnergyCable.CABLENETWORKS.get(getBlockPos())) {
                        if(this.canExtract(level,p)){
                            EXTRACT.add(p);
                        }else if(this.canRecieve(level,p)){
                            RECIEVE.add(p);
                        }
                    }

                    for (BlockPos c : RECIEVE) {
                        for(BlockPos e : EnergyCable.CABLES.get(getBlockPos())){
                            BlockEntity et = this.level.getBlockEntity(e);
                            BlockEntity ct = this.level.getBlockEntity(c);

                            if(et == null||ct==null){
                                continue;
                            }

                            if(this.getEnergy(level,e) >= 1&&this.getEnergy(level,c) < this.getMaxEnergy(level,c)){

                            level.setBlock(c, Blocks.BEDROCK.defaultBlockState(), 2);
                            System.out.println("work");
                            et.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).ifPresent(capability -> capability.extractEnergy(1, true));
                            ct.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).ifPresent(capability -> capability.receiveEnergy(1, true));
                            }
                        }
                    }

                    //System.out.println("handle");
                    /*for (BlockPos pb : EnergyCable.CABLENETWORKS.get(pos)) {
                        TileEntity fr = this.world.getTileEntity(pb);
                        if (fr == null) {
                            System.out.println("contiune");
                            continue;
                        }
                        for (BlockPos bp : EnergyCable.CABLENETWORKS.get(pos)) {
                            if (bp.getCoordinatesAsString().equals(pb.getCoordinatesAsString())) {
                                continue;
                            }
                            TileEntity te = this.world.getTileEntity(bp);
                            if (te == null) {
                                System.out.println("contiune");
                                continue;
                            }
                            if (this.canRecieve(world, pb) || this.canExtract(world, bp)) {
                                if (this.getEnergy(world, bp) > 0 && this.getEnergy(world, pb) < this.getMaxEnergy(world, pb)) {
                                    te.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).ifPresent(capability -> capability.extractEnergy(1, false));
                                    fr.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).ifPresent(capability -> capability.receiveEnergy(1, false));
                                    System.out.println("send");
                                    world.getPlayers().get(0).sendMessage(ITextComponent.getTextComponentOrEmpty("send"),null);
                                }else {
                                    world.getPlayers().get(0).sendMessage(ITextComponent.getTextComponentOrEmpty("its worng"),null);
                                }
                            }
                        }
                    }*/
                }
            }
        }
    }

    public EnergyStorage ENERGY = new EnergyStorage(9000, 200, 200, 0) {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int retval = super.receiveEnergy(maxReceive, simulate);
            if (!simulate) {
                setChanged();
                level.sendBlockUpdated(getBlockPos(), level.getBlockState(getBlockPos()), level.getBlockState(getBlockPos()), 2);
            }
            return retval;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int retval = super.extractEnergy(maxExtract, simulate);
            if (!simulate) {
                setChanged();
                level.sendBlockUpdated(getBlockPos(), level.getBlockState(getBlockPos()), level.getBlockState(getBlockPos()), 2);
            }
            return retval;
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public int getMaxEnergyStored() {
            return 1000;
        }
    };

    public boolean canRecieve(Level world, BlockPos pos){
        return Optional.ofNullable(world.getBlockEntity(pos))
                .flatMap(te -> te.getCapability(CapabilityEnergy.ENERGY).resolve())
                .map(cap -> cap.canReceive())
                .orElse(false);
    }

    public boolean canExtract(Level world, BlockPos pos){
        return Optional.ofNullable(world.getBlockEntity(pos))
                .flatMap(te -> te.getCapability(CapabilityEnergy.ENERGY).resolve())
                .map(cap -> cap.canExtract())
                .orElse(false);
    }

    public int getEnergy(Level world,BlockPos pos){
        return Optional.ofNullable(world.getBlockEntity(pos))
                .flatMap(te -> te.getCapability(CapabilityEnergy.ENERGY).resolve())
                .map(cap -> cap.getEnergyStored())
                .orElse(0);
    }

    public int getMaxEnergy(Level world,BlockPos pos){
        return Optional.ofNullable(world.getBlockEntity(pos))
                .flatMap(te -> te.getCapability(CapabilityEnergy.ENERGY).resolve())
                .map(cap -> cap.getMaxEnergyStored())
                .orElse(0);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.isRemoved()) {
             if (capability == CapabilityEnergy.ENERGY) {
                LazyOptional<T> optional = this.getCapabilityEnergy(capability, facing);
                if (optional != null && optional.isPresent()) {
                    return optional;
                }
            }
        }

        return super.getCapability(capability, facing);
    }

    public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, @Nullable Direction facing) {
        IEnergyStorage energyStorage = ENERGY;

        if (energyStorage != null) {
            return LazyOptional.of(() -> energyStorage).cast();
        }

        return LazyOptional.empty();
    }
}
