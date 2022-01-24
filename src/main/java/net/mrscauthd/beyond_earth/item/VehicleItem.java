package net.mrscauthd.beyond_earth.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VehicleItem extends Item {
    public VehicleItem(Properties p_41383_) {
        super(p_41383_);
    }

    protected static double getYOffset(LevelReader p_20626_, BlockPos p_20627_, boolean p_20628_, AABB p_20629_) {
        AABB aabb = new AABB(p_20627_);
        if (p_20628_) {
            aabb = aabb.expandTowards(0.0D, -1.0D, 0.0D);
        }

        Iterable<VoxelShape> iterable = p_20626_.getCollisions((Entity)null, aabb);
        return 1.0D + Shapes.collide(Direction.Axis.Y, p_20629_, iterable, p_20628_ ? -2.0D : -1.0D);
    }
}
