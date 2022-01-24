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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.entity.RoverEntity;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RoverItem extends VehicleItem {

    public static String fuelTag = BeyondEarthMod.MODID + ":fuel";

    public RoverItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        int fuel = p_41421_.getOrCreateTag().getInt(fuelTag);
        p_41423_.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getStorageText(GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE))));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        
        if (world.isClientSide()) {
        	return InteractionResult.PASS;
        }
        
        BlockPos pos = context.getClickedPos();
        InteractionHand hand = context.getHand();
        ItemStack itemStack = context.getItemInHand();

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

        if (!world.getBlockState(pos1).canOcclude() && !world.getBlockState(pos2).canOcclude() && !world.getBlockState(pos3).canOcclude() && !world.getBlockState(pos4).canOcclude() && !world.getBlockState(pos5).canOcclude() && !world.getBlockState(pos6).canOcclude() && !world.getBlockState(pos7).canOcclude() && !world.getBlockState(pos8).canOcclude() && !world.getBlockState(pos9).canOcclude()) {

            AABB scanAbove = new AABB(x - 0, y - 0, z - 0, x + 1, y + 1, z + 1);
            List<Entity> entities = player.getCommandSenderWorld().getEntitiesOfClass(Entity.class, scanAbove);

            if (entities.isEmpty()) {
                RoverEntity rover = new RoverEntity(ModInit.ROVER.get(), world);

                rover.setPos((double) pos.getX() + 0.5D,  pos.getY() + 1, (double) pos.getZ() + 0.5D);
                double d0 = this.getYOffset(world, pos, true, rover.getBoundingBox());
                float f = player.getYRot();

                rover.moveTo((double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D, f, 0.0F);

                rover.yRotO = f;

                world.addFreshEntity(rover);

                rover.getEntityData().set(RoverEntity.FUEL, itemStack.getOrCreateTag().getInt(fuelTag));

                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                } else {
                    player.swing(context.getHand(), true);
                }

                roverPlaceSound(pos, world);
            }
        }

        return super.useOn(context);
    }

    public static void roverPlaceSound(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1,1);
    }
}
