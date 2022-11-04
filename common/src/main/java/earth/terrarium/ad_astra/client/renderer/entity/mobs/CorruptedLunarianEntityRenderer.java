package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.CorruptedLunarianEntityModel;
import earth.terrarium.ad_astra.entities.mobs.CorruptedLunarian;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CorruptedLunarianEntityRenderer extends MobRenderer<CorruptedLunarian, CorruptedLunarianEntityModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/lunarian/corrupted_lunarian.png");

    public CorruptedLunarianEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new CorruptedLunarianEntityModel(context.bakeLayer(CorruptedLunarianEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CorruptedLunarian entity) {
        return TEXTURE;
    }
}
