package earth.terrarium.adastra.mixins.forge.common.entities;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import earth.terrarium.adastra.common.entities.mob.GlacianRam;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.IForgeShearable;

@Mixin(GlacianRam.class)
public abstract class GlacianRamMixin extends Animal implements IForgeShearable {

    protected GlacianRamMixin(EntityType<? extends GlacianRam> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
        GlacianRam self = (GlacianRam) (Object) this;
        return self.readyForShearing();
    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        GlacianRam self = (GlacianRam) (Object) this;
        return self.onSheared(player, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS);
    }
}