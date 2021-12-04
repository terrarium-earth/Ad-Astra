package net.mrscauthd.boss_tools.armor;

import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.armormaterial.SpaceSuitArmorMaterial;
import net.mrscauthd.boss_tools.entity.renderer.spacesuit.SpaceSuitModel;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.capability.SpaceSuitCapabilityProvider;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.entity.model.BipedModel;

import java.util.List;

import ArmorItem;

public class SpaceSuit {

	public static ArmorItem OXYGEN_MASK = new ArmorItem(SpaceSuitArmorMaterial.ArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedHead = new SpaceSuitModel.SPACE_SUIT_P1().Head;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/space_suit_head.png";
		}
	};

	public static ArmorItem SPACE_SUIT =  new ArmorItem(SpaceSuitArmorMaterial.ArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedBody = new SpaceSuitModel.SPACE_SUIT_P1().Body;
			armorModel.bipedLeftArm = new SpaceSuitModel.SPACE_SUIT_P1().ArmLeft;
			armorModel.bipedRightArm = new SpaceSuitModel.SPACE_SUIT_P1().ArmRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			return new SpaceSuitCapabilityProvider(stack, 48000);
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);
			list.add(GaugeTextHelper.buildSpacesuitOxygenTooltip(oxygenStorage));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/space_suit.png";
		}

		private double OXYGEN_TIMER = 0;

		@Override
		public void onArmorTick(ItemStack itemstack, World world, PlayerEntity player) {
			Methodes.extractArmorOxygenUsingTimer(itemstack, player);
		}

	};

	public static ArmorItem SPACE_PANTS = new ArmorItem(SpaceSuitArmorMaterial.ArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new SpaceSuitModel.SPACE_SUIT_P2().LegLeft;
			armorModel.bipedRightLeg = new SpaceSuitModel.SPACE_SUIT_P2().LegRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/space_suit_legs.png";
		}
	};

	public static ArmorItem SPACE_BOOTS = new ArmorItem(SpaceSuitArmorMaterial.ArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(BossToolsItemGroups.tab_normal)) {
		@Override
		@OnlyIn(Dist.CLIENT)
		public BipedModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
			BipedModel armorModel = new BipedModel(1);
			armorModel.bipedLeftLeg = new SpaceSuitModel.SPACE_SUIT_P1().FootLeft;
			armorModel.bipedRightLeg = new SpaceSuitModel.SPACE_SUIT_P1().FootRight;
			armorModel.isSneak = living.isSneaking();
			armorModel.isSitting = defaultModel.isSitting;
			armorModel.isChild = living.isChild();
			return armorModel;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return BossToolsMod.ModId + ":textures/models/armor/space_suit.png";
		}
	};
}