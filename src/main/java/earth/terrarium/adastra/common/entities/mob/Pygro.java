package earth.terrarium.adastra.common.entities.mob;

import earth.terrarium.adastra.common.registry.ModEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class Pygro extends Piglin {

    public Pygro(EntityType<? extends AbstractPiglin> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.45)
            .add(Attributes.MAX_HEALTH, 24)
            .add(Attributes.ATTACK_DAMAGE, 6);
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    protected void finishConversion(ServerLevel level) {
        ZombifiedPygro zombifiedPygroEntity = this.convertTo(ModEntityTypes.ZOMBIFIED_PYGRO.get(), true);
        if (zombifiedPygroEntity != null) {
            zombifiedPygroEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        }
    }
}
