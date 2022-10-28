package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.LunarianEntityModel;
import earth.terrarium.ad_astra.entities.mobs.LunarianWanderingTraderEntity;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LunarianWanderingTraderEntityRenderer extends MobEntityRenderer<LunarianWanderingTraderEntity, LunarianEntityModel<LunarianWanderingTraderEntity>> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entity/lunarian/lunarian_wandering_trader.png");

    public LunarianWanderingTraderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LunarianEntityModel<>(context.getPart(LunarianEntityModel.LAYER_LOCATION)), 0.5f);
        this.addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader(), context.getHeldItemRenderer()));
        this.addFeature(new VillagerHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(LunarianWanderingTraderEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(LunarianWanderingTraderEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}