package earth.terrarium.ad_astra.util.forge;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public record AdAstraTeleporter(TeleportTarget target) implements ITeleporter {

    @Override
    public @Nullable TeleportTarget getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, TeleportTarget> defaultPortalInfo) {
        return target;
    }

    @Override
    public boolean isVanilla() {
        return false;
    }

    @Override
    public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
        return false;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }
}