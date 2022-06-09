package com.github.alexnijjar.beyond_earth.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.mixin.PaintingEntityInvoker;
import com.github.alexnijjar.beyond_earth.registry.ModEntities;
import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;

public class SpacePaintingEntity extends PaintingEntity {

	public SpacePaintingEntity(EntityType<? extends PaintingEntity> entityType, World world) {
		super(entityType, world);
	}

	public SpacePaintingEntity(World world, BlockPos pos, Direction direction) {
		this(ModEntities.SPACE_PAINTING, world);
		this.attachmentPos = pos;

		List<PaintingVariant> paintings = Lists.newArrayList(Registry.PAINTING_VARIANT.iterator());
		List<PaintingVariant> paintingsToRemove = new LinkedList<>();

		// Only keep paintings from this mod.
		for (PaintingVariant painting : paintings) {
			if (!Registry.PAINTING_VARIANT.getId(painting).getNamespace().equals(BeyondEarth.MOD_ID)) {
				paintingsToRemove.add(painting);
			}
		}
		paintings.removeAll(paintingsToRemove);

		PaintingVariant painting;
		ArrayList<PaintingVariant> possiblePaintings = Lists.newArrayList();
		int i = 0;
		Iterator<PaintingVariant> iterator = paintings.stream().iterator();

		while (iterator.hasNext()) {
			painting = (PaintingVariant) iterator.next();
			((PaintingEntityInvoker)this).invokeSetVariant(RegistryEntry.of(painting));
			this.setFacing(direction);
			if (!this.canStayAttached())
				continue;
			possiblePaintings.add(painting);
			int j = painting.getWidth() * painting.getHeight();
			if (j <= i)
				continue;
			i = j;
		}
		if (!possiblePaintings.isEmpty()) {
			Iterator<PaintingVariant> iterator2 = possiblePaintings.iterator();
			while (iterator2.hasNext()) {
				painting = (PaintingVariant) iterator2.next();
				if (painting.getWidth() * painting.getHeight() >= i)
					continue;
				iterator2.remove();
			}
			((PaintingEntityInvoker)this).invokeSetVariant(RegistryEntry.of(possiblePaintings.get(this.random.nextInt(possiblePaintings.size()))));
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
