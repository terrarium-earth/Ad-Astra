package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.BasicContainer;
import earth.terrarium.ad_astra.common.block.machine.MachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.EtrionicBlastFurnaceMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
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
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public class EtrionicBlastFurnaceBlockEntity extends MachineBlockEntity implements EnergyAttachment.Block, BasicContainer, ExtraDataMenuProvider {
    private WrappedBlockEnergyContainer energyContainer;
    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    private AbstractCookingRecipe recipe;
    private int cookTime;
    private int cookTimeTotal;

    public EtrionicBlastFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.ETRIONIC_BLAST_FURNACE.get(), blockPos, blockState);
    }

    @Override
    public void serverTick() {
        if (this.recipe != null && this.canCraft()) {
            if (this.getEnergyStorage().internalExtract(10, true) > 0) { // Check energy // TODO
                this.getEnergyStorage().internalExtract(10, false);
                this.cookTime++;
                if (this.cookTime >= cookTimeTotal) {
                    this.cookTime = 0;
                    this.craft();
                }
            } else {
                this.cookTime = 0;
            }
        } else {
            this.cookTime = 0;
        }
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
        return new EtrionicBlastFurnaceMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(100000)) : this.energyContainer; // TODO: Configurable capacity
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void update() {
        if (level == null) return;
        this.recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.ETRIONIC_BLASTING.get(), this, level).orElse(null);
        if (this.recipe == null) {
            this.recipe = level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, this, level).orElse(null);
        }
        if (this.recipe == null) {
            this.cookTime = 0;
        } else if (!canCraft()) {
            this.recipe = null;
        } else {
            this.cookTimeTotal = Math.max(20, this.recipe.getCookingTime() - 40);
        }
    }

    private void craft() {
        if (recipe == null) return;
        getItem(0).shrink(1);
        ItemStack item = getItem(1);
        if (item.isEmpty()) {
            setItem(1, this.recipe.getResultItem().copy());
        } else {
            item.grow(this.recipe.getResultItem().getCount());
        }
        recipe = null;
        update();
    }

    private boolean canCraft() {
        if (recipe != null) {
            ItemStack output = getItem(1);
            return output.isEmpty() || (ItemStack.isSameItemSameTags(output, recipe.getResultItem()) && recipe.getResultItem().getCount() + output.getCount() <= output.getMaxStackSize());
        }
        return false;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
