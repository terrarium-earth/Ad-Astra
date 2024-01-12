package earth.terrarium.adastra.datagen.provider.client;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.blocks.machines.EnergizerBlock;
import earth.terrarium.adastra.common.blocks.pipes.PipeBlock;
import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    protected static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.FLUIDS.stream().map(RegistryEntry::get).forEach(this::fluidBlock);

        ModBlocks.CUBES.stream().map(RegistryEntry::get).forEach(this::basicBlock);
        ModBlocks.CTM_CUBES.stream().map(RegistryEntry::get).forEach(this::basicBlockNoState);
        ModBlocks.CUBE_COLUMNS.stream().map(RegistryEntry::get).forEach(this::basicCubeColumn);
        ModBlocks.PILLARS.stream().map(RegistryEntry::get).forEach(b -> logBlock((RotatedPillarBlock) b));
        ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(b -> stairsBlock((StairBlock) b, findTexture(b, "_stairs")));
        ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(b -> slabBlock((SlabBlock) b, findTexture(b, "_slab"), findTexture(b, "_slab")));
        ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(b -> wallBlock((WallBlock) b, findTexture(b, "_wall")));
        ModBlocks.PRESSURE_PLATES.stream().map(RegistryEntry::get).forEach(b -> pressurePlateBlock((PressurePlateBlock) b, findTexture(b, "_pressure_plate")));
        ModBlocks.BUTTONS.stream().map(RegistryEntry::get).forEach(b -> buttonBlock((ButtonBlock) b, findTexture(b, "_button")));

        ModBlocks.PILLARS.stream().map(RegistryEntry::get).forEach(b -> simpleBlockItem(b, models().getBuilder(name(b))));
        ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(b -> simpleBlockItem(b, models().getBuilder(name(b))));
        ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(b -> simpleBlockItem(b, models().getBuilder(name(b))));
        ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(b -> itemModels().wallInventory(name(b), findTexture(b, "_wall")));
        ModBlocks.PRESSURE_PLATES.stream().map(RegistryEntry::get).forEach(b -> simpleBlockItem(b, models().getBuilder(name(b))));
        ModBlocks.BUTTONS.stream().map(RegistryEntry::get).forEach(b -> itemModels().buttonInventory(name(b), findTexture(b, "_button")));

        doorBlock((DoorBlock) ModBlocks.AERONOS_DOOR.get(), modLoc("block/" + name(ModBlocks.AERONOS_DOOR.get()) + "_bottom"), modLoc("block/" + name(ModBlocks.AERONOS_DOOR.get()) + "_top"));
        doorBlock((DoorBlock) ModBlocks.STROPHAR_DOOR.get(), modLoc("block/" + name(ModBlocks.STROPHAR_DOOR.get()) + "_bottom"), modLoc("block/" + name(ModBlocks.STROPHAR_DOOR.get()) + "_top"));
        doorBlock((DoorBlock) ModBlocks.GLACIAN_DOOR.get(), modLoc("block/" + name(ModBlocks.GLACIAN_DOOR.get()) + "_bottom"), modLoc("block/" + name(ModBlocks.GLACIAN_DOOR.get()) + "_top"));
        doorBlock((DoorBlock) ModBlocks.STEEL_DOOR.get(), modLoc("block/" + name(ModBlocks.STEEL_DOOR.get()) + "_bottom"), modLoc("block/" + name(ModBlocks.STEEL_DOOR.get()) + "_top"));

        trapdoorBlock((TrapDoorBlock) ModBlocks.AERONOS_TRAPDOOR.get(), blockTexture(ModBlocks.AERONOS_TRAPDOOR.get()), true);
        trapdoorBlock((TrapDoorBlock) ModBlocks.STROPHAR_TRAPDOOR.get(), blockTexture(ModBlocks.STROPHAR_TRAPDOOR.get()), true);
        trapdoorBlock((TrapDoorBlock) ModBlocks.GLACIAN_TRAPDOOR.get(), blockTexture(ModBlocks.GLACIAN_TRAPDOOR.get()), true);
        trapdoorBlock((TrapDoorBlock) ModBlocks.STEEL_TRAPDOOR.get(), blockTexture(ModBlocks.STEEL_TRAPDOOR.get()), true);

        fenceBlock((FenceBlock) ModBlocks.AERONOS_FENCE.get(), findTexture(ModBlocks.AERONOS_FENCE.get(), "_fence"));
        fenceBlock((FenceBlock) ModBlocks.STROPHAR_FENCE.get(), findTexture(ModBlocks.STROPHAR_FENCE.get(), "_fence"));
        fenceBlock((FenceBlock) ModBlocks.GLACIAN_FENCE.get(), findTexture(ModBlocks.GLACIAN_FENCE.get(), "_fence"));

        fenceGateBlock((FenceGateBlock) ModBlocks.AERONOS_FENCE_GATE.get(), findTexture(ModBlocks.AERONOS_FENCE_GATE.get(), "_fence_gate"));
        fenceGateBlock((FenceGateBlock) ModBlocks.STROPHAR_FENCE_GATE.get(), findTexture(ModBlocks.STROPHAR_FENCE_GATE.get(), "_fence_gate"));
        fenceGateBlock((FenceGateBlock) ModBlocks.GLACIAN_FENCE_GATE.get(), findTexture(ModBlocks.GLACIAN_FENCE_GATE.get(), "_fence_gate"));

        simpleBlockItem(ModBlocks.AERONOS_TRAPDOOR.get(), models().getBuilder(name(ModBlocks.AERONOS_TRAPDOOR.get()) + "_bottom"));
        simpleBlockItem(ModBlocks.STROPHAR_TRAPDOOR.get(), models().getBuilder(name(ModBlocks.STROPHAR_TRAPDOOR.get()) + "_bottom"));
        simpleBlockItem(ModBlocks.GLACIAN_TRAPDOOR.get(), models().getBuilder(name(ModBlocks.GLACIAN_TRAPDOOR.get()) + "_bottom"));
        simpleBlockItem(ModBlocks.STEEL_TRAPDOOR.get(), models().getBuilder(name(ModBlocks.STEEL_TRAPDOOR.get()) + "_bottom"));

        simpleBlockItem(ModBlocks.AERONOS_FENCE.get(), models().getBuilder(name(ModBlocks.AERONOS_PLANKS.get())));
        simpleBlockItem(ModBlocks.STROPHAR_FENCE.get(), models().getBuilder(name(ModBlocks.STROPHAR_PLANKS.get())));
        simpleBlockItem(ModBlocks.GLACIAN_FENCE.get(), models().getBuilder(name(ModBlocks.GLACIAN_PLANKS.get())));

        simpleBlockItem(ModBlocks.AERONOS_FENCE_GATE.get(), models().getBuilder(name(ModBlocks.AERONOS_PLANKS.get())));
        simpleBlockItem(ModBlocks.STROPHAR_FENCE_GATE.get(), models().getBuilder(name(ModBlocks.STROPHAR_PLANKS.get())));
        simpleBlockItem(ModBlocks.GLACIAN_FENCE_GATE.get(), models().getBuilder(name(ModBlocks.GLACIAN_PLANKS.get())));

        machine(ModBlocks.COMPRESSOR.get());
        machine(ModBlocks.COAL_GENERATOR.get());
        etrionicBlastFurnaceBlock(ModBlocks.ETRIONIC_BLAST_FURNACE.get());
        steelMachine(ModBlocks.OXYGEN_LOADER.get());
        steelMachine(ModBlocks.FUEL_REFINERY.get());
        complexMachine(ModBlocks.WATER_PUMP.get());
        complexMachine(ModBlocks.SOLAR_PANEL.get());
        sidedRenderedBlock(ModBlocks.OXYGEN_DISTRIBUTOR.get());
        sidedRenderedBlock(ModBlocks.GRAVITY_NORMALIZER.get());
        energizer(ModBlocks.ENERGIZER.get());
        cryoFreezer(ModBlocks.CRYO_FREEZER.get());
        oxygenSensor(ModBlocks.OXYGEN_SENSOR.get());
        complexMachine(ModBlocks.NASA_WORKBENCH.get());

        ModBlocks.GLOBES.stream().map(RegistryEntry::get).forEach(this::globe);
        ModBlocks.FLAGS.stream().map(RegistryEntry::get).forEach(this::flag);

        ModBlocks.SIMPLE_SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(this::slidingDoor);
        existingBlock(ModBlocks.REINFORCED_DOOR.get());
        existingBlock(ModBlocks.AIRLOCK.get());

        existingBlock(ModBlocks.LAUNCH_PAD.get());

        pipe(ModBlocks.STEEL_CABLE.get(), "cable");
        pipe(ModBlocks.DESH_CABLE.get(), "large_cable");
        pipe(ModBlocks.DESH_FLUID_PIPE.get(), "fluid_pipe");
        pipe(ModBlocks.OSTRUM_FLUID_PIPE.get(), "fluid_pipe");
        basicBlock(ModBlocks.CABLE_DUCT.get());
        basicBlock(ModBlocks.FLUID_PIPE_DUCT.get());

        ModBlocks.INDUSTRIAL_LAMPS.stream().map(RegistryEntry::get).forEach(b -> industrialLamp(b, "industrial_lamp"));
        ModBlocks.SMALL_INDUSTRIAL_LAMPS.stream().map(RegistryEntry::get).forEach(b -> industrialLamp(b, "small_industrial_lamp"));
    }

    public void basicBlock(Block block) {
        simpleBlockItem(block, models().getBuilder(name(block)));
        simpleBlock(block);
    }

    public void basicBlock(Block block, ModelFile model) {
        simpleBlockItem(block, models().getBuilder(name(block)));
        simpleBlock(block, model);
    }

    public void basicBlockNoState(Block block) {
        simpleBlockItem(block, models().getBuilder(name(block)));
        cubeAll(block);
    }

    public void basicCubeColumn(Block block) {
        basicBlock(block,
            models().cubeColumn(
                name(block),
                modLoc("block/" + name(block)),
                modLoc("block/" + name(block) + "_top")));
    }

    private void fluidBlock(Block block) {
        simpleBlock(block, models().getBuilder(name(block)).texture("particle", WATER_STILL.toString()));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    public void existingBlock(Block block) {
        simpleBlock(block, models().getExistingFile(modLoc("block/%s".formatted(name(block)))));
    }

    public void machine(Block block) {
        simpleBlockItem(block, models().getBuilder("block/%s_on".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            boolean lit = state.getValue(MachineBlock.LIT);
            String name = this.name(block);
            ResourceLocation texture = modLoc((lit ? "block/%s_front_on" : "block/%s_front").formatted(name, name));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(lit ? name + "_on" : name)
                    .texture("down", modLoc("block/machine_bottom"))
                    .texture("up", modLoc("block/machine_top"))
                    .texture("north", texture)
                    .texture("south", modLoc("block/machine_side"))
                    .texture("east", modLoc("block/machine_side"))
                    .texture("west", modLoc("block/machine_side"))
                    .texture("particle", texture)
                    .parent(models().getExistingFile(new ResourceLocation("block/cube"))))
                .rotationY((int) (facing.toYRot() + 180) % 360)
                .build();
        });
    }

    public void steelMachine(Block block) {
        simpleBlockItem(block, models().getBuilder("block/%s_on".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            boolean lit = state.getValue(MachineBlock.LIT);
            String name = this.name(block);
            ResourceLocation texture = modLoc((lit ? "block/%s_front_on" : "block/%s_front").formatted(name, name));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(lit ? name + "_on" : name)
                    .texture("down", modLoc("block/steel_machine_bottom"))
                    .texture("up", modLoc("block/steel_machine_top"))
                    .texture("north", texture)
                    .texture("south", modLoc("block/steel_machine_side"))
                    .texture("east", modLoc("block/steel_machine_side"))
                    .texture("west", modLoc("block/steel_machine_side"))
                    .texture("particle", texture)
                    .parent(models().getExistingFile(new ResourceLocation("block/cube"))))
                .rotationY((int) (facing.toYRot() + 180) % 360)
                .build();
        });
    }

    public void complexMachine(Block block) {
        itemModels().withExistingParent(name(block), modLoc("block/%s".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            return ConfiguredModel.builder()
                .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name(block)))))
                .rotationY((int) (facing.toYRot() + 180) % 360)
                .build();
        });
    }

    public void energizer(Block block) {
        simpleBlockItem(block, models().getBuilder("block/%s_blue".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            int power = state.getValue(EnergizerBlock.POWER);
            String name = this.name(block);
            String type = switch (power) {
                case 0 -> "empty";
                case 1 -> "red";
                case 2 -> "orange";
                case 3 -> "yellow";
                case 4 -> "green";
                default -> "blue";
            };

            ResourceLocation texture = modLoc("block/%s_side_%s".formatted(name, type));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(name + "_" + type)
                    .texture("down", modLoc("block/energizer_bottom"))
                    .texture("up", modLoc("block/energizer_top"))
                    .texture("ring", modLoc("block/energizer_ring"))
                    .texture("arm", modLoc("block/energizer_arm"))
                    .texture("side", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(modLoc("block/%s".formatted(name)))))
                .rotationY((int) (facing.toYRot() + 180) % 360)
                .build();
        });
    }

    public void cryoFreezer(Block block) {
        itemModels().withExistingParent(name(block), modLoc("block/%s".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            boolean lit = state.getValue(MachineBlock.LIT);
            return ConfiguredModel.builder()
                .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name(block) + (lit ? "_on" : "")))))
                .rotationY((int) (facing.toYRot() + 180) % 360)
                .build();
        });
    }

    public void oxygenSensor(Block block) {
        simpleBlockItem(block, models().getBuilder("block/%s".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            boolean powered = state.getValue(MachineBlock.POWERED);
            String name = this.name(block);
            ResourceLocation texture = modLoc((powered ? "block/%s_front_on" : "block/%s_front").formatted(name, name));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(powered ? name + "_on" : name)
                    .texture("down", modLoc("block/oxygen_sensor_bottom"))
                    .texture("up", modLoc("block/oxygen_sensor_top"))
                    .texture("north", texture)
                    .texture("south", texture)
                    .texture("east", texture)
                    .texture("west", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(new ResourceLocation("block/cube"))))
                .rotationY((int) (facing.toYRot() + 180) % 360)
                .build();
        });
    }

    public void globe(Block block) {
        itemModels().getBuilder(key(block).getPath()).parent(itemModels().getExistingFile(modLoc("item/rendered_item")));
        getVariantBuilder(block).forAllStates(state -> {
            String name = this.name(block);
            ResourceLocation texture = modLoc("block/globe/%s".formatted(name));

            ConfiguredModel.builder()
                .modelFile(models().getBuilder(name + "_cube")
                    .texture("0", texture)
                    .parent(models().getExistingFile(modLoc("block/globe_cube"))))
                .build();

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(name)
                    .texture("0", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(modLoc("block/globe"))))
                .build();
        });
    }

    public void flag(Block block) {
        simpleBlockItem(block, models().getBuilder("block/%s".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            String name = this.name(block);
            ResourceLocation texture = modLoc("block/flag/%s".formatted(name));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(name)
                    .texture("0", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(modLoc("block/flag"))))
                .build();
        });
    }

    public void slidingDoor(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            String name = this.name(block);
            ResourceLocation texture = modLoc("block/sliding_door/%s".formatted(name));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(name)
                    .texture("0", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(modLoc("block/sliding_door"))))
                .build();
        });
    }

    public void pipe(Block block, String parent) {
        String name = name(block);
        models().withExistingParent(name, modLoc(parent)).texture("0", modLoc("block/pipe/%s".formatted(name)));
        models().withExistingParent(name + "_insert", modLoc(parent))
            .texture("0", modLoc("block/pipe/%s_insert".formatted(name)))
            .texture("particle", modLoc("block/pipe/%s_insert".formatted(name)));

        models().withExistingParent(name + "_extract", modLoc(parent))
            .texture("0", modLoc("block/pipe/%s_extract".formatted(name)))
            .texture("particle", modLoc("block/pipe/%s_extract".formatted(name)));

        models().withExistingParent(name + "_core", modLoc(parent + "_core"))
            .texture("0", modLoc("block/pipe/%s".formatted(name)))
            .texture("particle", modLoc("block/pipe/%s".formatted(name)));

        getMultipartBuilder(block)
            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_core".formatted(name))))
            .addModel()
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
            .addModel()
            .condition(PipeBlock.CONNECTED_NORTH, PipeProperty.NORMAL)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_insert".formatted(name))))
            .addModel()
            .condition(PipeBlock.CONNECTED_NORTH, PipeProperty.INSERT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_extract".formatted(name))))
            .addModel()
            .condition(PipeBlock.CONNECTED_NORTH, PipeProperty.EXTRACT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
            .rotationY(90)
            .addModel()
            .condition(PipeBlock.CONNECTED_EAST, PipeProperty.NORMAL)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_insert".formatted(name))))
            .rotationY(90)
            .addModel()
            .condition(PipeBlock.CONNECTED_EAST, PipeProperty.INSERT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_extract".formatted(name))))
            .rotationY(90)
            .addModel()
            .condition(PipeBlock.CONNECTED_EAST, PipeProperty.EXTRACT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
            .rotationY(180)
            .addModel()
            .condition(PipeBlock.CONNECTED_SOUTH, PipeProperty.NORMAL)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_insert".formatted(name))))
            .rotationY(180)
            .addModel()
            .condition(PipeBlock.CONNECTED_SOUTH, PipeProperty.INSERT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_extract".formatted(name))))
            .rotationY(180)
            .addModel()
            .condition(PipeBlock.CONNECTED_SOUTH, PipeProperty.EXTRACT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
            .rotationY(270)
            .addModel()
            .condition(PipeBlock.CONNECTED_WEST, PipeProperty.NORMAL)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_insert".formatted(name))))
            .rotationY(270)
            .addModel()
            .condition(PipeBlock.CONNECTED_WEST, PipeProperty.INSERT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_extract".formatted(name))))
            .rotationY(270)
            .addModel()
            .condition(PipeBlock.CONNECTED_WEST, PipeProperty.EXTRACT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
            .rotationX(270)
            .addModel()
            .condition(PipeBlock.CONNECTED_UP, PipeProperty.NORMAL)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_insert".formatted(name))))
            .rotationX(270)
            .addModel()
            .condition(PipeBlock.CONNECTED_UP, PipeProperty.INSERT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_extract".formatted(name))))
            .rotationX(270)
            .addModel()
            .condition(PipeBlock.CONNECTED_UP, PipeProperty.EXTRACT)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
            .rotationX(90)
            .addModel()
            .condition(PipeBlock.CONNECTED_DOWN, PipeProperty.NORMAL)
            .end()

            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_insert".formatted(name))))
            .rotationX(90)
            .addModel()
            .condition(PipeBlock.CONNECTED_DOWN, PipeProperty.INSERT)

            .end()
            .part()
            .modelFile(models().getExistingFile(modLoc("block/%s_extract".formatted(name))))
            .rotationX(90)
            .addModel()
            .condition(PipeBlock.CONNECTED_DOWN, PipeProperty.EXTRACT)
            .end();
    }

    public void etrionicBlastFurnaceBlock(Block block) {
        simpleBlockItem(block, models().getBuilder("block/etrionic_blast_furnace_on"));
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(MachineBlock.FACING);
            boolean lit = state.getValue(MachineBlock.LIT);
            ResourceLocation texture = lit ? modLoc("block/etrionic_blast_furnace/etrionic_blast_furnace_on") : modLoc("block/etrionic_blast_furnace/etrionic_blast_furnace");

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder("etrionic_blast_furnace" + (lit ? "_on" : ""))
                    .texture("0", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(modLoc("block/etrionic_blast_furnace_base"))))
                .rotationY((int) facing.toYRot())
                .build();
        });
    }

    public void industrialLamp(Block block, String parent) {
        simpleBlockItem(block, models().getBuilder("block/%s".formatted(name(block))));
        getVariantBuilder(block).forAllStates(state -> {
            String name = this.name(block);
            ResourceLocation texture = modLoc("block/lamp/%s".formatted(name));

            return ConfiguredModel.builder()
                .modelFile(models().getBuilder(name)
                    .texture("0", texture)
                    .parent(models().getExistingFile(modLoc("block/%s".formatted(parent)))))
                .rotationX(state.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                .rotationY((((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                .build();
        });
    }

    public void sidedRenderedBlock(Block block) {
        simpleBlockItem(block, itemModels().getExistingFile(ModItemModelProvider.RENDERED_ITEM));
        getVariantBuilder(block).forAllStates(state -> {
            String name = this.name(block);
            return ConfiguredModel.builder()
                .modelFile(models().getExistingFile(modLoc("block/%s".formatted(name))))
                .rotationX(state.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                .rotationY((((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                .build();
        });
    }

    private ResourceLocation findTexture(Block block, String replace) {
        ResourceLocation path = new ResourceLocation(AdAstra.MOD_ID, blockTexture(block).getPath().replace(replace, ""));
        if (!models().existingFileHelper.exists(path, TEXTURE)) {
            path = new ResourceLocation(AdAstra.MOD_ID, blockTexture(block).getPath().replace(replace, "s"));
            if (!models().existingFileHelper.exists(path, TEXTURE)) {
                path = new ResourceLocation(AdAstra.MOD_ID, blockTexture(block).getPath().replace(replace, "_bricks"));
                if (!models().existingFileHelper.exists(path, TEXTURE)) {
                    path = new ResourceLocation(AdAstra.MOD_ID, blockTexture(block).getPath().replace(replace, "_planks"));
                }
            }
        }
        return path;
    }
}
