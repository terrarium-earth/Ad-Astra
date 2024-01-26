package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.ClientPlatformUtils;
import earth.terrarium.adastra.client.dimension.ModDimensionSpecialEffects;
import earth.terrarium.adastra.common.items.armor.JetSuitItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ClientPlatformUtilsImpl {
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        return dispatcher.getModel(id);
    }

    public static void registerArmor(ResourceLocation texture, ModelLayerLocation layer, ClientPlatformUtils.ArmorFactory factory, Item... items) {
        ArmorRenderer.register((poseStack, buffer, stack, entity, slot, packedLight, original) -> {
            var root = Minecraft.getInstance().getEntityModels().bakeLayer(layer);
            var model = factory.create(root, slot, stack, original);

            if (stack.getItem() instanceof JetSuitItem suit) {
                suit.spawnParticles(entity.level(), entity, original, stack);
            }

            model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }, items);
    }

    public static void registerPlanetRenderers(Map<ResourceKey<Level>, ModDimensionSpecialEffects> renderers) {
        AdAstraClientFabric.registerDimensionEffects(renderers);
    }
}
