package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {

	public static SoundEvent ROCKET_LAUNCH_SOUND_EVENT;
	public static SoundEvent WRENCH;
	public static SoundEvent WINDY;

	public static SoundEvent ALIEN_WATCHER;
	public static SoundEvent DRONE_FLY_BY;
	public static SoundEvent FLYING_SAUCER;
	public static SoundEvent IMMINENT_DOOM;
	public static SoundEvent LIGHT_SPEED_TRAVEL;
	public static SoundEvent PASSING_SPACESHIP;
	public static SoundEvent SPACE_LASER;
	public static SoundEvent WORMHOLE;

	public static SoundEvent LARGE_DOOR_OPEN;
	public static SoundEvent LARGE_DOOR_CLOSE;
	public static SoundEvent SMALL_DOOR_OPEN;

	public static SoundEvent[] SPACE_SOUNDS;
	public static SoundEvent[] PLANET_SOUNDS;

	private static SoundEvent register(Identifier id) {
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	public static void register() {
		ROCKET_LAUNCH_SOUND_EVENT = register(new ModIdentifier("rocket_fly"));
		WRENCH = register(new ModIdentifier("wrench"));
		WINDY = register(new ModIdentifier("windy"));
		ALIEN_WATCHER = register(new ModIdentifier("alien_watcher"));
		DRONE_FLY_BY = register(new ModIdentifier("drone_fly_by"));
		FLYING_SAUCER = register(new ModIdentifier("flying_saucer"));
		IMMINENT_DOOM = register(new ModIdentifier("imminent_doom"));
		LIGHT_SPEED_TRAVEL = register(new ModIdentifier("light_speed_travel"));
		PASSING_SPACESHIP = register(new ModIdentifier("passing_spaceship"));
		SPACE_LASER = register(new ModIdentifier("space_laser"));
		WORMHOLE = register(new ModIdentifier("wormhole"));
		
		LARGE_DOOR_OPEN = register(new ModIdentifier("large_door_open"));
		LARGE_DOOR_CLOSE = register(new ModIdentifier("large_door_close"));
		SMALL_DOOR_OPEN = register(new ModIdentifier("small_door_open"));

		SPACE_SOUNDS = new SoundEvent[] { ALIEN_WATCHER, DRONE_FLY_BY, FLYING_SAUCER, IMMINENT_DOOM, LIGHT_SPEED_TRAVEL, PASSING_SPACESHIP, SPACE_LASER, WORMHOLE };
		PLANET_SOUNDS = new SoundEvent[] { ALIEN_WATCHER, FLYING_SAUCER, IMMINENT_DOOM, SPACE_LASER };
	}
}
