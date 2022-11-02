package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.recipes.CookingRecipe;
import earth.terrarium.ad_astra.recipes.ModRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class ProcessingMachineBlockEntity extends AbstractMachineBlockEntity {

    protected short cookTime;
    protected short cookTimeTotal;

    @Nullable
    protected Item inputItem;
    @Nullable
    protected ItemStack outputStack;

    public ProcessingMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putShort("CookTime", this.cookTime);
        nbt.putShort("CookTimeTotal", this.cookTimeTotal);
    }

    public short getCookTime() {
        return this.cookTime;
    }

    public short getCookTimeTotal() {
        return this.cookTimeTotal;
    }

    public void finishCooking() {
        if (this.outputStack != null) {
            int size = this.outputStack.getCount() + this.getItem(1).getCount();
            this.setItem(1, new ItemStack(this.outputStack.getItem(), size));
        }
        this.stopCooking();
    }

    public void stopCooking() {
        this.cookTime = 0;
        this.cookTimeTotal = 0;
        this.outputStack = null;
        this.inputItem = null;
        this.setChanged();
    }

    public <T extends CookingRecipe> CookingRecipe createRecipe(ModRecipeType<T> type, ItemStack testStack, boolean checkOutput) {
        stopCooking();

        CookingRecipe recipe = type.findFirst(this.level, f -> f.test(testStack));

        if (recipe != null) {

            // Stop if something is already in the output.
            if (checkOutput) {
                ItemStack outputSlot = this.getItem(1);
                ItemStack output = recipe.getResultItem();
                if (!outputSlot.isEmpty() && !outputSlot.getItem().equals(recipe.getResultItem().getItem()) || outputSlot.getCount() + output.getCount() > outputSlot.getMaxStackSize()) {
                    return null;
                }
            }

            this.outputStack = recipe.getResultItem();
            this.inputItem = testStack.getItem();
        }

        return recipe;
    }
}