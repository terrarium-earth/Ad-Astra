package com.github.alexnijjar.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.Camera;

@Mixin(Camera.class)
public interface CameraInvoker {

    @Invoker
    public void invokeMoveBy(double x, double y, double z);

    @Invoker
    public double invokeClipToSpace(double desiredCameraDistance);
}
