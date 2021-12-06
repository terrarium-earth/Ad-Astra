package net.mrscauthd.boss_tools.events;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;

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

                if (oxygenStorage.getOxygenStored() > 0 || entity.hasEffect(ModInnet.OXYGEN_EFFECT.get()) || entity.getPersistentData().getBoolean(BossToolsMod.ModId + ":planet_selection_gui_open")) {
                    entity.setAirSupply(300);
                }

            }

            if (!Methodes.spaceSuitCheckBoth(entity)) {
                entity.setAirSupply(-4);
            }
        }

    }
}
