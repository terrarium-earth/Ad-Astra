package com.github.alexnijjar.ad_astra.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {

	@Invoker("isBeingRainedOn")
	boolean invokeIsBeingRainedOn();
}