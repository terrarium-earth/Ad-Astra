package com.github.alexnijjar.ad_astra.entities.mobs.goal;

import java.util.EnumSet;
import java.util.function.Predicate;

import com.github.alexnijjar.ad_astra.registry.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EatPermafrostGoal extends Goal {
	private static final Predicate<BlockState> PERMAFROST_PREDICATE = BlockStatePredicate.forBlock(ModBlocks.PERMAFROST);
	private final MobEntity mob;
	private final World world;
	private int timer;

	public EatPermafrostGoal(MobEntity mobEntity) {
		this.mob = mobEntity;
		this.world = mobEntity.world;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.JUMP));
	}

	@Override
	public boolean canStart() {
		if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
			return false;
		} else {
			BlockPos blockPos = this.mob.getBlockPos();
			if (PERMAFROST_PREDICATE.test(this.world.getBlockState(blockPos))) {
				return true;
			} else {
				return this.world.getBlockState(blockPos.down()).isOf(ModBlocks.PERMAFROST);
			}
		}
	}

	@Override
	public void start() {
		this.timer = this.getTickCount(40);
		this.world.sendEntityStatus(this.mob, (byte) 10);
		this.mob.getNavigation().stop();
	}

	@Override
	public void stop() {
		this.timer = 0;
	}

	@Override
	public boolean shouldContinue() {
		return this.timer > 0;
	}

	public int getTimer() {
		return this.timer;
	}

	@Override
	public void tick() {
		this.timer = Math.max(0, this.timer - 1);
		if (this.timer == this.getTickCount(4)) {
			BlockPos blockPos = this.mob.getBlockPos();
			if (PERMAFROST_PREDICATE.test(this.world.getBlockState(blockPos))) {
				if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
					this.world.breakBlock(blockPos, false);
				}

				this.mob.onEatingGrass();
				this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
			} else {
				BlockPos blockPos2 = blockPos.down();
				if (this.world.getBlockState(blockPos2).isOf(ModBlocks.PERMAFROST)) {
					if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
						this.world.syncWorldEvent(2001, blockPos2, Block.getRawIdFromState(ModBlocks.PERMAFROST.getDefaultState()));
						this.world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 2);
					}

					this.mob.onEatingGrass();
					this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
				}
			}

		}
	}
}
