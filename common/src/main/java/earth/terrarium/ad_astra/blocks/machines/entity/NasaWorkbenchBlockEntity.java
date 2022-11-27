package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.recipes.NasaWorkbenchRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.menu.NasaWorkbenchMenu;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NasaWorkbenchBlockEntity extends AbstractMachineBlockEntity {

    private final List<NasaWorkbenchRecipe> acceptedInputs = new ArrayList<>();

    public NasaWorkbenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.NASA_WORKBENCH.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return new NasaWorkbenchMenu(syncId, inv, this);
    }

    @Override
    public int getInventorySize() {
        return 15;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot < 14;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    public void spawnWorkingParticles() {
        if (this.level instanceof ServerLevel serverWorld) {
            BlockPos pos = this.getBlockPos();
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.CRIT, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 10, 0.1, 0.1, 0.1, 0.1);
        }
    }

    public void spawnResultParticles() {
        if (this.level instanceof ServerLevel serverWorld) {
            BlockPos pos = this.getBlockPos();
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 100, 0.1, 0.1, 0.1, 0.7);
            this.level.playSound(null, pos, SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
    }

    public void spawnOutputAndClearInput(NasaWorkbenchRecipe recipe) {
        BlockPos pos = this.getBlockPos();
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 2.5, pos.getZ() + 0.5, recipe.getResultItem().copy());
        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().scale(0.5));
        this.level.addFreshEntity(itemEntity);
        itemEntity.setDefaultPickUpDelay();

        for (int i = 0; i < this.getItems().size() - 1 && i < recipe.getHolders().size(); i++) {
            this.getItems().get(i).shrink(recipe.getHolders().get(i).count());
        }
        this.setChanged();
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            for (ItemStack input : this.getItems()) {
                if (!input.isEmpty()) {
                    NasaWorkbenchRecipe recipe = NasaWorkbenchRecipe.findFirst(level, f -> f.test(input));
                    if (recipe != null) {
                        this.spawnWorkingParticles();
                        this.setActive(true);
                    } else {
                        this.setActive(false);
                    }
                }
            }
        }
    }
}