package net.mrscauthd.beyond_earth.events;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

        if (Methods.isSpaceWorldWithoutOxygen(world)) {
            BlockPos pos = event.getPos();

            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();

            /** FIRE */
            if (block == Blocks.FIRE.defaultBlockState().getBlock()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }

            /** WALL TORCH */
            if (block == Blocks.WALL_TORCH) {
                DirectionProperty property = (DirectionProperty) blockState.getBlock().getStateDefinition().getProperty("facing");

                world.setBlock(pos, ModInit.WALL_COAL_TORCH_BLOCK.get().defaultBlockState().setValue(property, blockState.getValue(property)), 3);

                playFireExtinguish(pos, world);
            }

            /** TORCH */
            if (block == Blocks.TORCH) {
                world.setBlock(pos, ModInit.COAL_TORCH_BLOCK.get().defaultBlockState(), 3);

                playFireExtinguish(pos, world);
            }

            /** LANTERN */
            if (block == Blocks.LANTERN) {
                boolean isHanging = blockState.getValue(LanternBlock.HANGING);

                if (isHanging) {
                    world.setBlock(pos, ModInit.COAL_LANTERN_BLOCK.get().defaultBlockState().setValue(CoalLanternBlock.HANGING, true), 3);
                } else {
                    world.setBlock(pos, ModInit.COAL_LANTERN_BLOCK.get().defaultBlockState(), 3);
                }

                playFireExtinguish(pos, world);
            }

            /** CAMPFIRE */
            if (block == Blocks.CAMPFIRE && blockState.getValue(CampfireBlock.LIT)) {
                BooleanProperty property = (BooleanProperty) world.getBlockState(pos).getBlock().getStateDefinition().getProperty("lit");

                world.setBlock(pos, world.getBlockState(pos).setValue(property, false), 3);

                playFireExtinguish(pos, world);
            }
        }
    }

    public static void playFireExtinguish(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
    }
}