This update brings a lot of internal changes from the rework into the release version of Ad Astra.
It is initially marked as a beta as there are a lot of internal changes, so it may break addons and
may be unstable.

## Changes
- Fixed lava turning into ice in space
- Fixed Ad Astra fluids in water tag
- Fixed rocket sound being stereo and not mono. This allows the audio to be directional.
- Machines no longer light up when active. This is to improve lighting performance.
- Large performance improvement to all machines
- Removed extinguished torches and lanterns. Torches and lanterns will simply break in space.
- Oxygen sensor block can now be inverted by shift-right clicking
- Oxygen, gravity and temperature are now saved in the world nbt. This should fix any issues where you don't have
  oxygen when you first open a world because it hasn't been calculated yet.
- Added public APIs for planets, oxygen, gravity, and temperature. Please use those instead of the internal ones
  when creating addons.
- Added events for oxygen, gravity, zero-g, and temperature, acid rain, and environment ticking. Please use those instead of mixing into the internal
tick methods.
- Improved many tooltips. They now have more info and colored elements.
- Machines now display energy/fluid in/out per tick.
- Energy and fluid numbers now have suffixes (K, M, G). Shifting will show the full number.
- Fluid tanks can now be cleared by shift-right clicking
- Updated the plate and rod textures
- Added gas tanks. these replace oxygen tanks and can hold any fluid. Using them will distribute the
  stored fluid into any container in your inventory, with armor being the first priority.
- Added the etrionic capacitor. This is an item that can hold a large amount of energy and can be discharged
into energy items in your inventory.
- Added the zip gun. When filled with oxygen, it propels you forward. Holding two at the same time will propel you
  further.
- Separated the config into a common config and client config.
- Added redstone control to machines. These have 4 options: `ALWAYS_ON`, `ON_WHEN_POWERED`,
  `ON_WHEN_NOT_POWERED`, and `NEVER_ON`.
- Added a slot in energy machines for powering them with items.
- Added side configuration to all machines. This allows you to control what each element
- Cables and fluid pipes are now much more performant and should now be smarter i.e. automatically
  figure out the sources and consumers without having to explicitly configure them.
