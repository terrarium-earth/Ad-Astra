package net.mrscauthd.beyond_earth.events;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	/**Entities*/
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALIEN_SPAWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALIEN_ZOMBIE_SPAWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> STAR_CRAWLER_SPAWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> PYGRO_SPAWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> PYGRO_BRUTE_SPAWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> MOGLER_SPAWN;
	public static final ForgeConfigSpec.ConfigValue<Boolean> MARTIAN_RAPTOR_SPAWN;

	/**Structures Overworld*/
	public static final ForgeConfigSpec.ConfigValue<Boolean> OIL_WELL_STRUCTURE;
	public static final ForgeConfigSpec.ConfigValue<Boolean> METEOR_STRUCTURE;

	/**Structures Planets*/
	public static final ForgeConfigSpec.ConfigValue<Boolean> ALIEN_VILLAGE_STRUCTURE;
	public static final ForgeConfigSpec.ConfigValue<Boolean> VENUS_BULLET_STRUCTURE;
	public static final ForgeConfigSpec.ConfigValue<Boolean> VENUS_TOWER_STRUCTURE;
	public static final ForgeConfigSpec.ConfigValue<Boolean> CRIMSON_VILLAGE_STRUCTURE;

	/**Entity Systems*/
	public static final ForgeConfigSpec.ConfigValue<Boolean> PLAYER_OXYGEN_SYSTEM;
	public static final ForgeConfigSpec.ConfigValue<Boolean> ENTITY_OXYGEN_SYSTEM;

	static {
		BUILDER.push("Beyond Earth Config");

		/**Entities*/
		ALIEN_SPAWN = BUILDER.comment("Enable or Disable Alien to Spawn").define("Alien Spawn", true);
		ALIEN_ZOMBIE_SPAWN = BUILDER.comment("Enable or Disable Alien Zombie to Spawn").define("Alien Zombie Spawn", true);
		STAR_CRAWLER_SPAWN = BUILDER.comment("Enable or Disable Star Crawler to Spawn").define("Star Crawler Spawn", true);
		PYGRO_SPAWN = BUILDER.comment("Enable or Disable Pygro to Spawn").define("Pygro Spawn", true);
		PYGRO_BRUTE_SPAWN = BUILDER.comment("Enable or Disable Pygro Brute to Spawn").define("Pygro Brute Spawn", true);
		MOGLER_SPAWN = BUILDER.comment("Enable or Disable Mogler to Spawn").define("Mogler Spawn", true);
		MARTIAN_RAPTOR_SPAWN = BUILDER.comment("Enable or Disable Martian Raptor to Spawn").define("Martian Raptor Spawn", true);

		/**Structures Overworld*/
		OIL_WELL_STRUCTURE = BUILDER.comment("Enable or Disable Oil Well Structure to Spawn").define("Oil Well Structure", true);
		METEOR_STRUCTURE = BUILDER.comment("Enable or Disable Meteor Structure to Spawn").define("Meteor Structure", true);

		/**Structures Planets*/
		ALIEN_VILLAGE_STRUCTURE = BUILDER.comment("Enable or Disable Alien Village Structure to Spawn").define("Alien Village Structure", true);
		VENUS_BULLET_STRUCTURE = BUILDER.comment("Enable or Disable Venus Bullet Structure to Spawn").define("Venus Bullet Structure", true);
		VENUS_TOWER_STRUCTURE = BUILDER.comment("Enable or Disable Venus Tower Structure to Spawn").define("Venus Tower Structure", true);
		CRIMSON_VILLAGE_STRUCTURE = BUILDER.comment("Enable or Disable Crimson Village Structure to Spawn").define("Crimson Village Structure", true);

		/**Entity Systems*/
		PLAYER_OXYGEN_SYSTEM = BUILDER.comment("Enable or Disable Player Oxygen System").define("Player Oxygen System", true);
		ENTITY_OXYGEN_SYSTEM = BUILDER.comment("Enable or Disable Entity Oxygen System").define("Entity Oxygen System", true);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}