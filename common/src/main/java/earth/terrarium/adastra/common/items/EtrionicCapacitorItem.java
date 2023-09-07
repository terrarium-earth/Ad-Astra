package earth.terrarium.adastra.common.items;

import dev.architectury.injectables.annotations.PlatformOnly;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import earth.terrarium.adastra.common.utils.DistributionMode;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EtrionicCapacitorItem extends Item implements BotariumEnergyItem<WrappedItemEnergyContainer> {
    public static final String ACTIVE_TAG = "Active";
    public static final String MODE_TAG = "Mode";

    public EtrionicCapacitorItem(Properties properties) {
        super(properties);
    }

    public static boolean active(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (tag.contains(ACTIVE_TAG)) {
            return tag.getBoolean(ACTIVE_TAG);
        }
        return true;
    }

    public static boolean toggleActive(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        var active = active(stack);
        tag.putBoolean(ACTIVE_TAG, !active);
        return !active;
    }

    public static DistributionMode mode(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (tag.contains(MODE_TAG)) {
            return DistributionMode.values()[tag.getByte(MODE_TAG)];
        }
        return DistributionMode.SEQUENTIAL;
    }

    public static DistributionMode toggleMode(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        var mode = mode(stack);
        var toggled = mode == DistributionMode.SEQUENTIAL ? DistributionMode.ROUND_ROBIN : DistributionMode.SEQUENTIAL;
        tag.putByte(MODE_TAG, (byte) toggled.ordinal());
        return toggled;
    }

    @Override
    public WrappedItemEnergyContainer getEnergyStorage(ItemStack holder) {
        return new WrappedItemEnergyContainer(
            holder,
            new SimpleEnergyContainer(500_000) {
                @Override
                public long maxInsert() {
                    return 10_000;
                }

                @Override
                public long maxExtract() {
                    return 20_000;
                }

                @Override
                public void setEnergy(long energy) {
                    super.setEnergy(energy);
                }
            });
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        var energy = getEnergyStorage(stack);
        tooltipComponents.add(ComponentUtils.getEnergyComponent(energy.getStoredEnergy(), energy.getMaxCapacity()));
        tooltipComponents.add(ComponentUtils.getActiveInactiveComponent(active(stack)));
        tooltipComponents.add(ComponentUtils.getDistributionModeComponent(mode(stack)));
        tooltipComponents.add(ComponentUtils.getMaxEnergyInComponent(energy.maxInsert()));
        tooltipComponents.add(ComponentUtils.getMaxEnergyOutComponent(energy.maxExtract()));
        ComponentUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.ETRIONIC_CAPACITOR_INFO);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }
        var stack = player.getItemInHand(usedHand);

        if (player.isShiftKeyDown()) {
            var mode = toggleMode(stack);
            player.displayClientMessage(mode == DistributionMode.SEQUENTIAL ? ConstantComponents.CHANGE_MODE_SEQUENTIAL : ConstantComponents.CHANGE_MODE_ROUND_ROBIN, true);
        } else {
            boolean active = toggleActive(stack);
            player.displayClientMessage(active ? ConstantComponents.CAPACITOR_ENABLED : ConstantComponents.CAPACITOR_DISABLED, true);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide()) return;
        if (!active(stack)) return;
        if (!(entity instanceof Player player)) return;
        Inventory inventory = player.getInventory();
        var container = getEnergyStorage(stack);
        if (container.getStoredEnergy() == 0) return;
        ItemStackHolder from = new ItemStackHolder(stack);
        switch (mode(stack)) {
            case SEQUENTIAL -> distributeSequential(from, container.maxExtract(), inventory);
            case ROUND_ROBIN -> distributeRoundRobin(from, container.maxExtract(), inventory);
        }
        if (from.isDirty()) {
            stack.setTag(from.getStack().getTag());
        }
    }

    public void distributeSequential(ItemStackHolder from, long maxExtract, Inventory inventory) {
        if (distributeSequential(from, maxExtract, inventory.items) > 0) return;
        if (distributeSequential(from, maxExtract, inventory.armor) > 0) return;
        distributeSequential(from, maxExtract, inventory.offhand);
    }

    public long distributeSequential(ItemStackHolder from, long maxExtract, List<ItemStack> items) {
        for (var item : items) {
            if (item.isEmpty() || item == from.getStack()) continue;
            ItemStackHolder to = new ItemStackHolder(item);
            long moved = EnergyApi.moveEnergy(from, to, maxExtract, false);
            if (moved == 0) continue;
            if (to.isDirty()) {
                item.setTag(to.getStack().getTag());
            }
            return moved;
        }
        return 0;
    }

    public void distributeRoundRobin(ItemStackHolder stack, long maxExtract, Inventory inventory) {
        distributeRoundRobin(stack, maxExtract, inventory.items);
        distributeRoundRobin(stack, maxExtract, inventory.armor);
        distributeRoundRobin(stack, maxExtract, inventory.offhand);
    }

    public void distributeRoundRobin(ItemStackHolder stack, long maxExtract, List<ItemStack> items) {
        int energyItems = items
            .stream().
            filter(EnergyApi::isEnergyItem)
            .filter(item -> !item.is(this))
            .mapToInt(ItemStack::getCount).sum();
        if (energyItems == 0) return;

        for (var item : items) {
            if (item.isEmpty() || item.is(this)) continue;
            ItemStackHolder to = new ItemStackHolder(item);
            EnergyApi.moveEnergy(stack, to, maxExtract / energyItems, false);
            if (to.isDirty()) {
                item.setTag(to.getStack().getTag());
            }
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getEnergyStorage(stack).getStoredEnergy() > 0;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        var energyStorage = getEnergyStorage(stack);
        return (int) (((double) energyStorage.getStoredEnergy() / energyStorage.getMaxCapacity()) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0x63dcc2;
    }

    // Fabric disabling of nbt change animation
    @SuppressWarnings("unused")
    @PlatformOnly(PlatformOnly.FABRIC)
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    // Forge disabling of nbt change animation
    @SuppressWarnings("unused")
    @PlatformOnly(PlatformOnly.FORGE)
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
