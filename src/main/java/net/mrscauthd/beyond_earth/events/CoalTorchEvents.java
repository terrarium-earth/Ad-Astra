package net.mrscauthd.beyond_earth.events;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.block.CoalLanternBlock;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID)
public class CoalTorchEvents {

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent event) {
        Level world = (Level) event.getWorld();

        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();

        BlockPos pos = new BlockPos(x,y,z);

        if (Methods.isSpaceWorldWithoutOxygen(world)) {

            //Remove Fire
            if (world.getBlockState(pos).getBlock() == Blocks.FIRE.defaultBlockState().getBlock()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.WALL_TORCH) {
                //Facing
                BlockState bs = world.getBlockState(pos);
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateDefinition().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlock(pos, ModInit.WALL_COAL_TORCH_BLOCK.get().defaultBlockState().setValue(property,bs.getValue(property)), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.TORCH) {
                world.setBlock(pos, ModInit.COAL_TORCH_BLOCK.get().defaultBlockState(), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.LANTERN && !world.getBlockState(pos).getValue(LanternBlock.HANGING)) {
                world.setBlock(pos, ModInit.COAL_LANTERN_BLOCK.get().defaultBlockState(), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.LANTERN && world.getBlockState(pos).getValue(LanternBlock.HANGING)) {
                world.setBlock(pos, ModInit.COAL_LANTERN_BLOCK.get().defaultBlockState().setValue(CoalLanternBlock.HANGING, true), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.CAMPFIRE && world.getBlockState(pos).getValue(CampfireBlock.LIT)) {
                //Get Block State
                BooleanProperty property = (BooleanProperty) world.getBlockState(pos).getBlock().getStateDefinition().getProperty("lit");

                //Set Block State
                world.setBlock(pos, world.getBlockState(pos).setValue(property, false), 3);

                play_fire_sounds_delete(pos, world);
            }

        }
    }

    public static void play_fire_sounds_delete(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
    }
}