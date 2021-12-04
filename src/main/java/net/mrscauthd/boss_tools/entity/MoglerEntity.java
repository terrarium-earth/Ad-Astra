package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.world.World;
import net.minecraft.network.IPacket;
import net.minecraft.entity.monster.HoglinEntity;
import net.mrscauthd.boss_tools.ModInnet;

import javax.annotation.Nullable;

public class MoglerEntity extends HoglinEntity {
    public MoglerEntity(EntityType<MoglerEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3)
                .createMutableAttribute(Attributes.MAX_HEALTH, 40)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.6)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        MoglerEntity moglerentity = (MoglerEntity) ModInnet.MOGLER.get().create(p_241840_1_);
        if (moglerentity != null) {
            moglerentity.enablePersistence();
        }

        return moglerentity;
    }
}
