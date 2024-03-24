package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.EtrionicBlastFurnaceMenu;
import earth.terrarium.adastra.common.recipes.machines.AlloyingRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.adastra.common.utils.ItemUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class EtrionicBlastFurnaceBlockEntity extends EnergyContainerMachineBlockEntity {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY)
    );

    @Nullable
    private AlloyingRecipe alloyingRecipe;
    protected final RecipeManager.CachedCheck<Container, AlloyingRecipe> alloyingQuickCheck = RecipeManager.createCheck(ModRecipeTypes.ALLOYING.get());

    private final BlastingRecipe[] recipes = new BlastingRecipe[4];

    private Mode mode = Mode.ALLOYING;
    protected int cookTime;
    protected int cookTimeTotal;

    public EtrionicBlastFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 9);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new EtrionicBlastFurnaceMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            EnergyUtils.machineInsertOnlyEnergy(MachineConfig.STEEL)
        );
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (canFunction()) {
            recipeTick(getEnergyStorage());
        }
        if (time % 5 == 0) {
            setLit(cookTimeTotal > 0 && canFunction());
        }
    }

    @Override
    public boolean shouldUpdate() {
        for (int i = 0; i < 4; i++) {
            if (recipes[i] == null) return true;
        }
        return false;
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pullItemsNearby(this, pos, new int[]{1, 2, 3, 4}, sideConfig.get(0), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{5, 6, 7, 8}, sideConfig.get(1), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(2), filter);
    }

    public void recipeTick(WrappedBlockEnergyContainer energyStorage) {
        if (mode == Mode.ALLOYING) {
            alloyingRecipeTick(energyStorage);
            return;
        }

        boolean isCooking = false;
        boolean shouldClear = true;
        for (int i = 0; i < 4; i++) {
            if (recipes[i] == null) continue;
            if (canCraft(energyStorage, recipes[i], i + 1)) {
                shouldClear = false;
            }
            energyStorage.internalExtract(MachineConfig.etrionicBlastFurnaceBlastingEnergyPerItem, false);
            isCooking = true;
            if (cookTime < cookTimeTotal) continue;
            for (int j = 0; j < 4; j++) {
                craft(recipes[j], j, j + 1);
            }
        }
        if (isCooking) {
            cookTime++;
        }
        if (shouldClear) {
            for (int i = 0; i < 4; i++) {
                clearRecipe(i);
            }
        }
    }

    protected boolean canCraft(WrappedBlockEnergyContainer energyStorage, BlastingRecipe recipe, int slot) {
        if (recipe == null) return false;
        if (energyStorage.internalExtract(MachineConfig.etrionicBlastFurnaceBlastingEnergyPerItem, true) < MachineConfig.etrionicBlastFurnaceBlastingEnergyPerItem)
            return false;
        if (!recipe.getIngredients().get(0).test(getItem(slot))) return false;
        return ItemUtils.canAddItem(this, recipe.getResultItem(level().registryAccess()), 5, 6, 7, 8);
    }

    protected void craft(BlastingRecipe recipe, int recipeIndex, int slot) {
        if (recipe == null) return;

        getItem(slot).shrink(1);
        ItemUtils.addItem(this, recipe.getResultItem(level().registryAccess()), 5, 6, 7, 8);

        cookTime = 0;
        if (getItem(slot).isEmpty()) clearRecipe(recipeIndex);
    }

    public void alloyingRecipeTick(WrappedBlockEnergyContainer energyStorage) {
        if (alloyingRecipe == null) return;
        if (!canCraftAlloying()) {
            clearAlloyingRecipe();
            return;
        }

        energyStorage.internalExtract(alloyingRecipe.energy(), false);

        cookTime++;
        if (cookTime < cookTimeTotal) return;
        craftAlloying();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canCraftAlloying() {
        return alloyingRecipe != null && alloyingRecipe.matches(this, level());
    }

    public void craftAlloying() {
        if (alloyingRecipe == null) return;

        for (var recipe : alloyingRecipe.ingredients()) {
            for (int i = 0; i < 4; i++) {
                if (recipe.test(getItem(i + 1))) {
                    getItem(i + 1).shrink(1);
                    break;
                }
            }
        }

        ItemUtils.addItem(this, alloyingRecipe.result(), 5, 6, 7, 8);

        cookTime = 0;
        if (!canCraftAlloying()) clearAlloyingRecipe();
    }

    @Override
    public void update() {
        if (level().isClientSide()) return;

        if (mode == Mode.BLASTING) {
            for (int i = 0; i < 4; i++) {
                createRecipe(i, i + 1);
            }
        } else {
            alloyingQuickCheck.getRecipeFor(this, level()).ifPresent(r -> {
                alloyingRecipe = r.value();
                cookTimeTotal = r.value().cookingTime();
            });
        }
    }

    protected void createRecipe(int recipe, int slot) {
        if (getItem(slot).isEmpty()) return;
        level().getRecipeManager().getAllRecipesFor(RecipeType.BLASTING)
            .stream()
            .filter(r -> r.value().getIngredients().get(0).test(getItem(slot)))
            .findFirst()
            .ifPresent(r -> {
                recipes[recipe] = r.value();
                cookTimeTotal = r.value().getCookingTime();
            });
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
        mode = Mode.values()[tag.getByte("Mode")];
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
        tag.putByte("Mode", (byte) mode.ordinal());
    }

    public void clearRecipe(int recipe) {
        recipes[recipe] = null;
        cookTime = 0;
        cookTimeTotal = 0;
    }

    public void clearAlloyingRecipe() {
        alloyingRecipe = null;
        cookTime = 0;
        cookTimeTotal = 0;
    }

    public int cookTime() {
        return cookTime;
    }

    public int cookTimeTotal() {
        return cookTimeTotal;
    }

    public Mode mode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    }

    public enum Mode {
        ALLOYING,
        BLASTING;

        public Component translation() {
            return Component.translatable("tooltip.ad_astra.etrionic_blast_furnace.mode.%s".formatted(name().toLowerCase(Locale.ROOT)));
        }

        public Mode next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public Mode previous() {
            return values()[(ordinal() - 1 + values().length) % values().length];
        }
    }
}
