package earth.terrarium.ad_astra.entity.mob;

import earth.terrarium.ad_astra.config.SpawnConfig;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Mogler extends Hoglin {

    public Mogler(EntityType<? extends Hoglin> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 50.0).add(Attributes.MOVEMENT_SPEED, 0.4f).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_KNOCKBACK, 1.0).add(Attributes.ATTACK_DAMAGE, 8.0);
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        Mogler moglerEntity = ModEntityTypes.MOGLER.get().create(level);
        if (moglerEntity != null) {
            moglerEntity.setPersistenceRequired();
        }
        return moglerEntity;
    }

    @Override
    public void finishConversion(ServerLevel level) {
        ZombifiedMogler zombifiedMoglerEntity = this.convertTo(ModEntityTypes.ZOMBIFIED_MOGLER.get(), true);
        if (zombifiedMoglerEntity != null) {
            zombifiedMoglerEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnReason) {
        if (!SpawnConfig.spawnMoglers) {
            return false;
        }
        return super.checkSpawnRules(level, spawnReason);
    }
}
