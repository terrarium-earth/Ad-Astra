package earth.terrarium.ad_astra.common.entity.mob;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.Level;

public class ZombifiedPygro extends ZombifiedPiglin {

    public ZombifiedPygro(EntityType<? extends ZombifiedPiglin> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 28.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0).add(Attributes.MOVEMENT_SPEED, 0.26f).add(Attributes.ATTACK_DAMAGE, 8.0);
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }
}
