package net.mrscauthd.boss_tools.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;

import java.util.List;
import java.util.stream.Stream;

public class RoverItem extends Item {

    public static String fuelTag = BossToolsMod.ModId + ":fuel";

    public RoverItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(itemstack, world, list, flag);
        int fuel = itemstack.getOrCreateTag().getInt(fuelTag);
        list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getStorageText(GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE))));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Hand hand = context.getHand();
        ItemStack itemStack = context.getItem();

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        BlockPos pos1 = new BlockPos(x, y + 1, z);
        BlockPos pos2 = new BlockPos(x + 1, y + 1, z);
        BlockPos pos3 = new BlockPos(x - 1, y + 1, z);
        BlockPos pos4 = new BlockPos(x, y + 1, z + 1);
        BlockPos pos5 = new BlockPos(x, y + 1, z - 1);
        BlockPos pos6 = new BlockPos(x + 1, y + 1, z + 1);
        BlockPos pos7 = new BlockPos(x + 1, y + 1, z -1);
        BlockPos pos8 = new BlockPos(x - 1, y + 1, z + 1);
        BlockPos pos9 = new BlockPos(x - 1, y + 1, z - 1);

        if (!world.getBlockState(pos1).isSolid() && !world.getBlockState(pos2).isSolid() && !world.getBlockState(pos3).isSolid() && !world.getBlockState(pos4).isSolid() && !world.getBlockState(pos5).isSolid() && !world.getBlockState(pos6).isSolid() && !world.getBlockState(pos7).isSolid() && !world.getBlockState(pos8).isSolid() && !world.getBlockState(pos9).isSolid()) {

            AxisAlignedBB scanAbove = new AxisAlignedBB(x - 0, y - 0, z - 0, x + 1, y + 1, z + 1);
            List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, scanAbove);

            if (entities.isEmpty()) {
                RoverEntity rocket = new RoverEntity((EntityType<? extends CreatureEntity>) ModInnet.ROVER.get(), world);

                rocket.setPosition((double) pos.getX() + 0.5D,  pos.getY() + 1, (double) pos.getZ() + 0.5D);
                double d0 = func_208051_a(world, pos, true, rocket.getBoundingBox());
                float f = player.rotationYaw;

                rocket.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D, f, 0.0F);

                rocket.rotationYawHead = rocket.rotationYaw;
                rocket.renderYawOffset = rocket.rotationYaw;

                if (world instanceof ServerWorld) {
                    rocket.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(rocket.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
                }

                world.addEntity(rocket);

                rocket.getDataManager().set(RoverEntity.FUEL, itemStack.getOrCreateTag().getInt(fuelTag));

                if (!player.abilities.isCreativeMode) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                }

                roverPlaceSound(pos, world);
            }
        }

        return super.onItemUseFirst(stack, context);
    }

    protected static double func_208051_a(IWorldReader worldReader, BlockPos pos, boolean p_208051_2_, AxisAlignedBB p_208051_3_) {
        AxisAlignedBB axisalignedbb = new AxisAlignedBB(pos);
        if (p_208051_2_) {
            axisalignedbb = axisalignedbb.expand(0.0D, -1.0D, 0.0D);
        }

        Stream<VoxelShape> stream = worldReader.func_234867_d_((Entity)null, axisalignedbb, (entity) -> {
            return true;
        });
        return 1.0D + VoxelShapes.getAllowedOffset(Direction.Axis.Y, p_208051_3_, stream, p_208051_2_ ? -2.0D : -1.0D);
    }

    public static void roverPlaceSound(BlockPos pos, World world) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1,1);
    }
}
