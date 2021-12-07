package net.mrscauthd.boss_tools.block.helper;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapeHelper {

	public static VoxelShape boxSimple(double x1, double y1, double z1, double x2, double y2, double z2) {
		AABB aabb = new AABB(x1, y1, z1, x2, y2, z2);
		return Shapes.box(aabb.minX / 16.0D, aabb.minY / 16.0D, aabb.minZ / 16.0D, aabb.maxX / 16.0D, aabb.maxY / 16.0D, aabb.maxZ / 16.0D);
	}

}
