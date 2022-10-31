package earth.terrarium.ad_astra.entities.mobs;

import earth.terrarium.ad_astra.registry.ModEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;

public class PygroBruteEntity extends PiglinBrute {

    public PygroBruteEntity(EntityType<? extends PiglinBrute> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.45).add(Attributes.MAX_HEALTH, 60).add(Attributes.ATTACK_DAMAGE, 9);
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    protected void finishConversion(ServerLevel level) {
        ZombifiedPygroEntity zombifiedPygroEntity = this.convertTo(ModEntityTypes.ZOMBIFIED_PYGRO.get(), true);
        if (zombifiedPygroEntity != null) {
            zombifiedPygroEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        }
    }
}
