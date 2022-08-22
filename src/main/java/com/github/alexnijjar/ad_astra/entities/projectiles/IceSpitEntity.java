package com.github.alexnijjar.ad_astra.entities.projectiles;

import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceSpitEntity extends ThrownItemEntity {

    public IceSpitEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceSpitEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ICE_SHARD;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Vec3d vec = this.getVelocity();

        this.world.addParticle(ParticleTypes.SPIT, x - vec.getX(), y - vec.getY(), z - vec.getZ(), 0, 0.001, 0);
        this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, x - vec.getX(), y - vec.getY(), z - vec.getZ(), 0, 0.001, 0);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 4);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }
}
