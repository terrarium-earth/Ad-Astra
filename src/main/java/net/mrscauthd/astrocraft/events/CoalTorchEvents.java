package net.mrscauthd.astrocraft.events;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.ModInnet;
import net.mrscauthd.astrocraft.block.CoalLanternBlock;

@Mod.EventBusSubscriber(modid = AstroCraftMod.MODID)
public class CoalTorchEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player entity = event.getPlayer();
        if (event.getHand() != entity.getUsedItemHand()) {
            return;
        }
        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();
        BlockPos pos = new BlockPos(x,y,z);
        Level world = event.getWorld();

        if (!Methodes.isSpaceWorld(world) && !entity.isCrouching() && entity.getMainHandItem().getItem() == Items.FLINT_AND_STEEL) {

            if (world.getBlockState(pos).getBlock() == ModInnet.WALL_COAL_TORCH_BLOCK.get()) {
                //Facing
                BlockState bs = world.getBlockState(pos);
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateDefinition().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlock(pos, Blocks.WALL_TORCH.defaultBlockState().setValue(property,bs.getValue(property)), 3);

                play_fire_sounds_place(pos, world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

            if (world.getBlockState(pos).getBlock() == ModInnet.COAL_TORCH_BLOCK.get().defaultBlockState().getBlock()) {
                world.setBlock(pos, Blocks.TORCH.defaultBlockState(), 3);

                play_fire_sounds_place(pos, world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

            if (world.getBlockState(pos).getBlock() == ModInnet.COAL_LANTERN_BLOCK.get() && world.getBlockState(pos).getValue(CoalLanternBlock.HANGING) == false) {
                world.setBlock(pos, Blocks.LANTERN.defaultBlockState(), 3);

                play_fire_sounds_place(pos, world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

            if (world.getBlockState(pos).getBlock() == ModInnet.COAL_LANTERN_BLOCK.get() && world.getBlockState(pos).getValue(CoalLanternBlock.HANGING) == true) {
                world.setBlock(pos, Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true), 3);

                play_fire_sounds_place(pos, world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        Level world = (Level) event.getWorld();

        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();

        BlockPos pos = new BlockPos(x,y,z);

        if (Methodes.isSpaceWorld(world)) {

            //Remove Fire
            if (world.getBlockState(pos).getBlock() == Blocks.FIRE.defaultBlockState().getBlock()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.WALL_TORCH) {
                //Facing
                BlockState bs = world.getBlockState(pos);
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateDefinition().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlock(pos, ModInnet.WALL_COAL_TORCH_BLOCK.get().defaultBlockState().setValue(property,bs.getValue(property)), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.TORCH) {
                world.setBlock(pos, ModInnet.COAL_TORCH_BLOCK.get().defaultBlockState(), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.LANTERN && !world.getBlockState(pos).getValue(LanternBlock.HANGING)) {
                world.setBlock(pos, ModInnet.COAL_LANTERN_BLOCK.get().defaultBlockState(), 3);

                play_fire_sounds_delete(pos, world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.LANTERN && world.getBlockState(pos).getValue(LanternBlock.HANGING)) {
                world.setBlock(pos, ModInnet.COAL_LANTERN_BLOCK.get().defaultBlockState().setValue(CoalLanternBlock.HANGING, true), 3);

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

    public static void play_fire_sounds_place(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1,1);
    }

    public static void flintDamage(ItemStack itemstack, Player playerentity, InteractionHand hand) {
        itemstack.hurtAndBreak(1, playerentity, (player) -> {
            player.broadcastBreakEvent(hand);
        });
    }
    
}