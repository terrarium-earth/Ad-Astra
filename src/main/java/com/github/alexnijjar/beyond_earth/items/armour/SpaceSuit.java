package com.github.alexnijjar.beyond_earth.items.armour;

import java.util.List;

import com.github.alexnijjar.beyond_earth.registry.ModArmour;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class SpaceSuit extends ArmorItem {

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
    }

    public void registerOxygenTank() {
        FluidStorage.ITEM.registerForItems(OxygenTankStorage::new, this);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(ModArmour.SPACE_SUIT) || stack.isOf(ModArmour.NETHERITE_SPACE_SUIT) || stack.isOf(ModArmour.JET_SUIT)) {
            long oxygen = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
            tooltip.add(Text.translatable("tooltip.beyond_earth.space_suit", oxygen, FluidUtils.dropletsToMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
        }
    }

    public long getTankSize() {
        return 1 * FluidConstants.BUCKET;
    }

    public FluidVariant getFluid(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("Fluid")) {
            return FluidVariant.fromNbt(nbt.getCompound("Fluid"));
        } else {
            return FluidVariant.blank();
        }
    }

    public void setFluid(ItemStack stack, FluidVariant variant) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.put("Fluid", variant.toNbt());
    }

    public long getAmount(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("Amount")) {
            return nbt.getLong("Amount");
        } else {
            return 0;
        }
    }

    public void setAmount(ItemStack stack, long amount) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putLong("Amount", amount);
    }

    // From MI.
    class OxygenTankStorage extends SingleVariantItemStorage<FluidVariant> {
        public OxygenTankStorage(ItemStack stack, ContainerItemContext context) {
            super(context);
        }

        @Override
        protected FluidVariant getBlankResource() {
            return FluidVariant.blank();
        }

        @Override
        protected FluidVariant getResource(ItemVariant currentVariant) {
            return getFluid(currentVariant.toStack());
        }

        @Override
        protected long getAmount(ItemVariant currentVariant) {
            return SpaceSuit.this.getAmount(currentVariant.toStack());
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return getTankSize();
        }

        @Override
        protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, FluidVariant newResource, long newAmount) {
            ItemStack stack = currentVariant.toStack();
            if (!newResource.isBlank() && newAmount > 0) {
                setFluid(stack, newResource);
                setAmount(stack, newAmount);
            }
            return ItemVariant.of(stack);
        }
    }
}