package net.mrscauthd.beyond_earth.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.armormaterial.NetheriteSpaceSuitMaterial;
import net.mrscauthd.beyond_earth.entity.renderer.spacesuit.SpaceSuitModel;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.capability.IOxygenStorage;
import net.mrscauthd.beyond_earth.capability.OxygenUtil;
import net.mrscauthd.beyond_earth.capability.SpaceSuitCapabilityProvider;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.itemgroup.ItemGroups;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NetheriteSpaceSuit {

	public static ArmorItem NETHERITE_OXYGEN_MASK = new ArmorItem(NetheriteSpaceSuitMaterial.ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(ItemGroups.tab_normal).fireResistant()) {

		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {

					Map<String, ModelPart> map = Map.of("head", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).Head,

							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

					HumanoidModel armorModel = new HumanoidModel(modelPart);

					armorModel.crouching = living.isCrouching();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();

					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/netherite_space_suit_head.png";
		}

	};

	public static ArmorItem NETHERITE_SPACE_SUIT = new ArmorItem(NetheriteSpaceSuitMaterial.ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(ItemGroups.tab_normal).fireResistant()) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {

					Map<String, ModelPart> map = Map.of(
							"body", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).Body,
							"left_arm", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).Left_Arm,
							"right_arm", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).Right_Arm,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

					HumanoidModel armorModel = new HumanoidModel(modelPart);

					armorModel.crouching = living.isCrouching();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();

					return armorModel;
				}
			});
		}

		@Override
		public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
			return new SpaceSuitCapabilityProvider(stack, 48000);
		}

		@Override
		public void appendHoverText(ItemStack p_41421_, @Nullable net.minecraft.world.level.Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
			super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
			IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(p_41421_);
			p_41423_.add(GaugeTextHelper.buildSpacesuitOxygenTooltip(oxygenStorage));
		}

		@OnlyIn(Dist.CLIENT)
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/netherite_space_suit.png";
		}

		@Override
		public void onArmorTick(ItemStack stack, Level world, Player player) {
			Methods.extractArmorOxygenUsingTimer(stack, player);
		}
	};

	public static ArmorItem NETHERITE_SPACE_PANTS = new ArmorItem(NetheriteSpaceSuitMaterial.ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(ItemGroups.tab_normal).fireResistant()) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SPACE_SUIT_P2(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION)).Right_Leg,
							"left_leg", new SpaceSuitModel.SPACE_SUIT_P2(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION)).Left_Leg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

					HumanoidModel armorModel = new HumanoidModel(modelPart);

					armorModel.crouching = living.isCrouching();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();

					return armorModel;
				}
			});
		}

		@OnlyIn(Dist.CLIENT)
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/netherite_space_suit_legs.png";
		}
	};

	public static ArmorItem NETHERITE_SPACE_BOOTS = new ArmorItem(NetheriteSpaceSuitMaterial.ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(ItemGroups.tab_normal).fireResistant()) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).Right_Boots,
							"left_leg", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).Left_Boots,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);
					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

					HumanoidModel armorModel = new HumanoidModel(modelPart);

					armorModel.crouching = living.isCrouching();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();

					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/netherite_space_suit.png";
		}
	};
}