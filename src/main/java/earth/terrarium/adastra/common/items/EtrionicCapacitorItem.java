package earth.terrarium.adastra.common.items;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModDataComponents;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.utils.DistributionMode;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.EnergyProvider;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
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

import java.util.List;

public class EtrionicCapacitorItem extends Item implements EnergyProvider.Item {

    public static final String ACTIVE_TAG = "Active";
    public static final String MODE_TAG = "Mode";

    public EtrionicCapacitorItem(Properties properties) {
        super(properties);
    }

    public static boolean active(ItemStack stack) {
        if (stack.has(ModDataComponents.ACTIVE.get())) {
            return stack.getOrDefault(ModDataComponents.ACTIVE.get(), false);
        }
        return true;
    }

    public static boolean toggleActive(ItemStack stack) {
        boolean active = active(stack);
        stack.set(ModDataComponents.ACTIVE.get(), !active);
        return !active;
    }

    public static DistributionMode mode(ItemStack stack) {
        if (stack.has(ModDataComponents.MODE.get())) {
            return DistributionMode.values()[stack.getOrDefault(ModDataComponents.MODE.get(), (byte) 0)];
        }
        return DistributionMode.SEQUENTIAL;
    }

    public static DistributionMode toggleMode(ItemStack stack) {
        DistributionMode mode = mode(stack);
        DistributionMode toggled = mode == DistributionMode.SEQUENTIAL ? DistributionMode.ROUND_ROBIN : DistributionMode.SEQUENTIAL;
        stack.set(ModDataComponents.MODE.get(), (byte) toggled.ordinal());
        return toggled;
    }

    @Override
    public ValueStorage getEnergy(ItemStack itemStack, ItemContext context) {
        return new SimpleValueStorage(context, ModDataManagers.VALUE_CONTENT.componentType(), 250_000);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        var energy = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
        tooltipComponents.add(TooltipUtils.getEnergyComponent(energy.getStoredAmount(), energy.getCapacity()));
        tooltipComponents.add(TooltipUtils.getActiveInactiveComponent(active(stack)));
        tooltipComponents.add(TooltipUtils.getDistributionModeComponent(mode(stack)));
//        tooltipComponents.add(TooltipUtils.getMaxEnergyInComponent(energy.maxInsert()));
//        tooltipComponents.add(TooltipUtils.getMaxEnergyOutComponent(energy.maxExtract()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.ETRIONIC_CAPACITOR_INFO);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }
        ItemStack stack = player.getItemInHand(usedHand);

        if (player.isShiftKeyDown()) {
            DistributionMode mode = toggleMode(stack);
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
        var container = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
        if (container.getCapacity() == 0) return;
        switch (mode(stack)) {
            case SEQUENTIAL -> distributeSequential(container, 250 * 5, inventory);
            case ROUND_ROBIN -> distributeRoundRobin(container, 250 * 5, inventory);
        }
    }

    public void distributeSequential(ValueStorage from, long maxExtract, Inventory inventory) {
        for (int i = inventory.getContainerSize() - 1; i >= 0; i--) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || stack.is(this)) continue;
            var to = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
            long moved = TransferUtil.moveValue(from, to, maxExtract, false);
            if (moved > 0) return;
        }
    }

    public void distributeRoundRobin(ValueStorage from, long maxExtract, Inventory inventory) {
        int energyItems = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!new ModifyOnlyContext(inventory.getItem(i)).isPresent(EnergyApi.ITEM)) continue;
            if (inventory.getItem(i).is(this)) continue;
            energyItems++;
        }
        if (energyItems == 0) return;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || stack.is(this)) continue;
            var to = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
            TransferUtil.moveValue(from, to, maxExtract / energyItems, false);
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        var energyStorage = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
        return energyStorage.getStoredAmount() > 0;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        var energyStorage = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
        return (int) (((double) energyStorage.getStoredAmount() / energyStorage.getCapacity()) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0x63dcc2;
    }
}
