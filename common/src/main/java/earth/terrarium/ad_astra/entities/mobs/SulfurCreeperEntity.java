package earth.terrarium.ad_astra.entities.mobs;

import java.util.Collection;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.util.OxygenUtils;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class SulfurCreeperEntity extends CreeperEntity {

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35);
	}

	public SulfurCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void explode() {
		if (!this.world.isClient) {
			Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
			float f = this.isOverlayConditionMet() ? 2.0f : 1.0f;
			this.dead = true;
			Explosion explosion = this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.5f * f, destructionType);
			for (PlayerEntity player : explosion.getAffectedPlayers().keySet()) {
				ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
				if (chest.getItem() instanceof SpaceSuit suit) {
					long oxygen = suit.getAmount(chest);

					if (oxygen > 0) {
						if (!OxygenUtils.entityHasOxygen(world, player)) {
							suit.setAmount(chest, oxygen - FluidHooks.BUCKET / 3);
							if ((suit.getAmount(chest) <= 0)) {
								suit.setAmount(chest, 0);
							}
						}
					}
				}
			}
			this.discard();
			Collection<StatusEffectInstance> collection = this.getStatusEffects();
			if (!collection.isEmpty()) {
				AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
				areaEffectCloudEntity.setRadius(2.5f);
				areaEffectCloudEntity.setRadiusOnUse(-0.5f);
				areaEffectCloudEntity.setWaitTime(10);
				areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
				areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
				for (StatusEffectInstance statusEffectInstance : collection) {
					areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
				}
				this.world.spawnEntity(areaEffectCloudEntity);
			}
		}
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		if (!AdAstra.CONFIG.general.spawnSulfurCreepers) {
			return false;
		}
		return super.canSpawn(world, spawnReason);
	}
}
