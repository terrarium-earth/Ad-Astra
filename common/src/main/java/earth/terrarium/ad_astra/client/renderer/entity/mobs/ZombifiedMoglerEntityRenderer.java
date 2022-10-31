package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.MoglerEntityModel;
import earth.terrarium.ad_astra.entities.mobs.ZombifiedMoglerEntity;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ZombifiedMoglerEntityRenderer extends MobRenderer<ZombifiedMoglerEntity, MoglerEntityModel<ZombifiedMoglerEntity>> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/zombified_mogler.png");

    public ZombifiedMoglerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MoglerEntityModel<>(context.bakeLayer(MoglerEntityModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(ZombifiedMoglerEntity entity) {
        return TEXTURE;
    }
}
