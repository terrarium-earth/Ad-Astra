package earth.terrarium.adastra.common.entities.vehicles;

import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.common.menus.vehicles.RoverMenu;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundPlayStationPacket;
import earth.terrarium.adastra.common.registry.ModDamageSources;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class Rover extends Vehicle implements PlayerRideable, RadioHolder {
    private static final float MAX_SPEED_KM = 50.0f;
    private static final float ACCELERATION_RATE = 0.02f;

    public static final EntityDataAccessor<Long> FUEL = SynchedEntityData.defineId(Rover.class, EntityDataSerializers.LONG);
    public static final EntityDataAccessor<String> FUEL_TYPE = SynchedEntityData.defineId(Rover.class, EntityDataSerializers.STRING);

    private final SimpleFluidContainer fluidContainer = new SimpleFluidContainer(FluidHooks.buckets(3), 1, (amount, fluid) -> fluid.is(ModFluidTags.TIER_1_ROVER_FUEL));

    private float speed;
    private float angle;

    public float wheelXRot;
    public float wheelYRot;

    private String radioUrl = "";

    public Rover(EntityType<?> type, Level level) {
        super(type, level);
        setMaxUpStep(1.0f);

        addPart(0.6f, 0.7f, new Vector3f(0.6f, 1f, 0.5f), (player, hand) -> {
            if (player.getVehicle() instanceof Rover) {
                if (player.level().isClientSide()) {
                    RadioHandler.open(null);
                }
                return InteractionResult.sidedSuccess(player.level().isClientSide());
            }
            return InteractionResult.PASS;
        });

        addPart(1.1f, 0.7f, new Vector3f(0.15f, 0.8f, -1.7f), (player, hand) -> {
            if (!level().isClientSide()) {
                this.openCustomInventoryScreen(player);
            }
            return InteractionResult.sidedSuccess(level().isClientSide());
        });
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FUEL, 0L);
        this.entityData.define(FUEL_TYPE, "air");
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        speed = compound.getFloat("Speed");
        angle = compound.getFloat("Angle");
        fluidContainer.deserialize(compound);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("Speed", speed);
        compound.putFloat("Angle", angle);
        fluidContainer.serialize(compound);
    }

    public FluidContainer fluidContainer() {
        return fluidContainer;
    }

    @Override
    public ItemStack getDropStack() {
        ItemStackHolder stack = new ItemStackHolder(ModItems.ROVER.get().getDefaultInstance());
        FluidApi.moveFluid(fluidContainer, FluidApi.getItemFluidContainer(stack), fluidContainer.getFluids().get(0), false);
        return stack.getStack();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    private void clampRotation(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(getYRot());
        float degrees = Mth.wrapDegrees(entityToUpdate.getYRot() - getYRot());
        float lookAngle = Mth.clamp(degrees, -105.0f, 105.0f);
        entityToUpdate.yRotO += lookAngle - degrees;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + lookAngle - degrees);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity entityToUpdate) {
        clampRotation(entityToUpdate);
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        float zOffset = getControllingPassenger() == passenger ? 1.75f : -1.75f;
        Vec3 position = new Vec3(-0.5, 0, zOffset).yRot(-getYRot() * (float) (Math.PI / 180) - (float) (Math.PI / 2));
        if (level().isClientSide()) {
            RadioHandler.stop();
        }
        return new Vec3(getX() + position.x, getY(), getZ() + position.z);
    }

    @Override
    public boolean isSafeToDismount(Player player) {
        return speed() < 0.1f;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        if (!hasPassenger(passenger)) return;

        float zOffset = getControllingPassenger() == passenger ? -0.6f : 0.4f;
        float yOffset = (float) ((this.isRemoved() ? 0.01f : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        Vec3 position = new Vec3(-0.5, 0, zOffset).yRot(-getYRot() * (float) (Math.PI / 180) - (float) (Math.PI / 2));

        clampRotation(passenger);
        passenger.setYRot(passenger.getYRot() + angle);
        passenger.setYHeadRot(passenger.getYHeadRot() + angle);
        callback.accept(passenger, getX() + position.x, getY() + yOffset, getZ() + position.z);
    }

    @Override
    public void tick() {
        super.tick();
        handleVehicleMovementTick();
        doEntityCollisionTick();
        if (!level().isClientSide()) {
            FluidUtils.moveItemToContainer(inventory, fluidContainer, 0, 1, 0);
            FluidUtils.moveContainerToItem(inventory, fluidContainer, 0, 1, 0);

            var fluidHolder = fluidContainer.getFluids().get(0);
            entityData.set(FUEL, fluidHolder.getFluidAmount());
            entityData.set(FUEL_TYPE, BuiltInRegistries.FLUID.getKey(fluidHolder.getFluid()).toString());
        }
    }

    private void handleVehicleMovementTick() {
        boolean noPassenger = getControllingPassenger() == null;
        float xxa = -xxa(); // right/left
        float zza = zza(); // forward/backward

        if (!onGround()) {
            xxa *= 0.2f;
            zza *= 0.2f;
        }

        if (!hasEnoughFuel()) {
            xxa = 0;
            zza = 0;
        }

        // acceleration
        if (zza != 0) {
            speed += ACCELERATION_RATE * zza;
        } else {
            speed *= noPassenger ? 0.98f : 0.96f;
        }

        if (noPassenger && speed < 0.1f && speed > -0.1f) {
            speed *= 0.9f;
        }

        // clamp speed
        float maxBlocksPerTick = MAX_SPEED_KM / 20.0f / 3.6f;
        speed = Mth.clamp(speed, -maxBlocksPerTick / 2, maxBlocksPerTick);

        // turning
        if (xxa != 0 && (speed > 0.05f || speed < -0.05f)) {
            angle += xxa * Math.signum(speed) * Math.abs(speed);
        } else {
            angle *= noPassenger ? 0.95f : 0.75f;
        }

        // clamp turning
        angle = Mth.clamp(angle, -3, 3);

        // handle turning
        setYRot(getYRot() + angle);

        // handle speed
        float yRot = getYRot() * (float) (Math.PI / 180);
        setDeltaMovement(
            Mth.sin(-yRot) * speed,
            getDeltaMovement().y,
            Mth.cos(yRot) * speed
        );

        if (zza > 0) consumeFuel();
    }


    // run over entities, launching and damaging them
    private void doEntityCollisionTick() {
        if (level().isClientSide()) return;
        if (getDeltaMovement().length() <= 0.15) return;
        AABB aabb = getBoundingBox().inflate(1.001);
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, aabb, entity -> !getPassengers().contains(entity));
        if (entities.isEmpty()) return;

        double power = getDeltaMovement().length() * 0.4;
        float damage = (float) (power * 0.5f) * 100;
        var yRot = getYRot() * (float) (Math.PI / 180);
        for (var entity : entities) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(Mth.sin(-yRot) * 0.1, power, Mth.cos(yRot) * 0.1));
            entity.hurt(ModDamageSources.ranOver(level(), this, getControllingPassenger()), damage);
        }
    }

    public float speed() {
        return speed;
    }

    public float angle() {
        return angle;
    }

    @Override
    public @NotNull String getRadioUrl() {
        return radioUrl;
    }

    @Override
    public void setRadioUrl(@NotNull String url) {
        this.radioUrl = url;

        for (Entity passenger : getPassengers()) {
            if (passenger instanceof Player player) {
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundPlayStationPacket(url), player);
            }
        }
    }

    public void consumeFuel() {
        if (level().isClientSide() || tickCount % 5 != 0) return;
        var toExtract = FluidHooks.newFluidHolder(fluidContainer.getFluids().get(0).getFluid(), FluidHooks.buckets(0.001), null);
        fluidContainer.extractFluid(toExtract, false);
    }

    public boolean hasEnoughFuel() {
        if (level().isClientSide()) {
            return entityData.get(FUEL) > 0;
        }
        return fluidContainer.getFluids().get(0).getFluidAmount() > 0;
    }

    public FluidHolder fluid() {
        return FluidHooks.newFluidHolder(
            BuiltInRegistries.FLUID.get(new ResourceLocation(entityData.get(FUEL_TYPE))),
            entityData.get(FUEL),
            null);
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new RoverMenu(containerId, inventory, this);
    }

    @Override
    public int getInventorySize() {
        return 18;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.ROVER.get());
    }
}
