package com.github.alexnijjar.beyond_earth.items;

import java.util.List;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.impl.transfer.context.PlayerContainerItemContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OxygenTankItem extends Item implements FluidContainingItem {

    public OxygenTankItem(Settings settings) {
        super(settings);
    }

    @Override
    public List<Fluid> getInputFluids() {
        return List.of(ModFluids.OXYGEN_STILL);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long oxygen = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
        tooltip.add(new TranslatableText("tooltip.beyond_earth.consumable"));
        tooltip.add(new TranslatableText("tooltip.beyond_earth.space_suit", oxygen, FluidUtils.dropletsToMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
    }

    @Override
    public long getTankSize() {
        return BeyondEarth.CONFIG.general.oxygenTankSize;
    }

    // Consume the tank and give the player oxygen.
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack tank = user.getStackInHand(hand);
        if (!world.isClient) {
            ItemStack chest = user.getEquippedStack(EquipmentSlot.CHEST);
            if (chest.isOf(ModItems.SPACE_SUIT) || chest.isOf(ModItems.NETHERITE_SPACE_SUIT) || chest.isOf(ModItems.JET_SUIT)) {

                PlayerInventoryStorage playerWrapper = PlayerInventoryStorage.of(user);
                Storage<FluidVariant> from = ContainerItemContext.ofPlayerHand(user, hand).find(FluidStorage.ITEM);
                Storage<FluidVariant> to = new PlayerContainerItemContext(user, playerWrapper.getSlot(38)).find(FluidStorage.ITEM);

                if (StorageUtil.move(from, to, f -> true, Long.MAX_VALUE, null) > 0) {
                    world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1, 1);
                    return TypedActionResult.consume(tank);
                }
            }
        }
        return super.use(world, user, hand);
    }

    public static ItemStack createOxygenatedTank() {
        ItemStack oxygenTank = ModItems.OXYGEN_TANK.getDefaultStack();
        ((OxygenTankItem) oxygenTank.getItem()).setAmount(oxygenTank, ((OxygenTankItem) oxygenTank.getItem()).getTankSize());
        ((OxygenTankItem) oxygenTank.getItem()).setFluid(oxygenTank, FluidVariant.of(ModFluids.OXYGEN_STILL));
        return oxygenTank;
    }
}