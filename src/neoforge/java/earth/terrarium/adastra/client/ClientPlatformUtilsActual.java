package earth.terrarium.adastra.client;

import earth.terrarium.adastra.client.dimension.ModDimensionSpecialEffects;
import earth.terrarium.adastra.client.neoforge.AdAstraClientNeoForge;
import earth.terrarium.adastra.client.renderers.ArmorRenderer;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.msrandom.multiplatform.annotations.Actual;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

import java.util.Map;

public class ClientPlatformUtilsActual {

    @Actual
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        return dispatcher.getModel(ModelResourceLocation.standalone(id));
    }

    @Actual
    public static void registerArmor(ResourceLocation texture, ModelLayerLocation layer, ClientPlatformUtils.ArmorFactory factory, Item... items) {
        for (Item item : items) {
            AdAstraClientNeoForge.ARMOR_RENDERERS.put(item, new ArmorRenderer(texture, layer, factory));
        }
    }

    @Actual
    public static void registerPlanetRenderers(Map<ResourceKey<Level>, ModDimensionSpecialEffects> renderers) {
        AdAstraClientNeoForge.DIMENSION_RENDERERS.clear();
        AdAstraClientNeoForge.DIMENSION_RENDERERS.putAll(renderers);
    }

    @Actual
    public static TextureAtlasSprite getFluidSprite(ResourceStack<FluidResource> stack) {
        return Minecraft.getInstance()
            .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
            .apply(IClientFluidTypeExtensions.of(stack.resource().getType()).getStillTexture());
    }

    @Actual
    public static int getFluidColor(ResourceStack<FluidResource> stack) {
        return IClientFluidTypeExtensions.of(stack.resource().getType()).getTintColor();
    }

    @Actual
    public static Component getDisplayName(ResourceStack<FluidResource> stack) {
        return stack.resource().getType().getFluidType().getDescription();
    }
}
