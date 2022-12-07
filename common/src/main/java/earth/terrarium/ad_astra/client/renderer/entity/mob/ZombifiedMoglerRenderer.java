package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.MoglerModel;
import earth.terrarium.ad_astra.entity.mob.ZombifiedMogler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ZombifiedMoglerRenderer extends MobRenderer<ZombifiedMogler, MoglerModel<ZombifiedMogler>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/zombified_mogler.png");

    public ZombifiedMoglerRenderer(EntityRendererProvider.Context context) {
        super(context, new MoglerModel<>(context.bakeLayer(MoglerModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ZombifiedMogler entity) {
        return TEXTURE;
    }
}