of the machine does to all sides. For example, you can configure a machine to pull items on
one side, extract energy from another side, and push fluid to another side.
- Added experimental orbit physics. This enables zero gravity movement while in orbit.
- Added an experimental "Air Vortex." This spawns when you break or otherwise open a space in
a sealed oxygenated structure in orbit or any planet with no oxygen. It violently sucks
everything out of the structure and launches it, undergoing rapid depressurization.
Now you can eject the impostor :)
- All blocks and items are now properly datagened. this should fix any sort of weird discrepancies
with some models as they are no longer manually created.
- Removed meteors. They'll be re-added in the future but for now they don't need to exist and are more annoying, if anything.
- Added a button in the oxygen distributor to highlight all blocks that it's distributing to.
This replaces the original oxygen bubble particle indicator. This is also now entirely
client-side and saved to the client config.
- Rewrote JEI and REI integration, with improved screens and tooltips.
- Flags now render the second skin layer.
- Fixed space suit arm glowing in first person with shaders
- Set max flag URL length from 32 to 64 characters.
- Fixed boots, pants, and helmet part of the space suit being able to hold energy and fluid.
- Changed package name from `earth.terrarium.ad_astra` to `earth.terrarium.adastra`. The mod id has not changed.
- Sliding doors can now be locked by right-clicking with a wrench. Locked sliding doors
can only be opened with redstone, similar to iron doors.
- Increased max oxygen distributor size from 3,000 blocks to 6,000 blocks.
- Fixed being able to interact with the rocket while inside of it.
- Each rocket now has its own fuel tag. This allows packmakers to require different fuels for different rockets.
- Coal generator will no longer continue consuming fuel if it's full on energy.
- Updated the rover driving mechanics to be more fluid and easier to control.
- The rover can now run over mobs.
- Fix water pump collision shape.
- Add resourcefullib highlight support to flags. This makes their interaction shape look correct.
- Improved and added many new descriptions to blocks and items.
- Added oxygen intake and outtake sounds when entering and exiting oxygenated areas.
- Fluid ducts now have the same internal mechanics as pipes, so they can now be used exactly like them.
- Added the radio. This is a real radio that'll play actual select radio stations. It's available as a
screen in the rover. You can open in by pressing `R` while in the rover or clicking on the screen on the rover.
- Added the radio block. It'll play real radio stations to everyone in the area like a jukebox.
- The chest on the back of the rover is now clickable.
- Opening your inventory while in the rover now opens the rover inventory.
- Rockets and rovers can no longer carry items. Breaking them will cause the items to drop.
- The Rocket sound is now looped. This allows it to play for as long as the rocket is flying.
- Fixed rocket sound not playing when leaving the world and rejoining while in a flying rocket.
- Fixed rocket particles not visible above world height with Embeddium/Sodium installed.
- Filled energy and fluid items are now in the creative tab.
- Fixed rocket particles lagging behind the rocket on servers.
- Fixed DFU running on all Ad Astra structures due to them being saved on an older version
- Added a keybind `V` to enable and disable the Jetsuit.
- Removed the hammer. Use the compressor to obtain plates instead.
- Each orbit can now have its owned recipe defined via datapack, instead of all of them using the same recipe.
- Reduced oil well spawn rates.
- Removed the `ad_astra_platform` tags. forge and fabric specific tags are now part of the "ad_astra" namespace as optional tags.
- Launchpads can now launch rockets when powered with redstone.
- Lander boosters now have a sound when used.
- The compressor can now compress storage blocks into 9 plates at once.
- The owner and location of space stations are now saved in the world nbt. This allows
you to land at your space station from anywhere and also prevents accidentally spawning
a new space station on an existing one.
- Updated the planets screen with a cleaner UI. You should be able to navigate the menu easier. And it 
should now be clear when you want to land on the planet, land on the space station or build a new space station.
- Add support for multiple owned space stations. There is now a list of owned space stations in the planets screen,
and you can choose which one to land on.
- You can no longer teleport to orbit directly in the planets menu.
You must build a space station and land on that space station first.
- Added industrial lamps and small industrial lamps. These are available in all 16 colors.
- Updated star colors to be more vibrant and have some colors be more rare than others.
- Improved planet skyboxes.
- Added [Shimmer](https://modrinth.com/mod/shimmer!) compat. This adds bloom to the rocket particles and adds colored lighting to the new industrial lamps.
- Added Factory blocks. These are decorative blocks that support connected textures if you have [Athena](https://modrinth.com/mod/athena-ctm) installed.
These are available for all metals.
- Added Encased Plateblocks, and Panel blocks. These are available for all metals.
- Redid many of the recipes.
- Added higher blast resistance for higher tier metals.
- Water will not longer instantly freeze, and it can now be placed on Venus and Mercury. It will now
simply freeze or evaporate over time.
- Plants now break over time when placed in non-oxygenated planets.
- Fixed sleeping on planets not advancing the night.
- Added the Etrionic Blast furnace. This is a better version of the blast furnace that uses energy, has 4 slots, and is now used
to smelt steel. The blast furnace steel recipe have been removed.
- Added the gravity normalizer. This allows you to control the gravity of a local area, similar
to the oxygen distributor. It's especially important in orbit due to the zero gravity environment.
- Added the Ti-69. It's a device that displays the local oxygen, temperature, and gravity.
- Copper no longer oxidizes in environments with no oxygen.
- Updated the oxygen distributor model. It now spins and can be placed on any block face.
- You now need to hold shift for two seconds before you can dismount moving vehicles.
- Landers now prevent the player from dismounting when falling without explicitly dismounting. This should fix
incompatibilities with mods like Transit Railway.
- Only allow mobs that can survive in space to spawn in ad astra dimensions. This should fix
the issue where mods like Alex's Mobs would spawn flies that kept dying.
- The warning to hold space when landing is now displayed boldly on screen instead of a chat message.
- The lander will now display the distance to the ground when landing.
Hopefully this will prevent people from missing it and crashing.
- The NASA workbench can now automatically craft rockets when powered with redstone.
- Added Cadmus compat to space stations. It'll automatically claim the chunks around your space station
and prevent players from spawning a new space station at any claimed chunks. 
- Added Argonauts compat to space stations. Players on the same guild will be
able to share space stations.

## Breaking Changes
- Removed Glacian signs, and aeronos and strophar chests.
- The JSON format for Ad Astra planets has changed a bit. You'll need
to update your existing planet file for it to work. Check the GitHub wiki (when it's updated).
- Some recipe IDs have been updated. It won't break anything in-game, but 
if you're using these recipes in modpacks, you'll need to change them. This includes 
  - "ad_astra:space_station_recipe" -> "ad_astra:space_station"
  - "ad_astra:cryo_fuel_conversion" -> "ad_astra:cryo_freezing"
  - "ad_astra:fuel_conversion" -> "ad_astra:refining"
  - "ad_astra:oxygen_conversion" -> "ad_astra:oxygen_loading"
- Removed the sky renderer JSON API. Sky renderers should now be done in code.
- Removed the JSON APIs for adding planets, galaxies and info in the planets screen.

## TODO:
- Sliding doors can now be redstone powered from any of its blocks, instead of just the bottom middle one.
