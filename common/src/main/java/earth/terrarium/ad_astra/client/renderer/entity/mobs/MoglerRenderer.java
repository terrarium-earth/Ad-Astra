package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.MoglerModel;
import earth.terrarium.ad_astra.entities.mobs.Mogler;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MoglerRenderer extends MobRenderer<Mogler, MoglerModel<Mogler>> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/mogler.png");

    public MoglerRenderer(EntityRendererProvider.Context context) {
        super(context, new MoglerModel<>(context.bakeLayer(MoglerModel.LAYER_LOCATION)), 0.7f);
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
