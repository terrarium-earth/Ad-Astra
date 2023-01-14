package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<SoundEvent> SPACE_SOUNDS = ResourcefulRegistries.create(SOUND_EVENTS);
    public static final ResourcefulRegistry<SoundEvent> PLANET_SOUNDS = ResourcefulRegistries.create(SOUND_EVENTS);

    public static final RegistryEntry<SoundEvent> ROCKET_LAUNCH_SOUND_EVENT = SOUND_EVENTS.register("rocket_fly", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "rocket_fly")));
    public static final RegistryEntry<SoundEvent> WRENCH = SOUND_EVENTS.register("wrench", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "wrench")));
    public static final RegistryEntry<SoundEvent> WINDY = SOUND_EVENTS.register("windy", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "windy")));

    public static final RegistryEntry<SoundEvent> ALIEN_WATCHER = PLANET_SOUNDS.register("alien_watcher", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "alien_watcher")));
    public static final RegistryEntry<SoundEvent> DRONE_FLY_BY = SPACE_SOUNDS.register("drone_fly_by", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "drone_fly_by")));
    public static final RegistryEntry<SoundEvent> FLYING_SAUCER = PLANET_SOUNDS.register("flying_saucer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "flying_saucer")));
    public static final RegistryEntry<SoundEvent> IMMINENT_DOOM = PLANET_SOUNDS.register("imminent_doom", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "imminent_doom")));
    public static final RegistryEntry<SoundEvent> LIGHT_SPEED_TRAVEL = SPACE_SOUNDS.register("light_speed_travel", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "light_speed_travel")));
    public static final RegistryEntry<SoundEvent> PASSING_SPACESHIP = SPACE_SOUNDS.register("passing_spaceship", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "passing_spaceship")));
    public static final RegistryEntry<SoundEvent> SPACE_LASER = PLANET_SOUNDS.register("space_laser", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "space_laser")));
    public static final RegistryEntry<SoundEvent> WORMHOLE = SPACE_SOUNDS.register("wormhole", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "wormhole")));

    public static final RegistryEntry<SoundEvent> LARGE_DOOR_OPEN = SOUND_EVENTS.register("large_door_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "large_door_open")));
    public static final RegistryEntry<SoundEvent> LARGE_DOOR_CLOSE = SOUND_EVENTS.register("large_door_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "large_door_close")));
    public static final RegistryEntry<SoundEvent> SMALL_DOOR_OPEN = SOUND_EVENTS.register("small_door_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AdAstra.MOD_ID, "small_door_open")));
}
