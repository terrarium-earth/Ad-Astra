package earth.terrarium.adastra.api.upgrades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface Upgradable {
    String UPGRADES_TAG = "Upgrades";

    Set<ResourceLocation> getUpgrades();

    default boolean hasUpgrade(ResourceLocation upgrade, ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (tag.contains(UPGRADES_TAG)) {
            CompoundTag upgradesTag = tag.getCompound(UPGRADES_TAG);
            return upgradesTag.contains(upgrade.toString()) && upgradesTag.getByte(upgrade.toString()) > 0;
        }
        return false;
    }

    default Map<ResourceLocation, Byte> getAllUpgrades(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (tag.contains(UPGRADES_TAG)) {
            CompoundTag upgradesTag = tag.getCompound(UPGRADES_TAG);
            return upgradesTag.getAllKeys().stream().collect(Collectors.toMap(ResourceLocation::new, upgradesTag::getByte));
        }
        return Collections.emptyMap();
    }

    default void addUpgrade(ResourceLocation upgrade, ItemStack stack) {
        var tag = stack.getOrCreateTag();
        CompoundTag upgradesTag = tag.contains(UPGRADES_TAG) ? tag.getCompound(UPGRADES_TAG) : new CompoundTag();
        upgradesTag.putByte(upgrade.toString(), (byte) 1);
        tag.put(UPGRADES_TAG, upgradesTag);
    }

    default void removeUpgrade(ResourceLocation upgrade, ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (tag.contains(UPGRADES_TAG)) {
            CompoundTag upgradesTag = tag.getCompound(UPGRADES_TAG);
            upgradesTag.remove(upgrade.getNamespace());
        }
    }
}
