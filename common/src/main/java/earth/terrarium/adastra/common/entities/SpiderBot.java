package earth.terrarium.adastra.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class SpiderBot extends PathfinderMob implements GeoEntity {

    public static final RawAnimation SITTING_IDLE = RawAnimation.begin().thenLoop("animation.model.sitting.idle");
    public static final RawAnimation STANDING_UP = RawAnimation.begin().thenPlay("animation.model.sitting.stand.up").thenLoop("animation.model.sitting.idle");
    public static final RawAnimation STANDING_IDLE = RawAnimation.begin().thenLoop("animation.model.standing.idle");
    public static final RawAnimation WALKING = RawAnimation.begin().thenLoop("animation.model.walking");
    public static final RawAnimation SITTING_DOWN = RawAnimation.begin().thenPlay("animation.model.sitting.stand.down").thenLoop("animation.model.sitting.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    private UUID owner;

    public SpiderBot(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            if (state.isMoving() && this.onGround()) {
                return state.setAndContinue(WALKING);
            } else {
                return state.setAndContinue(STANDING_IDLE);
            }
        }).setSoundKeyframeHandler(event -> {
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0.35));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Owner")) {
            this.owner = compound.getUUID("Owner");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.owner != null) {
            compound.putUUID("Owner", this.owner);
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.owner == null) {
            this.owner = player.getUUID();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
