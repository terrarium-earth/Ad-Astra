package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class RecipeMachineBlockEntity<T extends Recipe<RecipeInput>> extends EnergyContainerMachineBlockEntity {

    @Nullable
    protected T recipe;
    protected int cookTime;
    protected int cookTimeTotal;
    protected final RecipeManager.CachedCheck<RecipeInput, T> quickCheck;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> RecipeMachineBlockEntity.this.cookTime;
                case 1 -> RecipeMachineBlockEntity.this.cookTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    RecipeMachineBlockEntity.this.cookTime = value;
                    break;
                case 1:
                    RecipeMachineBlockEntity.this.cookTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public RecipeMachineBlockEntity(BlockPos pos, BlockState state, int containerSize, Supplier<RecipeType<T>> recipeType) {
        super(pos, state, containerSize);
        this.quickCheck = RecipeManager.createCheck(recipeType.get());
    }

    @Override
    public void internalServerTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        super.internalServerTick(level, time, state, pos);
        if (recipe != null && canFunction()) {
            recipeTick(level, getEnergyStorage());
        }
        if (time % 5 == 0 && shouldAutomaticallyUpdateLitState()) {
            setLit(cookTimeTotal > 0 && recipe != null && canFunction());
        }
    }

    public boolean shouldAutomaticallyUpdateLitState() {
        return true;
    }

    @Override
    public boolean shouldUpdate() {
        return recipe == null;
    }

    public abstract void recipeTick(ServerLevel level, ValueStorage energyStorage);

    public boolean canCraft() {
        return recipe != null && recipe.matches(new ContainerRecipeWrapper(this), level());
    }

    public abstract void craft();

    public void updateSlots() {}

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
    }

    public void clearRecipe() {
        recipe = null;
        cookTime = 0;
        cookTimeTotal = 0;
    }

    public int cookTime() {
        return cookTime;
    }

    public int cookTimeTotal() {
        return cookTimeTotal;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return super.getItem(slot);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }
}
