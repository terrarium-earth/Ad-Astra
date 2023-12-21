package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.NasaWorkbenchMenu;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class NasaWorkbenchBlockEntity extends ContainerMachineBlockEntity {
    @Nullable
    protected NasaWorkbenchRecipe recipe;

    public NasaWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 15);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new NasaWorkbenchMenu(id, inventory, this);
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) return;
        tickSideInteractions(pos, d -> true);

        for (int i = 0; i < 14; i++) {
            if (!getItem(i).isEmpty()) {
                spawnWorkingParticles(level, pos);
                break;
            }
        }

        if (recipe != null && canCraft()) {
            setItem(14, recipe.result());
        } else {
            setItem(14, ItemStack.EMPTY);
        }
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter) {
        TransferUtils.pushItemsNearby(this, pos, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, getSideConfig().get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, getSideConfig().get(0), filter);
    }

    @Override
    public void update() {
        if (level().isClientSide()) return;
        level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH.get())
            .stream()
            .filter(r -> IntStream.range(0, 14)
                .allMatch(i -> r.ingredients().get(i).test(getItem(i))))
            .findFirst()
            .ifPresent(r -> recipe = r);
    }

    public boolean canCraft() {
        if (recipe == null) return false;
        for (int i = 0; i < 14; i++) {
            if (!recipe.ingredients().get(i).test(getItem(i))) return false;
        }
        return true;
    }

    public void craft() {
        if (recipe == null) return;
        spawnResultParticles();
        Containers.dropItemStack(level(),
            getBlockPos().getX(),
            getBlockPos().getY() + 1,
            getBlockPos().getZ(),
            getItem(14).copy());

        setItem(14, ItemStack.EMPTY);
        for (int i = 0; i < 14; i++) {
            getItem(i).shrink(1);
        }
        recipe = null;
    }

    public void spawnWorkingParticles(ServerLevel level, BlockPos pos) {
        ModUtils.sendParticles(level,
            ParticleTypes.ELECTRIC_SPARK,
            pos.getX() + 0.5,
            pos.getY() + 1.5,
            pos.getZ() + 0.5,
            3,
            0.12, 0.12, 0.12,
            0.15);
    }

    public void spawnResultParticles() {
        if (level instanceof ServerLevel serverLevel) {
            var pos = getBlockPos();
            ModUtils.sendParticles(serverLevel,
                ParticleTypes.TOTEM_OF_UNDYING,
                pos.getX() + 0.5,
                pos.getY() + 1.5,
                pos.getZ() + 0.5,
                100,
                0.1, 0.1, 0.1,
                0.7);
            serverLevel.playSound(null, pos, SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return List.of(new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS));
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    }
}