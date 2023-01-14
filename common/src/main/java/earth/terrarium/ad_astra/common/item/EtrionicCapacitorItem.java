package earth.terrarium.ad_astra.common.item;

import dev.architectury.injectables.annotations.PlatformOnly;
import dev.architectury.platform.Platform;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.util.LangUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.base.PlatformItemEnergyManager;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class EtrionicCapacitorItem extends Item implements EnergyAttachment.Item {
    public static final String TOGGLE_KEY = "ToggledOn";

    public EtrionicCapacitorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        long energy = getEnergyStorage(stack).getStoredEnergy();
        // Make use config
        tooltip.add(Component.translatable("tooltip.ad_astra.energy_bar", energy, 250000).setStyle(Style.EMPTY.withColor(energy > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getItemInHand(usedHand);
            if (player.isShiftKeyDown()) {
                DistributionMode mode = DistributionMode.switchMode(stack);
                player.displayClientMessage(Component.translatable(LangUtils.CAPACITOR_MODE, mode.getComponent()), true);
                return InteractionResultHolder.success(stack);
            } else {
                boolean toggled = toggle(stack);
                player.displayClientMessage(Component.translatable(toggled ? LangUtils.CAPACITOR_ON : LangUtils.CAPACITOR_OFF), true);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public WrappedItemEnergyContainer getEnergyStorage(ItemStack object) {
        //TODO Add config
        return new WrappedItemEnergyContainer(object, new SimpleEnergyContainer(250000));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        //TODO config
        long transferRate = 500;
        ItemStackHolder from = new ItemStackHolder(stack);
        if (getEnergyStorage(stack).getStoredEnergy() > 0 && isToggled(stack) && entity instanceof Player player) {
            DistributionMode mode = DistributionMode.getMode(stack);
            if (mode == DistributionMode.ROUND_ROBIN) {
                Map<SlottedItem, Long> containers = new HashMap<>();
                AtomicLong total = new AtomicLong();
                for (int i = 0; i < player.getInventory().items.size(); i++) {
                    ItemStack item = player.getInventory().items.get(i);
                    if (item == stack) continue;
                    int finalI = i;
                    EnergyHooks.safeGetItemEnergyManager(item).ifPresent((energy) -> {
                        long movable = energy.insert(new ItemStackHolder(item.copy()), transferRate, true);
                        if (movable > 0) {
                            containers.put(new SlottedItem(item, finalI, (itemStack, ind) -> player.getInventory().items.set(ind, itemStack)), movable);
                            total.addAndGet(movable);
                        }
                    });
                }
                for (EquipmentSlot value : EquipmentSlot.values()) {
                    ItemStack item = player.getItemBySlot(value);
                    if (item == stack) continue;
                    EnergyHooks.safeGetItemEnergyManager(item).ifPresent((energy) -> {
                        long movable = energy.insert(new ItemStackHolder(item.copy()), transferRate, true);
                        if (movable > 0) {
                            containers.put(new SlottedItem(item, value.ordinal(), (itemStack, ind) -> player.setItemSlot(EquipmentSlot.values()[ind], itemStack)), movable);
                            total.addAndGet(movable);
                        }
                    });
                }
                for (var container : containers.entrySet()) {
                    ItemStackHolder to = new ItemStackHolder(container.getKey().stack());
                    if (total.get() <= transferRate) {
                        EnergyHooks.safeMoveItemToItemEnergy(from, to, transferRate);
                    } else {
                        EnergyHooks.safeMoveItemToItemEnergy(from, to, (long) Math.ceil(transferRate * ((double) container.getValue().intValue() / total.get())));
                    }
                    container.getKey().apply(to);
                    if (from.isDirty()) player.getInventory().setItem(slotId, from.getStack());
                }
            } else if (mode == DistributionMode.SEQUENTIAL) {
                long transfered = 0;

                for (EquipmentSlot value : EquipmentSlot.values()) {
                    ItemStack item = player.getItemBySlot(value);
                    if (item == stack || item.isEmpty()) continue;
                    ItemStackHolder to = new ItemStackHolder(item);
                    Optional<PlatformItemEnergyManager> platformItemEnergyManager = EnergyHooks.safeGetItemEnergyManager(item);
                    if (platformItemEnergyManager.isPresent()) {
                        long movable = platformItemEnergyManager.get().insert(to, transferRate, true);
                        if (movable > 0) {
                            EnergyHooks.safeMoveItemToItemEnergy(from, to, transferRate);
                            transfered += transferRate;
                            if (to.isDirty()) player.setItemSlot(value, to.getStack());
                            if (from.isDirty()) player.getInventory().setItem(slotId, from.getStack());
                        }
                    }
                    if (transfered == transferRate) return;
                }

                for (int i = 0; i < player.getInventory().items.size(); i++) {
                    ItemStack item = player.getInventory().items.get(i);
                    if (item == stack || item.isEmpty()) continue;
                    ItemStackHolder to = new ItemStackHolder(item);
                    Optional<PlatformItemEnergyManager> platformItemEnergyManager = EnergyHooks.safeGetItemEnergyManager(item);
                    if (platformItemEnergyManager.isPresent()) {
                        long movable = platformItemEnergyManager.get().insert(to, transferRate, true);
                        if (movable > 0) {
                            EnergyHooks.safeMoveItemToItemEnergy(from, to, transferRate);
                            transfered += transferRate;
                            if (to.isDirty()) player.getInventory().setItem(i, to.getStack());
                            if (from.isDirty()) player.getInventory().setItem(slotId, from.getStack());
                        }
                    }
                    if (transfered == transferRate) return;
                }
            }
        }
    }

    public static boolean toggle(ItemStack stack) {
        boolean mode = !isToggled(stack);
        stack.getOrCreateTag().putBoolean(TOGGLE_KEY, mode);
        return mode;
    }

    public static boolean isToggled(ItemStack stack) {
        if (stack.getOrCreateTag().contains(TOGGLE_KEY)) {
            return stack.getOrCreateTag().getBoolean(TOGGLE_KEY);
        } else {
            stack.getOrCreateTag().putBoolean(TOGGLE_KEY, true);
            return true;
        }
    }

    public static float itemProperty(ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) {
        return isToggled(itemStack) ? 0 : 1;
    }

    public enum DistributionMode {
        ROUND_ROBIN,
        SEQUENTIAL;

        public static final String KEY = "DistributionMode";
        public static final DistributionMode[] VALUES = values();

        public static DistributionMode getMode(ItemStack stack) {
            if (stack.getOrCreateTag().contains(KEY)) {
                try {
                    return DistributionMode.valueOf(stack.getOrCreateTag().getString(KEY));
                } catch (Error ignored) {
                }
            }
            stack.getOrCreateTag().putString(KEY, DistributionMode.SEQUENTIAL.toString());
            return DistributionMode.SEQUENTIAL;
        }

        public static DistributionMode switchMode(ItemStack stack) {
            DistributionMode mode = getMode(stack);
            DistributionMode newMode = VALUES[((mode.ordinal() + 1) % VALUES.length)];
            stack.getOrCreateTag().putString(KEY, newMode.name());
            return newMode;
        }

        public Component getComponent() {
            return Component.translatable("misc.ad_astra.distribution_mode." + toString().toLowerCase(Locale.ROOT));
        }
    }

    //Fabric disabling of nbt change animation
    @PlatformOnly(PlatformOnly.FABRIC)
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    //Forge disabling of nbt change animation
    @PlatformOnly(PlatformOnly.FORGE)
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        //TODO config
        return getEnergyStorage(stack).getStoredEnergy() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var energyStorage = getEnergyStorage(stack);
        return (int) (((double) energyStorage.getStoredEnergy() / energyStorage.getMaxCapacity()) * 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return AdAstra.ETRIUM_COLOR;
    }

    public record SlottedItem(ItemStack stack, int slot, SlotFunction runnable) {
        public void apply(ItemStackHolder holder) {
            if (holder.isDirty()) runnable.apply(holder.getStack(), slot);
        }
    }

    @FunctionalInterface
    public interface SlotFunction {
        public abstract void apply(ItemStack stack, int slot);
    }
}