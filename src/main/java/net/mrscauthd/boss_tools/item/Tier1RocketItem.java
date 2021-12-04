package net.mrscauthd.boss_tools.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.block.RocketLaunchPad;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;

import java.util.List;
import java.util.stream.Stream;

public class Tier1RocketItem extends Item {

    public static String fuelTag = BossToolsMod.ModId + ":fuel";
    public static String bucketTag = BossToolsMod.ModId + ":bucket";

    public Tier1RocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(itemstack, world, list, flag);
        int fuel = itemstack.getOrCreateTag().getInt(fuelTag) / 3;
        list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(GaugeValueHelper.getFuel(fuel, 100))));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        Hand hand = context.getHand();
        ItemStack itemStack = context.getItem();

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (state.getBlock() instanceof RocketLaunchPad && state.get(RocketLaunchPad.STAGE)) {

            BlockPos pos1 = new BlockPos(x,y + 1, z);
            BlockPos pos2 = new BlockPos(x,y + 2, z);
            BlockPos pos3 = new BlockPos(x,y + 3, z);
            BlockPos pos4 = new BlockPos(x,y + 4, z);

            if (world.getBlockState(pos1).isAir() && world.getBlockState(pos2).isAir() && world.getBlockState(pos3).isAir() && world.getBlockState(pos4).isAir()) {

                AxisAlignedBB scanAbove = new AxisAlignedBB(x - 0, y - 0, z - 0, x + 1, y + 1, z + 1);
                List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class, scanAbove);

                if (entities.isEmpty()) {
                    RocketTier1Entity rocket = new RocketTier1Entity(ModInnet.TIER_1_ROCKET.get(), world);

                    rocket.setPosition((double) pos.getX() + 0.5D,  pos.getY() + 1, (double) pos.getZ() + 0.5D);
                    double d0 = func_208051_a(world, pos, true, rocket.getBoundingBox());
                    rocket.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);

                    rocket.rotationYawHead = rocket.rotationYaw;
                    rocket.renderYawOffset = rocket.rotationYaw;

                    if (world instanceof ServerWorld) {
                        rocket.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(rocket.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
                    }
                    world.addEntity(rocket);

                    rocket.getDataManager().set(RocketTier1Entity.FUEL, itemStack.getOrCreateTag().getInt(fuelTag));
                    rocket.getDataManager().set(RocketTier1Entity.BUCKET, itemStack.getOrCreateTag().getBoolean(bucketTag));

                    if (!player.abilities.isCreativeMode) {
                        player.setHeldItem(hand, ItemStack.EMPTY);
                    } else {
                        player.swing(context.getHand(), true);
                    }

                    rocketPlaceSound(pos, world);
                }
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

    public static void rocketPlaceSound(BlockPos pos, World world) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1,1);
    }
}
