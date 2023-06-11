package earth.terrarium.ad_astra.common.entity.mob.goal;

import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatPermafrostGoal extends Goal {
    private static final Predicate<BlockState> PERMAFROST_PREDICATE = BlockStatePredicate.forBlock(ModBlocks.PERMAFROST.get());
    private final Mob mob;
    private final Level level;
    private int timer;

    public EatPermafrostGoal(Mob mobEntity) {
        this.mob = mobEntity;
        this.level = mobEntity.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockPos = this.mob.blockPosition();
            if (PERMAFROST_PREDICATE.test(this.level.getBlockState(blockPos))) {
                return true;
            } else {
                return this.level.getBlockState(blockPos.below()).is(ModBlocks.PERMAFROST.get());
            }
        }
    }

    @Override
    public void start() {
        this.timer = this.adjustedTickDelay(40);
        this.level.broadcastEntityEvent(this.mob, (byte) 10);
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.timer = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer > 0;
    }

    public int getTimer() {
        return this.timer;
    }

    @Override
    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == this.adjustedTickDelay(4)) {
            BlockPos blockPos = this.mob.blockPosition();
            if (PERMAFROST_PREDICATE.test(this.level.getBlockState(blockPos))) {
                if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.level.destroyBlock(blockPos, false);
                }

                this.mob.ate();
            } else {
                BlockPos blockPos2 = blockPos.below();
                if (this.level.getBlockState(blockPos2).is(ModBlocks.PERMAFROST.get())) {
                    if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        this.level.levelEvent(2001, blockPos2, Block.getId(ModBlocks.PERMAFROST.get().defaultBlockState()));
                        this.level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 2);
                    }

                    this.mob.ate();
                }
            }

        }
    }
}
