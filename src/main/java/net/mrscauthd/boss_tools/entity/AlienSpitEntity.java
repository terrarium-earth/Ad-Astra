package net.mrscauthd.boss_tools.entity;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.IPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;
import net.mrscauthd.boss_tools.ModInnet;

import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class AlienSpitEntity extends AbstractArrowEntity implements IRendersAsItem {
    public AlienSpitEntity(EntityType<? extends AlienSpitEntity> type, World world) {
        super(type, world);
    }

    public AlienSpitEntity(EntityType<? extends AlienSpitEntity> type, LivingEntity entity, World world) {
        super(type, entity, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(ModInnet.ICE_SHARD.get(), 1);
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

    @Override
    protected SoundEvent getHitEntitySound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        entity.setArrowCountInEntity(entity.getArrowCountInEntity() - 1);
    }

    @Override
    public void tick() {
        super.tick();
        double x = this.getPosX();
        double y = this.getPosY();
        double z = this.getPosZ();
        World world = this.world;
        Entity entity = this.func_234616_v_();
        world.addParticle(ParticleTypes.SPIT, x, y, z, 0, 0.001, 0);
        world.addParticle(ParticleTypes.ITEM_SNOWBALL, x, y, z, 0, 0.001, 0);
        if (this.inGround) {
            this.remove();
        }
    }

    public static AlienSpitEntity shoot(LivingEntity entity, LivingEntity target, int damage) {
        AlienSpitEntity entityarrow = new AlienSpitEntity(ModInnet.ALIEN_SPIT_ENTITY.get(), entity, entity.world);
        double d0 = target.getPosY() + (double) target.getEyeHeight() - 1.1;
        double d1 = target.getPosX() - entity.getPosX();
        double d3 = target.getPosZ() - entity.getPosZ();
        entityarrow.shoot(d1, d0 - entityarrow.getPosY() + (double) MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F, d3, 0.7f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setDamage(damage);
        entityarrow.setKnockbackStrength(1);
        entityarrow.setIsCritical(false);
        entity.world.addEntity(entityarrow);
        double x = entity.getPosX();
        double y = entity.getPosY();
        double z = entity.getPosZ();
        entity.world.playSound(null, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.shoot")), SoundCategory.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
