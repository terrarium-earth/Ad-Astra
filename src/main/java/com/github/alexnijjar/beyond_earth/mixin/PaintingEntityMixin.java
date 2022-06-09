package com.github.alexnijjar.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.decoration.painting.PaintingEntity;

@Mixin(PaintingEntity.class)
public class PaintingEntityMixin {

	// @ModifyVariable(method = "Lnet/minecraft/entity/decoration/painting/PaintingEntity;<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)V", at = @At("STORE"), ordinal = 0)
	// private Iterator<PaintingVariant> modifyIterator(Iterator<PaintingVariant> iterator) {
	// 	List<PaintingVariant> paintings = Lists.newArrayList(iterator);
	// 	List<PaintingVariant> paintingsToRemove = new LinkedList<>();

	// 	// Remove the paintings from this mod for the minecraft painting item.
	// 	for (PaintingVariant painting : paintings) {
	// 		if (Registry.PAINTING_VARIANT.getId(painting).getNamespace().equals(BeyondEarth.MOD_ID)) {
	// 			paintingsToRemove.add(painting);
	// 		}
	// 	}
	// 	paintings.removeAll(paintingsToRemove);

	// 	return paintings.listIterator();
	// }
}
