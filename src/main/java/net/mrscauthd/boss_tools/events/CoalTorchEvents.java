package net.mrscauthd.boss_tools.events;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.CoalLanternBlock;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId)
public class CoalTorchEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity entity = event.getPlayer();
        if (event.getHand() != entity.getActiveHand()) {
            return;
        }
        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();
        BlockPos pos = new BlockPos(x,y,z);
        IWorld world = event.getWorld();

        if (!Methodes.isSpaceWorld((World) world) && !entity.isSneaking() && entity.getHeldItemMainhand().getItem() == Items.FLINT_AND_STEEL) {

            if (world.getBlockState(pos).getBlock() == ModInnet.WALL_COAL_TORCH_BLOCK.get()) {
                //Facing
                BlockState bs = world.getBlockState(pos);
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateContainer().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlockState(pos, Blocks.WALL_TORCH.getDefaultState().with(property,bs.get(property)), 3);

                play_fire_sounds_place(pos, (World) world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

            if (world.getBlockState(pos).getBlock() == ModInnet.COAL_TORCH_BLOCK.get().getDefaultState().getBlock()) {
                world.setBlockState(pos, Blocks.TORCH.getDefaultState(), 3);

                play_fire_sounds_place(pos, (World) world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

            if (world.getBlockState(pos).getBlock() == ModInnet.COAL_LANTERN_BLOCK.get() && world.getBlockState(pos).getBlockState().get(CoalLanternBlock.HANGING) == false) {
                world.setBlockState(pos, Blocks.LANTERN.getDefaultState(), 3);

                play_fire_sounds_place(pos, (World) world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

            if (world.getBlockState(pos).getBlock() == ModInnet.COAL_LANTERN_BLOCK.get() && world.getBlockState(pos).getBlockState().get(CoalLanternBlock.HANGING) == true) {
                world.setBlockState(pos, Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true), 3);

                play_fire_sounds_place(pos, (World) world);
                flintDamage(event.getItemStack(),event.getPlayer(),event.getHand());
            }

        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        IWorld world = event.getWorld();

        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();

        BlockPos pos = new BlockPos(x,y,z);

        if (Methodes.isSpaceWorld((World) world) == true) {
            //Remove Fire
            if (world.getBlockState(pos).getBlock() == Blocks.FIRE.getDefaultState().getBlock()) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.WALL_TORCH) {
                //Facing
                BlockState bs = world.getBlockState(pos);
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateContainer().getProperty("facing");

                //Place Coal Torch Block with Dr
                world.setBlockState(pos, ModInnet.WALL_COAL_TORCH_BLOCK.get().getDefaultState().with(property,bs.get(property)), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.TORCH) {
                world.setBlockState(pos, ModInnet.COAL_TORCH_BLOCK.get().getDefaultState(), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.LANTERN && world.getBlockState(pos).getBlockState().get(LanternBlock.HANGING) == false) {
                world.setBlockState(pos, ModInnet.COAL_LANTERN_BLOCK.get().getDefaultState(), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.LANTERN && world.getBlockState(pos).getBlockState().get(LanternBlock.HANGING) == true) {
                world.setBlockState(pos, ModInnet.COAL_LANTERN_BLOCK.get().getDefaultState().with(CoalLanternBlock.HANGING, true), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

            if (world.getBlockState(pos).getBlock() == Blocks.CAMPFIRE && world.getBlockState(pos).getBlockState().get(CampfireBlock.LIT) == true) {
                //Get Block State
                BooleanProperty property = (BooleanProperty) world.getBlockState(pos).getBlock().getStateContainer().getProperty("lit");

                //Set Block State
                world.setBlockState(pos, world.getBlockState(pos).with(property, false), 3);

                play_fire_sounds_delete(pos, (World) world);
            }

        }
    }

    public static void play_fire_sounds_delete(BlockPos pos, World world) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
    }

    public static void play_fire_sounds_place(BlockPos pos, World world) {
        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE,SoundCategory.BLOCKS, 1,1);
    }

    public static void flintDamage(ItemStack itemstack, PlayerEntity playerentity, Hand hand) {
        itemstack.damageItem(1, playerentity, (player) -> {
            player.sendBreakAnimation(hand);
        });
    }
    
}