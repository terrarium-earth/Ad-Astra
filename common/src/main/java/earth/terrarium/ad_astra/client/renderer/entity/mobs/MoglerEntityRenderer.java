package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.MoglerEntityModel;
import earth.terrarium.ad_astra.entities.mobs.Mogler;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MoglerEntityRenderer extends MobRenderer<Mogler, MoglerEntityModel<Mogler>> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/mogler.png");

    public MoglerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MoglerEntityModel<>(context.bakeLayer(MoglerEntityModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mogler entity) {
        return TEXTURE;
    }

    @Override
    protected boolean isShaking(Mogler entity) {
        return super.isShaking(entity) || entity.isConverting();
    }
}
