package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.HydraulicPressMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EtrionicPressBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private WrappedBlockEnergyContainer energyContainer;
    private AbstractCookingRecipe recipe;

    public EtrionicPressBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.HYDRAULIC_PRESS.get(), blockPos, blockState, 2);
    }

    @Override
    public void serverTick() {
        if (recipe != null && canCraft()) {
            if (getEnergyStorage().internalExtract(10, true) >= 10) {
                getEnergyStorage().internalExtract(10, false);
                cookTime++;
                if (cookTime >= cookTimeTotal) {
                    cookTime = 0;
                    craft();
                }
            } else {
                cookTime = 0;
            }
        } else {
            cookTime = 0;
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new HydraulicPressMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(20000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public void update() {
        if (level == null) return;
        recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.HYDRAULIC_PRESSING.get(), this, level).orElse(null);
        if (recipe == null) {
            cookTime = 0;
        } else if (!canCraft()) {
            recipe = null;
        } else {
            cookTimeTotal = Math.max(20, recipe.getCookingTime() - 40);
        }
    }

    private void craft() {
        if (recipe == null) return;
        getItem(0).shrink(1);
        ItemStack item = getItem(1);
        if (item.isEmpty()) {
            setItem(1, recipe.getResultItem().copy());
        } else {
            item.grow(recipe.getResultItem().getCount());
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            if (canCraft()) {
                return state.setAndContinue(DefaultAnimations.IDLE);
            } else {
                return state.setAndContinue(DefaultAnimations.REST);
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
