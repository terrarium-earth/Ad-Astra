package com.github.alexnijjar.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Holder;

@Mixin(PaintingEntity.class)
public interface PaintingEntityInvoker {
    @Invoker("setVariant")
    public void adastra_invokeSetVariant(Holder<PaintingVariant> variant);
}
