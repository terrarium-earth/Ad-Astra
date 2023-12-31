package earth.terrarium.adastra.client.renderers.entities.mobs;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.models.entities.mobs.PygroModel;
import earth.terrarium.adastra.client.models.entities.mobs.ZombifiedPygroModel;
import earth.terrarium.adastra.common.entities.mob.ZombifiedPygro;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class ZombifiedPygroRenderer extends MobRenderer<ZombifiedPygro, ZombifiedPygroModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/zombified_pygro.png");

    public ZombifiedPygroRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombifiedPygroModel(context.bakeLayer(PygroModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)), new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(ZombifiedPygro mobEntity) {
        return TEXTURE;
    }
}
