package net.mrscauthd.beyond_earth.entity.renderer.flag;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

@OnlyIn(Dist.CLIENT)
public class TileEntityHeadModel extends SkullModelBase {
	private final ModelPart root;
	protected final ModelPart head;

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "flag"), "main");

	public TileEntityHeadModel(ModelPart p_170945_) {
		this.root = p_170945_;
		this.head = p_170945_.getChild("head");
	}

	public static MeshDefinition createHeadModel() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(8, 8).addBox(-3.0F, -11.0F, 3.980F, 8.0F, 8.0F, 0.020F).texOffs(0, 8).addBox(-3.0F, -11.0F, 4.002F, 8.0F, 8.0F, 0.020F), PartPose.ZERO);
		return meshdefinition;
	}

	public static LayerDefinition createHumanoidHeadLayer() {
		MeshDefinition meshdefinition = createHeadModel();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.getChild("head").addOrReplaceChild("hat", CubeListBuilder.create().texOffs(40, 8).addBox(-3.0F, -11.0F, 3.970F, 8.0F, 8.0F, 0.020F).texOffs(32, 8).addBox(-3.0F, -11.0F, 4.020F, 8.0F, 8.0F, 0.020F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createMobHeadLayer() {
		MeshDefinition meshdefinition = createHeadModel();
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
		this.head.yRot = p_103812_ * ((float) Math.PI / 180F);
		this.head.xRot = p_103813_ * ((float) Math.PI / 180F);
	}

	public void renderToBuffer(PoseStack p_103815_, VertexConsumer p_103816_, int p_103817_, int p_103818_, float p_103819_, float p_103820_, float p_103821_, float p_103822_) {
		this.root.render(p_103815_, p_103816_, p_103817_, p_103818_, p_103819_, p_103820_, p_103821_, p_103822_);
	}
}