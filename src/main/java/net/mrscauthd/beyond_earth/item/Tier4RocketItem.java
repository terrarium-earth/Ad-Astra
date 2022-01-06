package net.mrscauthd.beyond_earth.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.block.RocketLaunchPad;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Tier4RocketItem extends Item {

    public static String fuelTag = BeyondEarthMod.MODID + ":fuel";
    public static String bucketTag = BeyondEarthMod.MODID + ":buckets";

    public Tier4RocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        int fuel = itemstack.getOrCreateTag().getInt(fuelTag) / 3;
        list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(GaugeValueHelper.getFuel(fuel, 100))));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        InteractionHand hand = context.getHand();
        ItemStack itemStack = context.getItemInHand();

        if (world.isClientSide()) {
            return InteractionResult.PASS;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (state.getBlock() instanceof RocketLaunchPad && state.getValue(RocketLaunchPad.STAGE)) {

            //TODO MAKE IT HEIGHER
            BlockPos pos1 = new BlockPos(x,y + 1, z);
            BlockPos pos2 = new BlockPos(x,y + 2, z);
            BlockPos pos3 = new BlockPos(x,y + 3, z);
            BlockPos pos4 = new BlockPos(x,y + 4, z);

            if (world.getBlockState(pos1).isAir() && world.getBlockState(pos2).isAir() && world.getBlockState(pos3).isAir() && world.getBlockState(pos4).isAir()) {

                AABB scanAbove = new AABB(x - 0, y - 0, z - 0, x + 1, y + 1, z + 1);
                List<Entity> entities = player.getCommandSenderWorld().getEntitiesOfClass(Entity.class, scanAbove);

                if (entities.isEmpty()) {
                    RocketTier4Entity rocket = new RocketTier4Entity(ModInit.TIER_4_ROCKET.get(), world);

                    rocket.setPos((double) pos.getX() + 0.5D,  pos.getY() + 1, (double) pos.getZ() + 0.5D);
                    double d0 = getYOffset(world, pos, true, rocket.getBoundingBox());
                    rocket.moveTo((double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);

                    rocket.yHeadRot = rocket.getYRot();
                    rocket.yBodyRot = rocket.getYRot();

                    if (world instanceof ServerLevel) {
                        rocket.finalizeSpawn((ServerLevelAccessor) world, world.getCurrentDifficultyAt(new BlockPos(rocket.getX(), rocket.getY(), rocket.getZ())), MobSpawnType.MOB_SUMMONED, null, null);
                    }
                    world.addFreshEntity(rocket);

                    rocket.getEntityData().set(RocketTier4Entity.FUEL, itemStack.getOrCreateTag().getInt(fuelTag));
                    rocket.getEntityData().set(RocketTier4Entity.BUCKETS, itemStack.getOrCreateTag().getInt(bucketTag));

                    if (!player.getAbilities().instabuild) {
                        player.setItemInHand(hand, ItemStack.EMPTY);
                    } else {
                        player.swing(context.getHand(), true);
                    }

                    rocketPlaceSound(pos, world);
                }
            }
        }

        return super.useOn(context);
    }


    protected static double getYOffset(LevelReader p_20626_, BlockPos p_20627_, boolean p_20628_, AABB p_20629_) {
        AABB aabb = new AABB(p_20627_);
        if (p_20628_) {
            aabb = aabb.expandTowards(0.0D, -1.0D, 0.0D);
        }

        Iterable<VoxelShape> iterable = p_20626_.getCollisions((Entity)null, aabb);
        return 1.0D + Shapes.collide(Direction.Axis.Y, p_20629_, iterable, p_20628_ ? -2.0D : -1.0D);
    }

    public static void rocketPlaceSound(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1,1);
    }
}
