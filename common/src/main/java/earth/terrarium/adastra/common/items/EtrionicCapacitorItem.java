package earth.terrarium.adastra.common.items;

import dev.architectury.injectables.annotations.PlatformOnly;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.DistributionMode;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
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
            new SimpleEnergyContainer(250_000) {
                @Override
                public long maxInsert() {
                    return 250;
                }

                @Override
                public long maxExtract() {
                    return 500;
                }
            });
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        var energy = getEnergyStorage(stack);
        tooltipComponents.add(TooltipUtils.getEnergyComponent(energy.getStoredEnergy(), energy.getMaxCapacity()));
        tooltipComponents.add(TooltipUtils.getActiveInactiveComponent(active(stack)));
        tooltipComponents.add(TooltipUtils.getDistributionModeComponent(mode(stack)));
        tooltipComponents.add(TooltipUtils.getMaxEnergyInComponent(energy.maxInsert()));
        tooltipComponents.add(TooltipUtils.getMaxEnergyOutComponent(energy.maxExtract()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.ETRIONIC_CAPACITOR_INFO);
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
        if (level.isClientSide()) return;
        if (entity.tickCount % 5 == 0) return;
        if (!active(stack)) return;
        if (!(entity instanceof Player player)) return;
        Inventory inventory = player.getInventory();
        var container = getEnergyStorage(stack);
        if (container.getStoredEnergy() == 0) return;
        ItemStackHolder from = new ItemStackHolder(stack);
        switch (mode(stack)) {
            case SEQUENTIAL -> distributeSequential(from, container.maxExtract() * 5, inventory);
            case ROUND_ROBIN -> distributeRoundRobin(from, container.maxExtract() * 5, inventory);
        }
        inventory.setItem(slotId, from.getStack());
    }

    public void distributeSequential(ItemStackHolder from, long maxExtract, Inventory inventory) {
        for (int i = inventory.getContainerSize() - 1; i >= 0; i--) {
            var stack = inventory.getItem(i);
            if (stack.isEmpty() || stack.is(this)) continue;
            ItemStackHolder to = new ItemStackHolder(stack);
            long moved = EnergyApi.moveEnergy(from, to, maxExtract, false);
            inventory.setItem(i, to.getStack());
            if (moved > 0) return;
        }
    }

    public void distributeRoundRobin(ItemStackHolder from, long maxExtract, Inventory inventory) {
        int energyItems = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!EnergyContainer.holdsEnergy(inventory.getItem(i))) continue;
            if (inventory.getItem(i).is(this)) continue;
            energyItems++;
        }
        if (energyItems == 0) return;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            var stack = inventory.getItem(i);
            if (stack.isEmpty() || stack.is(this)) continue;
            ItemStackHolder to = new ItemStackHolder(stack);
            EnergyApi.moveEnergy(from, to, maxExtract / energyItems, false);
            inventory.setItem(i, to.getStack());
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

    // NeoForge disabling of nbt change animation
    @SuppressWarnings("unused")
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
