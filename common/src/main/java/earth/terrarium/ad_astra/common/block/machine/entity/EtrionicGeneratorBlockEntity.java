package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.recipe.machine.EtrionicGeneratingRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.EtrionicGeneratorMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EtrionicGeneratorBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private WrappedBlockEnergyContainer energyContainer;
    private EtrionicGeneratingRecipe recipe;

    public EtrionicGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.ETRIONIC_GENERATOR.get(), blockPos, blockState, 1);
    }

    @Override
    public void serverTick() {
        if (recipe != null) {
            if (getEnergyStorage().internalInsert(recipe.energy(), true) >= recipe.energy()) {
                getEnergyStorage().internalInsert(recipe.energy(), false);
                cookTime++;
                if (cookTime >= cookTimeTotal) {
                    cookTime = 0;
                    craft();
                }
            }
        } else {
            cookTime = 0;
        }

        EnergyHooks.distributeEnergyNearby(this, 128);
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EtrionicGeneratorMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new ExtractOnlyEnergyContainer(100000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public void update() {
        if (level == null) return;
        if (level.isClientSide) return;
        recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.ETRIONIC_GENERATING.get(), this, level).orElse(null);
        if (recipe == null) {
            cookTime = 0;
        } else if (cookTime == 0) {
            cookTimeTotal = recipe.cookingTime();
            getItem(0).shrink(1);
        }
    }

    private void craft() {
        if (recipe == null) return;
        update();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> state.setAndContinue(DefaultAnimations.IDLE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
