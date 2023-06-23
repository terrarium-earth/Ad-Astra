package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.PygroModel;
import earth.terrarium.ad_astra.common.entity.mob.Pygro;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class PygroRenderer extends MobRenderer<Pygro, PygroModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/pygro.png");

    public PygroRenderer(EntityRendererProvider.Context context) {
        super(context, new PygroModel(context.bakeLayer(PygroModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)), new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(Pygro mobEntity) {
        return TEXTURE;
    }
}
