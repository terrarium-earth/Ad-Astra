package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.CorruptedLunarianModel;
import earth.terrarium.ad_astra.entities.mobs.CorruptedLunarian;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CorruptedLunarianRenderer extends MobRenderer<CorruptedLunarian, CorruptedLunarianModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/lunarian/corrupted_lunarian.png");

    public CorruptedLunarianRenderer(EntityRendererProvider.Context context) {
        super(context, new CorruptedLunarianModel(context.bakeLayer(CorruptedLunarianModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CorruptedLunarian entity) {
        return TEXTURE;
    }
}
