package earth.terrarium.adastra.common.entities.mob;

import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class SulfurCreeper extends Creeper {

    public SulfurCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    public void explodeCreeper() {
        if (this.level().isClientSide) return;
        float power = isPowered() ? 2 : 1;
        this.dead = true;
        Explosion explosion = this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3 * power, Level.ExplosionInteraction.MOB);
        this.discard();

        for (Player player : explosion.getHitPlayers().keySet()) {
            var stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (SpaceSuitItem.hasFullSet(player)) {
                ModifyOnlyContext itemContext = new ModifyOnlyContext(stack);
                if (!itemContext.isPresent(FluidApi.ITEM)) continue;
                var container = itemContext.find(FluidApi.ITEM);
                if (container == null) continue;
                long amount = Math.max(0, (long) ((7 - player.getPosition(0).distanceTo(player.getPosition(0))) * (FluidAmounts.toPlatformAmount(125))));
                container.extract(container.getResource(0), amount, false);
                player.setItemSlot(EquipmentSlot.CHEST, itemContext.stack());
            }
        }

        Collection<MobEffectInstance> effects = this.getActiveEffects();
        if (!effects.isEmpty()) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaEffectCloud.setRadius(2.5F);
            areaEffectCloud.setRadiusOnUse(-0.5F);
            areaEffectCloud.setWaitTime(10);
            areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
            areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());

            for (MobEffectInstance mobEffectInstance : effects) {
                areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));
            }

            this.level().addFreshEntity(areaEffectCloud);
        }
    }
}
