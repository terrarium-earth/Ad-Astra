package earth.terrarium.ad_astra.common.util.forge;

import earth.terrarium.ad_astra.common.recipe.condition.IRecipeConditionSerializer;
import earth.terrarium.ad_astra.common.recipe.condition.forge.RecipeConditionImpl;
import earth.terrarium.ad_astra.mixin.forge.AxeItemAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PlatformUtilsImpl {
    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return new CreativeModeTab(loc.getNamespace() + "." + loc.getPath()) {
            @Override
            public @NotNull ItemStack makeIcon() {
                return icon.get();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        return (T) entity.changeDimension(level, new AdAstraTeleporter(target));
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        Map<Block, Block> strippedBlocks = new HashMap<>(AxeItemAccessor.ad_astra$getStrippables());
        strippedBlocks.put(log, strippedLog);
        AxeItemAccessor.ad_astra$setStrippables(strippedBlocks);
    }

    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, properties);
    }

    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, decoratorPredicate);
    }

    public static void registerRecipeConditionSerializer(IRecipeConditionSerializer<?> recipeConditionSerializer) {
        CraftingHelper.register(new RecipeConditionImpl.Serializer(recipeConditionSerializer));
    }
}
