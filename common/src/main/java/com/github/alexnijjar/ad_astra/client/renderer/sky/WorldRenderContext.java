/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.alexnijjar.ad_astra.client.renderer.sky;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

/**
 * Except as noted below, the properties exposed here match the parameters passed to
 * {@link WorldRenderer#render(MatrixStack, float, long, boolean, Camera, GameRenderer, LightmapTextureManager, Matrix4f)}.
 */
@Environment(EnvType.CLIENT)
public interface WorldRenderContext {
	/**
	 * The world renderer instance doing the rendering and invoking the event.
	 *
	 * @return WorldRenderer instance invoking the event
	 */
	WorldRenderer worldRenderer();

	MatrixStack matrixStack();

	float tickDelta();

	Camera camera();

	GameRenderer gameRenderer();

	LightmapTextureManager lightmapTextureManager();

	Matrix4f projectionMatrix();

	/**
	 * Convenient access to {WorldRenderer.world}.
	 *
	 * @return world renderer's client world instance
	 */
	ClientWorld world();

	/**
	 * Test to know if "fabulous" graphics mode is enabled.
	 *
	 * <p>Use this for renders that need to render on top of all translucency to activate or deactivate different
	 * event handlers to get optimal depth testing results. When fabulous is off, it may be better to render
	 * during {@code WorldRenderLastCallback} after clouds and weather are drawn. Conversely, when fabulous mode is on,
	 * it may be better to draw during {@code WorldRenderPostTranslucentCallback}, before the fabulous mode composite
	 * shader runs, depending on which translucent buffer is being targeted.
	 *
	 * @return {@code true} when "fabulous" graphics mode is enabled.
	 */
	boolean advancedTranslucency();

	/**
	 * Used in {@code BLOCK_OUTLINE} to convey the parameters normally sent to
	 * {@code WorldRenderer.drawBlockOutline}.
	 */
	@Environment(EnvType.CLIENT)
	interface BlockOutlineContext {
		@Deprecated
		VertexConsumer vertexConsumer();

		Entity entity();

		BlockPos blockPos();

		BlockState blockState();
	}
}
