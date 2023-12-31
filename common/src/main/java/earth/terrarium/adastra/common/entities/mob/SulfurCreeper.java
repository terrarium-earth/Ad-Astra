package earth.terrarium.adastra.common.entities.mob;

import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
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

    @Override
    protected void explodeCreeper() {
        if (this.level().isClientSide) return;
        float power = isPowered() ? 2 : 1;
        this.dead = true;
        Explosion explosion = this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3 * power, Level.ExplosionInteraction.MOB);
        this.discard();

        for (Player player : explosion.getHitPlayers().keySet()) {
            var stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (SpaceSuitItem.hasFullSet(player)) {
                ItemStackHolder holder = new ItemStackHolder(stack);
                if (!FluidApi.isFluidContainingItem(stack)) continue;
                FluidContainer fluidContainer = FluidApi.getItemFluidContainer(holder);
                long amount = Math.max(0, (long) ((7 - player.getPosition(0).distanceTo(player.getPosition(0))) * (FluidHooks.buckets(1f) / 8)));
                FluidHolder toExtract = FluidHooks.newFluidHolder(fluidContainer.getFluids().get(0).getFluid(), amount, null);
                fluidContainer.extractFluid(toExtract, false);
                player.setItemSlot(EquipmentSlot.CHEST, holder.getStack());
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
