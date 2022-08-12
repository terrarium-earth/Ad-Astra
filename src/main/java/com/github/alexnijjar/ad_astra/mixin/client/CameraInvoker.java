package com.github.alexnijjar.ad_astra.mixin.client;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraInvoker {

	@Invoker
	void invokeMoveBy(double x, double y, double z);

	@Invoker
	double invokeClipToSpace(double desiredCameraDistance);
}
