package net.mrscauthd.beyond_earth.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.armormaterial.SpaceSuitMaterial;
import net.mrscauthd.beyond_earth.capability.oxygen.CapabilityOxygen;
import net.mrscauthd.beyond_earth.entity.renderer.spacesuit.SpaceSuitModel;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.capability.oxygen.OxygenUtil;
import net.mrscauthd.beyond_earth.capability.oxygen.SpaceSuitCapabilityProvider;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.item.FilledAltArmorItem;
import net.mrscauthd.beyond_earth.itemgroup.ItemGroups;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SpaceSuit {

	public static ArmorItem OXYGEN_MASK = new FilledAltArmorItem(SpaceSuitMaterial.ArmorMaterial, EquipmentSlot.HEAD, new Item.Properties().tab(ItemGroups.tab_normal)) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getBaseArmorModel(LivingEntity living, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultModel) {

					Map<String, ModelPart> map = Map.of("head", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).head,

									 "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									 "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									 "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									 "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									 "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P1 armorModel = new SpaceSuitModel.SPACE_SUIT_P1(modelPart);

					armorModel.entity = living;

					return armorModel;
				}
			});
		}

		@Override
		public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
			if (p_41391_ != ItemGroups.tab_normal) {
				super.fillItemCategory(p_41391_, p_41392_);
			}
		}

		@Override
		public void itemCategoryAlt(CreativeModeTab tab, NonNullList<ItemStack> list) {
			if (this.allowdedIn(tab)) {
				list.add(new ItemStack(this));
			}
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/space_suit_head.png";
		}
	};

	public static ArmorItem SPACE_SUIT =  new FilledAltArmorItem(SpaceSuitMaterial.ArmorMaterial, EquipmentSlot.CHEST, new Item.Properties().tab(ItemGroups.tab_normal)) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public Model getBaseArmorModel(LivingEntity living, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultModel) {

					Map<String, ModelPart> map = Map.of(
							"body", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).body,
							"right_arm", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).rightArm,
							"left_arm", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).leftArm,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P1 armorModel = new SpaceSuitModel.SPACE_SUIT_P1(modelPart);

					armorModel.entity = living;

					return armorModel;
				}
			});
		}

		@Override
		public void fillItemCategoryAlt(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
			if (this.allowdedIn(p_41391_)) {
				ItemStack full = new ItemStack(this);
				IOxygenStorage oxygenStorage = full.getCapability(CapabilityOxygen.OXYGEN).orElse(null);

				if (oxygenStorage != null) {
					oxygenStorage.setOxygenStored(oxygenStorage.getMaxOxygenStored());
					p_41392_.add(full);
				}
			}
		}

		@Override
		public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
			if (p_41391_ != ItemGroups.tab_normal) {
				super.fillItemCategory(p_41391_, p_41392_);
			}
		}

		@Override
		public void itemCategoryAlt(CreativeModeTab tab, NonNullList<ItemStack> list) {
			if (this.allowdedIn(tab)) {
				list.add(new ItemStack(this));
			}
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
			return BeyondEarthMod.MODID + ":textures/models/armor/space_suit.png";
		}

		@Override
		public void onArmorTick(ItemStack stack, Level world, Player player) {
			Methods.extractArmorOxygenUsingTimer(stack, player);
		}
	};

	public static ArmorItem SPACE_PANTS = new FilledAltArmorItem(SpaceSuitMaterial.ArmorMaterial, EquipmentSlot.LEGS, new Item.Properties().tab(ItemGroups.tab_normal)) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public Model getBaseArmorModel(LivingEntity living, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultModel) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SPACE_SUIT_P2(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION)).rightLeg,
							"left_leg", new SpaceSuitModel.SPACE_SUIT_P2(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION)).leftLeg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P2 armorModel = new SpaceSuitModel.SPACE_SUIT_P2(modelPart);

					armorModel.entity = living;

					return armorModel;
				}
			});
		}

		@Override
		public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
			if (p_41391_ != ItemGroups.tab_normal) {
				super.fillItemCategory(p_41391_, p_41392_);
			}
		}

		@Override
		public void itemCategoryAlt(CreativeModeTab tab, NonNullList<ItemStack> list) {
			if (this.allowdedIn(tab)) {
				list.add(new ItemStack(this));
			}
		}

		@OnlyIn(Dist.CLIENT)
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/space_suit_legs.png";
		}
	};

	public static ArmorItem SPACE_BOOTS = new FilledAltArmorItem(SpaceSuitMaterial.ArmorMaterial, EquipmentSlot.FEET, new Item.Properties().tab(ItemGroups.tab_normal)) {
		@Override
		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {

				@Override
				@OnlyIn(Dist.CLIENT)
				public Model getBaseArmorModel(LivingEntity living, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultModel) {

					Map<String, ModelPart> map = Map.of(
							"right_leg", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).rightLeg,
							"left_leg", new SpaceSuitModel.SPACE_SUIT_P1(Minecraft.getInstance().getEntityModels().bakeLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION)).leftLeg,

							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())

					);

					ModelPart modelPart = new ModelPart(Collections.emptyList(), map);
					SpaceSuitModel.SPACE_SUIT_P1 armorModel = new SpaceSuitModel.SPACE_SUIT_P1(modelPart);

					armorModel.entity = living;

					return armorModel;
				}
			});
		}

		@Override
		public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
			if (p_41391_ != ItemGroups.tab_normal) {
				super.fillItemCategory(p_41391_, p_41392_);
			}
		}

		@Override
		public void itemCategoryAlt(CreativeModeTab tab, NonNullList<ItemStack> list) {
			if (this.allowdedIn(tab)) {
				list.add(new ItemStack(this));
			}
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return BeyondEarthMod.MODID + ":textures/models/armor/space_suit.png";
		}
	};
}