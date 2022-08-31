package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.LunarianEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.LunarianEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public class LunarianEntityRenderer extends MobEntityRenderer<LunarianEntity, LunarianEntityModel<LunarianEntity>> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/entity/lunarian/lunarian.png");
	public static final Identifier FARMER_TEXTURE = new ModIdentifier("textures/entity/lunarian/farmer_lunarian.png");
	public static final Identifier FISHERMAN_TEXTURE = new ModIdentifier("textures/entity/lunarian/fisherman_lunarian.png");
	public static final Identifier SHEPHERD_TEXTURE = new ModIdentifier("textures/entity/lunarian/shepherd_lunarian.png");
	public static final Identifier FLETCHER_TEXTURE = new ModIdentifier("textures/entity/lunarian/fletcher_lunarian.png");
	public static final Identifier LIBRARIAN_TEXTURE = new ModIdentifier("textures/entity/lunarian/librarian_lunarian.png");
	public static final Identifier CARTOGRAPHER_TEXTURE = new ModIdentifier("textures/entity/lunarian/cartographer_lunarian.png");
	public static final Identifier CLERIC_TEXTURE = new ModIdentifier("textures/entity/lunarian/cleric_lunarian.png");
	public static final Identifier ARMORER_TEXTURE = new ModIdentifier("textures/entity/lunarian/armorer_lunarian.png");
	public static final Identifier WEAPONSMITH_TEXTURE = new ModIdentifier("textures/entity/lunarian/weaponsmith_lunarian.png");
	public static final Identifier TOOLSMITH_TEXTURE = new ModIdentifier("textures/entity/lunarian/toolsmith_lunarian.png");
	public static final Identifier BUTCHER_TEXTURE = new ModIdentifier("textures/entity/lunarian/butcher_lunarian.png");
	public static final Identifier LEATHERWORKER_TEXTURE = new ModIdentifier("textures/entity/lunarian/leatherworker_lunarian.png");
	public static final Identifier MASON_TEXTURE = new ModIdentifier("textures/entity/lunarian/mason_lunarian.png");

	public LunarianEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new LunarianEntityModel<>(context.getPart(LunarianEntityModel.LAYER_LOCATION)), 0.5f);
		this.addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader(), context.getHeldItemRenderer()));
		this.addFeature(new VillagerHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
	}

	@Override
	public Identifier getTexture(LunarianEntity entity) {

		VillagerProfession profession = entity.getVillagerData().getProfession();
		if (profession.equals(VillagerProfession.ARMORER)) {
			return ARMORER_TEXTURE;
		} else if (profession.equals(VillagerProfession.BUTCHER)) {
			return BUTCHER_TEXTURE;
		} else if (profession.equals(VillagerProfession.CARTOGRAPHER)) {
			return CARTOGRAPHER_TEXTURE;
		} else if (profession.equals(VillagerProfession.CLERIC)) {
			return CLERIC_TEXTURE;
		} else if (profession.equals(VillagerProfession.FARMER)) {
			return FARMER_TEXTURE;
		} else if (profession.equals(VillagerProfession.FISHERMAN)) {
			return FISHERMAN_TEXTURE;
		} else if (profession.equals(VillagerProfession.FLETCHER)) {
			return FLETCHER_TEXTURE;
		} else if (profession.equals(VillagerProfession.LEATHERWORKER)) {
			return LEATHERWORKER_TEXTURE;
		} else if (profession.equals(VillagerProfession.LIBRARIAN)) {
			return LIBRARIAN_TEXTURE;
		} else if (profession.equals(VillagerProfession.MASON)) {
			return MASON_TEXTURE;
		} else if (profession.equals(VillagerProfession.SHEPHERD)) {
			return SHEPHERD_TEXTURE;
		} else if (profession.equals(VillagerProfession.TOOLSMITH)) {
			return TOOLSMITH_TEXTURE;
		} else if (profession.equals(VillagerProfession.WEAPONSMITH)) {
			return WEAPONSMITH_TEXTURE;
		} else {
			return TEXTURE;
		}
	}

	protected void scale(LunarianEntity villagerEntity, MatrixStack matrixStack, float f) {
		float g = 0.9375f;
		if (villagerEntity.isBaby()) {
			g *= 0.5f;
			this.shadowRadius = 0.25f;
		} else {
			this.shadowRadius = 0.5f;
		}
		matrixStack.scale(g, g, g);
	}
}
