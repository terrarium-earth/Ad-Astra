package net.mrscauthd.boss_tools.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.mrscauthd.boss_tools.ModInnet;

import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class AlienSpitEntity extends AbstractArrow implements ItemSupplier {
    public AlienSpitEntity(EntityType<? extends AlienSpitEntity> type, Level world) {
        super(type, world);
    }

    public AlienSpitEntity(EntityType<? extends AlienSpitEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(ModInnet.ICE_SHARD.get(), 1);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return null;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity p_36744_) {
        super.doPostHurtEffects(p_36744_);
        p_36744_.setArrowCount(p_36744_.getArrowCount() - 1);
    }

    @Override
    public void tick() {
        super.tick();
        double x = this.getY();
        double y = this.getY();
        double z = this.getZ();
        Level world = this.level;
        Entity entity = this.getOwner();
        world.addParticle(ParticleTypes.SPIT, x, y, z, 0, 0.001, 0);
        world.addParticle(ParticleTypes.ITEM_SNOWBALL, x, y, z, 0, 0.001, 0);
        if (this.inGround) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public static AlienSpitEntity shoot(LivingEntity entity, LivingEntity target, int damage) {
        AlienSpitEntity entityarrow = new AlienSpitEntity(ModInnet.ALIEN_SPIT_ENTITY.get(), entity, entity.level);
        double d0 = target.getY() + (double) target.getEyeHeight() - 1.1;
        double d1 = target.getX() - entity.getX();
        double d3 = target.getZ() - entity.getZ();
        entityarrow.shoot(d1, d0 - entityarrow.getY() + (double) Math.sqrt(d1 * d1 + d3 * d3) * 0.2F, d3, 0.7f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(1);
        entityarrow.setCritArrow(false);
        entity.level.addFreshEntity(entityarrow);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        entity.level.playSound(null, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundSource.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
