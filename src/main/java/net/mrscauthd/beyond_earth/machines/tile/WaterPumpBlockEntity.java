package net.mrscauthd.beyond_earth.machines.tile;

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInnet;
import net.mrscauthd.beyond_earth.capability.FluidHandlerWrapper;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gui.screens.waterpump.WaterPumpGui;
import net.mrscauthd.beyond_earth.machines.WaterPump;

public class WaterPumpBlockEntity extends AbstractMachineBlockEntity {
	public static final int TRANSFER_PER_TICK = 10;
	
    public WaterPumpBlockEntity(BlockPos pos, BlockState state) {
        super(ModInnet.WATER_PUMP.get(), pos, state);
    }

    public static final ResourceLocation WATER_TANK = new ResourceLocation(BeyondEarthMod.MODID, "water_tank");
    public static final int TANK_CAPACITY = 6000;
    public double WATER_TIMER = 0;
    private FluidTank waterTank;

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new WaterPumpGui.GuiContainer(id, inventory, this);
    }

    @Override
    protected void tickProcessing() {
    	
        BlockPos pos = this.getBlockPos();
		BlockPos pickupPos = new BlockPos(pos.getX(),pos.getY() - 1, pos.getZ());

        if (this.level.getFluidState(pickupPos) == Fluids.WATER.getSource(false)) {

            if (hasSpaceInWaterTank(this.getWaterTank().getFluid().getAmount())) {

                if (this.consumePowerForOperation() != null) {

                    WATER_TIMER = WATER_TIMER + 1;

                    if (WATER_TIMER > 10) {

                        ((BucketPickup) this.level.getBlockState(pickupPos).getBlock()).pickupBlock(this.level, pickupPos, this.level.getBlockState(pickupPos));

                        this.getWaterTank().fill(new FluidStack(Fluids.WATER,1000), IFluidHandler.FluidAction.EXECUTE);

                        WATER_TIMER = 0;
                    }
                }
            }

            if (this.getWaterTank().getFluid().getAmount() > 1) {
                BlockEntity ejectBlockEntity = level.getBlockEntity(new BlockPos(pos.getX(),pos.getY() + 1, pos.getZ()));

                if (ejectBlockEntity != null) {

                	IFluidHandler fluidHandler = ejectBlockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.DOWN).orElse(null);
                	
                	if (fluidHandler != null) {
                		int transferPerTick = this.getTransferPerTick();
                		
                		if (FluidUtil.tryFluidTransfer(fluidHandler, this.waterTank, transferPerTick, false).getAmount() == transferPerTick)
                		{
                			FluidUtil.tryFluidTransfer(fluidHandler, this.waterTank, transferPerTick, true);
                		}
                	}
                }
            }
        }
    }
    
    public int getTransferPerTick() {
		return TRANSFER_PER_TICK;
	}

    public boolean hasSpaceInWaterTank(int water) {
        return hasSpaceIn(water, this.getWaterTank().getFluid());
    }

    public boolean hasSpaceIn(int water, FluidStack storage) {
        return water < TANK_CAPACITY - 999;
    }

    @Override
    public <T> LazyOptional<T> getCapabilityFluidHandler(Capability<T> capability, Direction facing) {
        if (facing == null || facing == Direction.UP) {
            return super.getCapabilityFluidHandler(capability, facing).lazyMap(handler -> new FluidHandlerWrapper((IFluidHandler) handler, true, false)).cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, Direction facing) {
        BlockState blockState = this.getBlockState();

        if (blockState.hasProperty(WaterPump.FACING)) {
            Direction blockDirection = blockState.getValue(WaterPump.FACING);

            if (facing == null || facing == blockDirection) {
                return super.getCapabilityEnergy(capability, facing);
            }
        }

        return LazyOptional.empty();
    }

    @Override
    protected void createPowerSystems(PowerSystemRegistry map) {
        super.createPowerSystems(map);

        map.put(new PowerSystemEnergyCommon(this) {
            @Override
            public int getBasePowerForOperation() {
                return WaterPumpBlockEntity.this.getBasePowerForOperation();
            }
        });
    }

    @Override
    protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
        super.createEnergyStorages(registry);
        registry.put(this.createEnergyStorageCommon());
    }

    public int getBasePowerForOperation() {
        return 1;
    }

    @Override
    protected void createFluidHandlers(NamedComponentRegistry<IFluidHandler> registry) {
        super.createFluidHandlers(registry);
        this.waterTank = (FluidTank) registry.computeIfAbsent(this.getTankName(), k -> this.creatTank(k));
    }

    protected int getInitialTankCapacity(ResourceLocation name) {
        return TANK_CAPACITY;
    }

    protected Predicate<FluidStack> getInitialTankValidator(ResourceLocation name) {
        Fluid fluid = this.getTankFluid(name);
        return fluid != null ? fs -> FluidUtil2.isSame(fs, fluid) : null;
    }

    protected Fluid getTankFluid(ResourceLocation name) {
        if (name.equals(this.getTankName())) {
            return Fluids.WATER;
        } else {
            return null;
        }
    }

    protected FluidTank creatTank(ResourceLocation name) {
        return new FluidTank(this.getInitialTankCapacity(name), this.getInitialTankValidator(name)) {
            @Override
            protected void onContentsChanged() {
                super.onContentsChanged();
                WaterPumpBlockEntity.this.setChanged();
            }
        };
    }

    public ResourceLocation getTankName() {
        return WATER_TANK;
    }

    public FluidTank getWaterTank() {
        return this.waterTank;
    }

    @Override
    public boolean hasSpaceInOutput() {
        return false;
    }

}
