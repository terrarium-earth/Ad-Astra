package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.LunarianEntityModel;
import earth.terrarium.ad_astra.entities.mobs.LunarianWanderingTraderEntity;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LunarianWanderingTraderEntityRenderer extends MobRenderer<LunarianWanderingTraderEntity, LunarianEntityModel<LunarianWanderingTraderEntity>> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/lunarian/lunarian_wandering_trader.png");

    public LunarianWanderingTraderEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new LunarianEntityModel<>(context.bakeLayer(LunarianEntityModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(LunarianWanderingTraderEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(LunarianWanderingTraderEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}