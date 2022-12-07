package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.CorruptedLunarianModel;
import earth.terrarium.ad_astra.entity.mob.CorruptedLunarian;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class CorruptedLunarianRenderer extends MobRenderer<CorruptedLunarian, CorruptedLunarianModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/lunarian/corrupted_lunarian.png");

    public CorruptedLunarianRenderer(EntityRendererProvider.Context context) {
        super(context, new CorruptedLunarianModel(context.bakeLayer(CorruptedLunarianModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CorruptedLunarian entity) {
        return TEXTURE;
    }
}
