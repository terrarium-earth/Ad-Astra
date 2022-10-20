package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import earth.terrarium.ad_astra.util.ModIdentifier;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.MerchantEntity;

public class LunarianEntityModel<T extends MerchantEntity> extends VillagerResemblingModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("lunarian"), "main");

	public LunarianEntityModel(ModelPart root) {
		super(root);
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 19).mirrored().cuboid(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(0, 0).mirrored().cuboid(-4.5f, -18.0f, -4.5f, 9.0f, 10.0f, 9.0f, new Dilation(0.0f)).mirrored(false).uv(80, 18).mirrored().cuboid(-8.0f, -14.0f, -8.0f, 16.0f, 0.0f, 16.0f, new Dilation(0.0f)).mirrored(false).uv(36, 0).cuboid(-4.5f, -18.0f, -4.5f, 9.0f, 10.0f, 9.0f, new Dilation(0.5f)).uv(32, 19).cuboid(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new Dilation(0.5f)).uv(0, 20).mirrored().cuboid(-1.0f, -3.0f, -6.0f, 2.0f, 4.0f, 2.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

		ModelPartData hat = head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);
		hat.addChild(EntityModelPartNames.HAT_RIM, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(100, 0).mirrored().cuboid(0.0f, -12.0f, -5.0f, 8.0f, 12.0f, 6.0f, new Dilation(0.0f)).mirrored(false).uv(0, 36).mirrored().cuboid(0.0f, -12.0f, -5.0f, 8.0f, 19.0f, 6.0f, new Dilation(0.5f)).mirrored(false).uv(28, 36).mirrored().cuboid(0.0f, -12.0f, -5.0f, 8.0f, 19.0f, 6.0f, new Dilation(0.7f)).mirrored(false), ModelTransform.pivot(-4.0f, 12.0f, 2.0f));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 81).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(16, 81).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.5f)).mirrored(false), ModelTransform.pivot(2.0f, 12.0f, 0.0f));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 81).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).uv(16, 81).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.5f)), ModelTransform.pivot(-2.0f, 12.0f, 0.0f));

		ModelPartData arms = modelPartData.addChild("arms", ModelPartBuilder.create().uv(0, 73).mirrored().cuboid(-4.0f, 2.1434f, -1.7952f, 8.0f, 4.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(0, 61).mirrored().cuboid(4.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(0, 61).cuboid(-8.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new Dilation(0.0f)).uv(16, 61).mirrored().cuboid(4.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new Dilation(0.5f)).mirrored(false).uv(16, 61).cuboid(-8.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new Dilation(0.5f)), ModelTransform.of(0.0f, 2.0f, 0.0f, -0.9599f, 0.0f, 0.0f));
		return TexturedModelData.of(modelData, 128, 128);
	}
}
