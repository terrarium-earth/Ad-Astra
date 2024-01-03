package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, AdAstra.MOD_ID);

    public static final RegistryEntry<SoundEvent> ROCKET_LAUNCH = SOUND_EVENTS.register("rocket_launch", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "rocket_launch")));

    public static final RegistryEntry<SoundEvent> ROCKET = SOUND_EVENTS.register("rocket", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "rocket")));

    public static final RegistryEntry<SoundEvent> WRENCH = SOUND_EVENTS.register("wrench", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "wrench")));

    public static final RegistryEntry<SoundEvent> SLIDING_DOOR_CLOSE = SOUND_EVENTS.register("sliding_door_close", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "sliding_door_close")));

    public static final RegistryEntry<SoundEvent> SLIDING_DOOR_OPEN = SOUND_EVENTS.register("sliding_door_open", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "sliding_door_open")));

    public static final RegistryEntry<SoundEvent> OXYGEN_INTAKE = SOUND_EVENTS.register("oxygen_intake", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "oxygen_intake")));

    public static final RegistryEntry<SoundEvent> OXYGEN_OUTTAKE = SOUND_EVENTS.register("oxygen_outtake", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "oxygen_outtake")));

    public static final RegistryEntry<SoundEvent> GRAVITY_NORMALIZER_IDLE = SOUND_EVENTS.register("gravity_normalizer_idle", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "gravity_normalizer_idle")));
}
