package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.PygroBruteModel;
import earth.terrarium.ad_astra.common.entity.mob.PygroBrute;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class PygroBruteRenderer extends MobRenderer<PygroBrute, PygroBruteModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/pygro_brute.png");

    public PygroBruteRenderer(EntityRendererProvider.Context context) {
        super(context, new PygroBruteModel(context.bakeLayer(PygroBruteModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)), new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(PygroBrute mobEntity) {
        return TEXTURE;
    }
}
