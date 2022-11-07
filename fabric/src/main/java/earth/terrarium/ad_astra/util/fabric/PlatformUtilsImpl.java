package earth.terrarium.ad_astra.util.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.portal.PortalInfo;

public class PlatformUtilsImpl {
    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        return FabricDimensions.teleport(entity, level, target);
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        StrippableBlockRegistry.register(log, strippedLog);
    }

    public static int getBurnTime(ItemStack stack) {
        return FuelRegistry.INSTANCE.get(stack.getItem());
    }
}
