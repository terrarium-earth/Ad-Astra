package earth.terrarium.ad_astra.items;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.BiPredicate;

public class OxygenTankItem extends Item implements FluidContainingItem {

    public OxygenTankItem(Properties settings) {
        super(settings);
    }

    public static ItemStack createOxygenatedTank() {
        ItemStackHolder oxygenTank = new ItemStackHolder(ModItems.OXYGEN_TANK.get().getDefaultInstance());
        ((OxygenTankItem) oxygenTank.getStack().getItem()).insert(oxygenTank, FluidHooks.newFluidHolder(ModFluids.OXYGEN_STILL.get(), AdAstra.CONFIG.general.oxygenTankSize, null));

        return oxygenTank.getStack();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
        long oxygen = FluidHooks.toMillibuckets(FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount());
        tooltip.add(Component.translatable("tooltip.ad_astra.consumable"));
        tooltip.add(Component.translatable("tooltip.ad_astra.space_suit", oxygen, FluidHooks.toMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
    }

    // Consume the tank and give the player oxygen.
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        if (!level.isClientSide) {
            ItemStack tank = user.getItemInHand(hand);
            ItemStack chest = user.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.is(ModItems.SPACE_SUIT.get()) || chest.is(ModItems.NETHERITE_SPACE_SUIT.get()) || chest.is(ModItems.JET_SUIT.get())) {

                ItemStackHolder from = new ItemStackHolder(tank);
                ItemStackHolder to = new ItemStackHolder(user.getInventory().getArmor(2));

                var fromFluidHolder = FluidHooks.getItemFluidManager(from.getStack()).getFluidInTank(0);

                if (FluidHooks.moveItemToItemFluid(from, to, FluidHooks.newFluidHolder(fromFluidHolder.getFluid(), FluidHooks.buckets(1) / 1000, fromFluidHolder.getCompound())) > 0) {
                    if (from.isDirty()) user.setItemInHand(hand, from.getStack());
                    if (to.isDirty()) user.setItemSlot(EquipmentSlot.CHEST, to.getStack());
                    level.playSound(null, user.blockPosition(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1, 1);
                    return InteractionResultHolder.consume(tank);
                }
            }
        }
        return super.use(level, user, hand);
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> f.getFluid().is(ModTags.OXYGEN);
    }

    @Override
    public long getTankSize() {
        return AdAstra.CONFIG.general.oxygenTankSize;
    }
}