package net.mrscauthd.beyond_earth.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.beyond_earth.ModInit;

import javax.annotation.Nullable;

public class MoglerEntity extends Hoglin {
    public MoglerEntity(EntityType<MoglerEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_KNOCKBACK, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 6);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_149900_, AgeableMob p_149901_) {
        MoglerEntity moglerentity = (MoglerEntity) ModInit.MOGLER.get().create(p_149900_);
        if (moglerentity != null) {
            moglerentity.setPersistenceRequired();
        }
        return moglerentity;
    }
}
