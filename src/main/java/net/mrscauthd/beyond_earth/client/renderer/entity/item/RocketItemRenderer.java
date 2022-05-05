package net.mrscauthd.beyond_earth.client.renderer.entity.item;

import dev.monarkhes.myron.api.Myron;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class RocketItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private static final Identifier MODEL = new ModIdentifier("models/misc/rocket_t1");

    @Override
    public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay) {
        
        BakedModel model = Myron.getModel(MODEL);

        if (model != null) {
            matrices.push();

            matrices.pop();
        }
    }
}
