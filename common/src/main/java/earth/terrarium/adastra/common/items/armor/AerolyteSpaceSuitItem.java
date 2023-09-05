package earth.terrarium.adastra.common.items.armor;

import earth.terrarium.adastra.api.upgrades.Upgradable;
import earth.terrarium.adastra.common.items.base.CustomGeoArmorItem;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.utils.KeybindManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Set;
import java.util.stream.StreamSupport;

public class AerolyteSpaceSuitItem extends CustomGeoArmorItem implements Upgradable {

    public static final RawAnimation IDLE_CLOSED = RawAnimation.begin().thenLoop("animation.model.idle.closed");
    public static final RawAnimation IDLE_OPEN = RawAnimation.begin().thenLoop("animation.model.idle.open");
    public static final RawAnimation OPEN = RawAnimation.begin().thenPlayAndHold("animation.model.open");
    public static final RawAnimation CLOSE = RawAnimation.begin().thenPlayAndHold("animation.model.close");

    public AerolyteSpaceSuitItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            Entity entity = state.getData(DataTickets.ENTITY);
            if (!(entity instanceof LivingEntity livingEntity)) return PlayState.STOP;

            boolean openWings = livingEntity.isFallFlying() || livingEntity.fallDistance > 4.0f;
            state.getController().setAnimation(openWings ? OPEN : CLOSE);

            if (entity instanceof ArmorStand) return PlayState.CONTINUE;

            Set<Item> wornArmor = new ObjectOpenHashSet<>();

            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty()) return PlayState.STOP;
                wornArmor.add(stack.getItem());
            }

            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
                ModItems.AEROLYTE_SPACE_HELMET.get(),
                ModItems.AEROLYTE_SPACE_SUIT.get(),
                ModItems.AEROLYTE_SPACE_PANTS.get(),
                ModItems.AEROLYTE_SPACE_BOOTS.get()));

            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (this.type != Type.CHESTPLATE) return;
        if (entity instanceof Player player) {
            if (player.getAbilities().flying) return;
            if (!KeybindManager.suitFlightEnabled(player)) return;
            if (!KeybindManager.jumpDown(player)) return;
            if (!hasFullSet(player)) return;

            if (KeybindManager.sprintDown(player)) {
                this.fullFlight(level, player);
            } else {
                this.upwardsFlight(level, player);
            }
        }
    }

    protected void upwardsFlight(Level level, Player player) {
        double currentTime = level.getGameTime() / 20.0;
        double acceleration = sigmoidAcceleration(currentTime, 5.0, 1.0, 2.0);
        acceleration /= 25.0f;

        player.addDeltaMovement(new Vec3(0, Math.max(0.0025, acceleration), 0));
        player.fallDistance = Math.max(player.fallDistance / 1.5f, 0.0f);
    }

    protected void fullFlight(Level level, Player player) {
        Vec3 lookAngle = player.getLookAngle();
        Vec3 movement = new Vec3(lookAngle.x, 0, lookAngle.z).normalize().scale(0.075);
        if (player.getDeltaMovement().length() > 3.0) {
            return;
        }
        player.addDeltaMovement(movement);
        player.fallDistance = Math.max(player.fallDistance / 1.5f, 0.0f);
        if (!player.isFallFlying()) {
            player.startFallFlying();
        }
    }

    public static double sigmoidAcceleration(double t, double peakTime, double peakAcceleration, double initialAcceleration) {
        return ((2 * peakAcceleration) / (1 + Math.exp(-t / peakTime)) - peakAcceleration) + initialAcceleration;
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false)
            .allMatch(stack -> stack.getItem() instanceof AerolyteSpaceSuitItem);
    }

    protected boolean isFullFlightEnabled(Player player) {
        return KeybindManager.suitFlightEnabled(player) && KeybindManager.jumpDown(player) && KeybindManager.sprintDown(player);
    }

    @Override
    public Set<ResourceLocation> getUpgrades() {
        return Set.of(
        );
    }

    @SuppressWarnings("unused") // Forge
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (entity.level().isClientSide()) return true;
        if (this.type != Type.CHESTPLATE) return true;
        int nextFlightTick = flightTicks + 1;
        if (nextFlightTick % 10 != 0) return true;

        if (nextFlightTick % 20 == 0) {
            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
        }

        entity.gameEvent(GameEvent.ELYTRA_GLIDE);

        return true;
    }

    @SuppressWarnings("unused") // Forge
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return entity instanceof Player p && isFullFlightEnabled(p);
    }
}
