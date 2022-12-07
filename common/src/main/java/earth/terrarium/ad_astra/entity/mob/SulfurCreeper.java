package earth.terrarium.ad_astra.entity.mob;

import earth.terrarium.ad_astra.config.SpawnConfig;
import earth.terrarium.ad_astra.item.armor.SpaceSuit;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Collection;

public class SulfurCreeper extends Creeper {

    public SulfurCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    @Override
    protected void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction destructionType = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float f = this.isPowered() ? 2.0f : 1.0f;
            this.dead = true;
            Explosion explosion = this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2.5f * f, destructionType);
            for (Player player : explosion.getHitPlayers().keySet()) {
                ItemStackHolder chest = new ItemStackHolder(player.getItemBySlot(EquipmentSlot.CHEST));
                if (chest.getStack().getItem() instanceof SpaceSuit suit) {
                    if (explosion.getSourceMob() == null) return;
                    suit.extract(chest, FluidHooks.newFluidHolder(suit.getFluid(chest.getStack()), Math.max(0, (long) ((7 - player.getPosition(0).distanceTo(explosion.getSourceMob().getPosition(0))) * (FluidHooks.buckets(1) / 8))), null));
                }
                if (chest.isDirty()) player.setItemSlot(EquipmentSlot.CHEST, chest.getStack());
            }
            this.discard();
            Collection<MobEffectInstance> collection = this.getActiveEffects();
            if (!collection.isEmpty()) {
                AreaEffectCloud areaEffectCloudEntity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
                areaEffectCloudEntity.setRadius(2.5f);
                areaEffectCloudEntity.setRadiusOnUse(-0.5f);
                areaEffectCloudEntity.setWaitTime(10);
                areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
                areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
                for (MobEffectInstance statusEffectInstance : collection) {
                    areaEffectCloudEntity.addEffect(new MobEffectInstance(statusEffectInstance));
                }
                this.level.addFreshEntity(areaEffectCloudEntity);
            }
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnReason) {
        if (!SpawnConfig.spawnSulfurCreepers) {
            return false;
        }
        return super.checkSpawnRules(level, spawnReason);
    }
}
