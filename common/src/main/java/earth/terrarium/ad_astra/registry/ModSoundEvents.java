package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;

import java.util.List;
import java.util.function.Supplier;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(AdAstra.MOD_ID, Registry.SOUND_EVENT_REGISTRY);

    public static final Supplier<SoundEvent> ROCKET_LAUNCH_SOUND_EVENT = register("rocket_fly");
    public static final Supplier<SoundEvent> WRENCH = register("wrench");
    public static final Supplier<SoundEvent> WINDY = register("windy");

    public static final Supplier<SoundEvent> ALIEN_WATCHER = register("alien_watcher");
    public static final Supplier<SoundEvent> DRONE_FLY_BY = register("drone_fly_by");
    public static final Supplier<SoundEvent> FLYING_SAUCER = register("flying_saucer");
    public static final Supplier<SoundEvent> IMMINENT_DOOM = register("imminent_doom");
    public static final Supplier<SoundEvent> LIGHT_SPEED_TRAVEL = register("light_speed_travel");
    public static final Supplier<SoundEvent> PASSING_SPACESHIP = register("passing_spaceship");
    public static final Supplier<SoundEvent> SPACE_LASER = register("space_laser");
    public static final List<Supplier<SoundEvent>> PLANET_SOUNDS = List.of(ALIEN_WATCHER, FLYING_SAUCER, IMMINENT_DOOM, SPACE_LASER);
    public static final Supplier<SoundEvent> WORMHOLE = register("wormhole");
    public static final List<Supplier<SoundEvent>> SPACE_SOUNDS = List.of(ALIEN_WATCHER, DRONE_FLY_BY, FLYING_SAUCER, IMMINENT_DOOM, LIGHT_SPEED_TRAVEL, PASSING_SPACESHIP, SPACE_LASER, WORMHOLE);
    public static final Supplier<SoundEvent> LARGE_DOOR_OPEN = register("large_door_open");
    public static final Supplier<SoundEvent> LARGE_DOOR_CLOSE = register("large_door_close");
    public static final Supplier<SoundEvent> SMALL_DOOR_OPEN = register("small_door_open");

    private static Supplier<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> new SoundEvent(new ModResourceLocation(id)));
    }

    public static void register() {
        SOUND_EVENTS.register();
    }
}
