package net.mrscauthd.beyond_earth.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.events.Config;

public class PygroBruteEntity extends PiglinBrute {
    public PygroBruteEntity(EntityType<? extends PiglinBrute> p_35048_, Level p_35049_) {
        super(p_35048_, p_35049_);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.ATTACK_DAMAGE, 5);
    }

    @Override
    public void tick() {
        super.tick();
        if (!Config.PYGRO_BRUTE_SPAWN.get()) {
            if (!this.level.isClientSide) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
}
