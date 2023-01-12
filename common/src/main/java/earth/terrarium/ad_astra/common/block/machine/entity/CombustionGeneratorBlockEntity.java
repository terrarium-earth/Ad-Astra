package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.BasicContainer;
import earth.terrarium.ad_astra.common.block.machine.MachineBlockEntity;
import earth.terrarium.ad_astra.common.recipe.machine.CombustionRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.CombustionGeneratorMenu;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public class CombustionGeneratorBlockEntity extends MachineBlockEntity implements EnergyAttachment.Block, FluidAttachment.Block, BasicContainer, ExtraDataMenuProvider {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    private CombustionRecipe recipe;
    private int cookTime;
    private int cookTimeTotal;

    public CombustionGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.COMBUSTION_GENERATOR.get(), blockPos, blockState);
    }

    @Override
    public void serverTick() {
        if (this.recipe != null) {
            if (!getFluidContainer().isEmpty() && getFluidContainer().getFluids().get(0).getFluid().equals(recipe.ingredient().getFluid())) {
                if (this.getEnergyStorage().internalInsert(this.recipe.energy(), true) > 0) {
                    this.getEnergyStorage().internalInsert(this.recipe.energy(), false);
                    if (getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), recipe.ingredient().getFluidAmount(), null), false).getFluidAmount() > 0) {
                        this.cookTime++;
                        if (this.cookTime >= cookTimeTotal) {
                            this.cookTime = 0;
                            getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), recipe.ingredient().getFluidAmount(), null), false);
                            if (fluidContainer.getFluids().get(0).isEmpty()) {
                                this.recipe = null;
                            }
                        }
                    }
                }
            }
        } else {
            this.cookTime = 0;
        }

        EnergyHooks.distributeEnergyNearby(this, 128);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.cookTimeTotal);
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CombustionGeneratorMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new ExtractOnlyEnergyContainer(100000)) : this.energyContainer; // TODO: Configurable capacity
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return fluidContainer == null ? fluidContainer = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(i -> FluidHooks.buckets(5f), 1, (amount, fluid) -> true)) : this.fluidContainer;
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        return getFluidContainer(this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void update() {
        if (level == null) return;
        this.recipe = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COMBUSTION.get()).stream().filter(r -> r.matches(this)).findFirst().orElse(null);
        if (this.recipe == null) {
            this.cookTime = 0;
        } else {
            this.cookTimeTotal = this.recipe.cookingTime();
        }

        FluidUtils.insertItemFluidToTank(this, this, 0, 1, f -> true);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
