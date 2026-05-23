package earth.terrarium.adastra.client;

import earth.terrarium.adastra.client.dimension.ModDimensionSpecialEffects;
import earth.terrarium.adastra.client.fabric.AdAstraClientFabric;
import earth.terrarium.adastra.common.items.armor.JetSuitItem;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.Map;

public class ClientPlatformUtilsActual {

    @Actual
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        return dispatcher.getModel(new ModelResourceLocation(id, "standalone"));
    }

    @Actual
    public static void registerArmor(ResourceLocation texture, ModelLayerLocation layer, ClientPlatformUtils.ArmorFactory factory, Item... items) {
        ArmorRenderer.register((poseStack, buffer, stack, entity, slot, packedLight, original) -> {
            var root = Minecraft.getInstance().getEntityModels().bakeLayer(layer);
            var model = factory.create(root, slot, stack, original);

            if (stack.getItem() instanceof JetSuitItem suit) {
                suit.spawnParticles(entity.level(), entity, original, stack);
            }

            model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY, -1);
        }, items);
    }

    @Actual
    public static void registerPlanetRenderers(Map<ResourceKey<Level>, ModDimensionSpecialEffects> renderers) {
        AdAstraClientFabric.registerDimensionEffects(renderers);
    }

    @Actual
    public static TextureAtlasSprite getFluidSprite(ResourceStack<FluidResource> stack) {
        return FluidVariantRendering.getSprite(FluidVariant.of(stack.resource().getType()));
    }

    @Actual
    public static int getFluidColor(ResourceStack<FluidResource> stack) {
        return FluidVariantRendering.getColor(FluidVariant.of(stack.resource().getType()));
    }

    @Actual
    public static Component getDisplayName(ResourceStack<FluidResource> stack) {
        return FluidVariantAttributes.getName(FluidVariant.of(stack.resource().getType()));
    }
}
