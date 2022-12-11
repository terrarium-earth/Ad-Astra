package earth.terrarium.ad_astra.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.LunarianModel;
import earth.terrarium.ad_astra.common.entity.mob.LunarianWanderingTrader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class LunarianWanderingTraderRenderer extends MobRenderer<LunarianWanderingTrader, LunarianModel<LunarianWanderingTrader>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/lunarian/lunarian_wandering_trader.png");

    public LunarianWanderingTraderRenderer(EntityRendererProvider.Context context) {
        super(context, new LunarianModel<>(context.bakeLayer(LunarianModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(LunarianWanderingTrader entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(LunarianWanderingTrader entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}