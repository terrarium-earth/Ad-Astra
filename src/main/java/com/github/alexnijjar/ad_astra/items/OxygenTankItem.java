package com.github.alexnijjar.ad_astra.items;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.impl.transfer.context.PlayerContainerItemContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OxygenTankItem extends Item implements FluidContainingItem {

    public static final long TANK_SIZE = AdAstra.CONFIG.world.oxygenTankSize;

    public OxygenTankItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long oxygen = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
        tooltip.add(Text.translatable("tooltip.ad_astra.consumable"));
        tooltip.add(Text.translatable("tooltip.ad_astra.space_suit", oxygen, FluidUtils.dropletsToMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
    }

    @Override
    public long getTankSize() {
        return TANK_SIZE;
    }

    // Consume the tank and give the player oxygen.
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack tank = user.getStackInHand(hand);
        if (!world.isClient) {
            ItemStack chest = user.getEquippedStack(EquipmentSlot.CHEST);
            if (chest.isOf(ModItems.SPACE_SUIT) || chest.isOf(ModItems.NETHERITE_SPACE_SUIT) || chest.isOf(ModItems.JET_SUIT)) {

                PlayerInventoryStorage playerWrapper = PlayerInventoryStorage.of(user);
                ContainerItemContext from = ContainerItemContext.withInitial(tank);
                PlayerContainerItemContext to = new PlayerContainerItemContext(user, playerWrapper.getSlot(38));

                if (from != null && to != null) {
                    long amountExtracted;
                    if ((amountExtracted = this.transferFluid(from.find(FluidStorage.ITEM), to.find(FluidStorage.ITEM))) > 0) {
                        this.setAmount(tank, this.getAmount(tank) - amountExtracted);
                        world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1, 1);
                        return TypedActionResult.consume(tank);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }
}