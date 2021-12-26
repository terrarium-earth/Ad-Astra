package net.mrscauthd.beyond_earth.events;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.capability.IOxygenStorage;
import net.mrscauthd.beyond_earth.capability.OxygenUtil;

public class OxygenSystem {

    public static void OxygenSystem(Player entity) {
        Level world = entity.getLevel();
        if (Config.PlayerOxygenSystem && Methodes.isSpaceWorld(world) && !entity.isSpectator() && !entity.getAbilities().instabuild) {

            if (entity.getAirSupply() < 1) {
                Methodes.OxygenDamage(entity);
            }

            if (Methodes.spaceSuitCheckBoth(entity)) {

                ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, 2));
                IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

                if (oxygenStorage.getOxygenStored() == 0) {
                    entity.setAirSupply(-4);
                }

                if (oxygenStorage.getOxygenStored() > 0) {
                    entity.setAirSupply(300);
                }

            }

            if (!Methodes.spaceSuitCheckBoth(entity)) {
                entity.setAirSupply(-4);
            }

            if (entity.hasEffect(ModInit.OXYGEN_EFFECT.get()) || entity.getPersistentData().getBoolean(BeyondEarthMod.MODID + ":planet_selection_gui_open")) {
                entity.setAirSupply(300);
            }
        }
    }
}
