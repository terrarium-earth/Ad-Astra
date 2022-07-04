package com.github.alexnijjar.beyond_earth.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;
import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpacePaintingEntity extends PaintingEntity {

	public SpacePaintingEntity(EntityType<? extends PaintingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public SpacePaintingEntity(World world, BlockPos pos, Direction direction) {
		this(ModEntityTypes.SPACE_PAINTING, world);
		this.attachmentPos = pos;

		List<PaintingMotive> motives = Lists.newArrayList(Registry.PAINTING_MOTIVE.iterator());
		List<PaintingMotive> motivesToRemove = new LinkedList<>();

		// Only keep motives from this mod.
		for (PaintingMotive motive : motives) {
			if (!Registry.PAINTING_MOTIVE.getId(motive).getNamespace().equals(BeyondEarth.MOD_ID)) {
				motivesToRemove.add(motive);
			}
		}
		motives.removeAll(motivesToRemove);

		PaintingMotive paintingMotive;
		ArrayList<PaintingMotive> possibleMotives = Lists.newArrayList();
		int i = 0;
		Iterator<PaintingMotive> iterator = motives.stream().iterator();

		while (iterator.hasNext()) {
			this.motive = paintingMotive = (PaintingMotive) iterator.next();
			this.setFacing(direction);
			if (!this.canStayAttached())
				continue;
			possibleMotives.add(paintingMotive);
			int j = paintingMotive.getWidth() * paintingMotive.getHeight();
			if (j <= i)
				continue;
			i = j;
		}
		if (!possibleMotives.isEmpty()) {
			Iterator<PaintingMotive> iterator2 = possibleMotives.iterator();
			while (iterator2.hasNext()) {
				paintingMotive = (PaintingMotive) iterator2.next();
				if (paintingMotive.getWidth() * paintingMotive.getHeight() >= i)
					continue;
				iterator2.remove();
			}
			this.motive = (PaintingMotive) possibleMotives.get(this.random.nextInt(possibleMotives.size()));
		}
		this.setFacing(direction);
	}

	@Override
	public ItemEntity dropItem(ItemConvertible item) {
		return super.dropItem(ModItems.SPACE_PAINTING);
	}
	
	@Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.SPACE_PAINTING);
    }
}