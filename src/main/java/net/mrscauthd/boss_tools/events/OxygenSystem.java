package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;

public class OxygenSystem {

    public static void OxygenSystem(PlayerEntity entity) {
        World world = entity.getEntityWorld();
        if (Config.PlayerOxygenSystem && Methodes.isSpaceWorld(world) && !entity.isSpectator() && !entity.abilities.isCreativeMode) {

            if (entity.getAir() < 1) {
                Methodes.OxygenDamage(entity);
            }

            if (Methodes.spaceSuitCheckBoth(entity)) {

                ItemStack itemstack = entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, 2));
                IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

                if (oxygenStorage.getOxygenStored() == 0) {
                    entity.setAir(-4);
                }

                if (oxygenStorage.getOxygenStored() > 0 || entity.isPotionActive(ModInnet.OXYGEN_EFFECT.get()) || entity.getPersistentData().getBoolean(BossToolsMod.ModId + ":planet_selection_gui_open")) {
                    entity.setAir(300);
                }

            }

            if (!Methodes.spaceSuitCheckBoth(entity)) {
                entity.setAir(-4);
            }
        }

    }
}
