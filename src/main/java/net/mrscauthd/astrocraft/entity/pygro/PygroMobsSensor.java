package net.mrscauthd.astrocraft.entity.pygro;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PygroMobsSensor extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_REPELLENT);
    }

    @Override
    protected void doTick(ServerLevel worldIn, LivingEntity entityIn) {
        Brain<?> brain = entityIn.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent(worldIn, entityIn));
        Optional<Mob> optional = Optional.empty();
        Optional<Hoglin> optional1 = Optional.empty();
        Optional<Hoglin> optional2 = Optional.empty();
        Optional<Piglin> optional3 = Optional.empty();
        Optional<LivingEntity> optional4 = Optional.empty();
        Optional<Player> optional5 = Optional.empty();
        Optional<Player> optional6 = Optional.empty();
        int i = 0;
        List<AbstractPiglin> list = Lists.newArrayList();
        List<AbstractPiglin> list1 = Lists.newArrayList();
        NearestVisibleLivingEntities nearestvisiblelivingentities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());

        for(LivingEntity livingentity : nearestvisiblelivingentities.findAll((p_186157_) -> {
            return true;
        })) {
            if (livingentity instanceof Hoglin) {
                Hoglin hoglinentity = (Hoglin)livingentity;
                if (hoglinentity.isBaby() && !optional2.isPresent()) {
                    optional2 = Optional.of(hoglinentity);
                } else if (hoglinentity.isAdult()) {
                    ++i;
                    if (!optional1.isPresent() && hoglinentity.canBeHunted()) {
                        optional1 = Optional.of(hoglinentity);
                    }
                }
            } else if (livingentity instanceof PiglinBrute) {
                list.add((PiglinBrute)livingentity);
            } else if (livingentity instanceof Piglin) {
                Piglin piglinentity = (Piglin)livingentity;
                if (piglinentity.isBaby() && !optional3.isPresent()) {
                    optional3 = Optional.of(piglinentity);
                } else if (piglinentity.isAdult()) {
                    list.add(piglinentity);
                }
            } else if (livingentity instanceof Player) {
                Player playerentity = (Player)livingentity;

                if (!optional5.isPresent() && false && !PiglinAi.isWearingGold(playerentity)) {
                    optional5 = Optional.of(playerentity);
                }

                if (!optional6.isPresent() && !playerentity.isSpectator() && PiglinAi.isPlayerHoldingLovedItem(playerentity)) {
                    optional6 = Optional.of(playerentity);
                }
            } else if (optional.isPresent() || !(livingentity instanceof WitherSkeleton) && !(livingentity instanceof WitherBoss)) {
                if (!optional4.isPresent() && PiglinAi.isZombified(livingentity.getType())) {
                    optional4 = Optional.of(livingentity);
                }
            } else {
                optional = Optional.of((Mob)livingentity);
            }
        }

        for(LivingEntity livingentity1 : brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of())) {
            if (livingentity1 instanceof AbstractPiglin && ((AbstractPiglin)livingentity1).isAdult()) {
                list1.add((AbstractPiglin)livingentity1);
            }
        }

        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, optional1);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, optional2);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, optional4);
        brain.setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, optional5);
        brain.setMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, optional6);
        brain.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, list1);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, list);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, list.size());
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, i);
    }

    private static Optional<BlockPos> findNearestRepellent(ServerLevel world, LivingEntity livingEntity) {
        return BlockPos.findClosestMatch(new BlockPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()), 8, 4, (pos) -> {
            return isRepellent(world, pos);
        });
    }

    private static boolean isRepellent(ServerLevel world, BlockPos pos) {
        BlockState blockstate = world.getBlockState(pos);
        boolean flag = blockstate.is(BlockTags.PIGLIN_REPELLENTS);
        return flag && blockstate.is(Blocks.SOUL_CAMPFIRE) ? CampfireBlock.isLitCampfire(blockstate) : flag;
    }
}