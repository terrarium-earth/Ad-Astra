package earth.terrarium.adastra.datagen.dimensions;

import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.datagen.provider.server.registry.ModBiomeDataProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRuleData {
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource MOON_SAND = makeStateRule(ModBlocks.MOON_SAND.get());
    private static final SurfaceRules.RuleSource MOON_STONE = makeStateRule(ModBlocks.MOON_STONE.get());

    public static SurfaceRules.RuleSource moon() {
        SurfaceRules.ConditionSource isMoonWastes = SurfaceRules.isBiome(ModBiomeDataProvider.MOON_WASTES);

        SurfaceRules.RuleSource bedrockFloorRule = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.verticalGradient("bedrock_floor",
                    VerticalAnchor.bottom(),
                    VerticalAnchor.aboveBottom(5)),
                BEDROCK
            )
        );

        SurfaceRules.RuleSource lunarWastesRule = SurfaceRules.sequence(
            SurfaceRules.ifTrue(isMoonWastes,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        SurfaceRules.yBlockCheck(
                            VerticalAnchor.absolute(87),
                            2),
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                SurfaceRules.stoneDepthCheck(
                                    4,
                                    false,
                                    CaveSurface.FLOOR),
                                MOON_SAND
                            )
                        )
                    )
                )
            )
        );

        return SurfaceRules.sequence(
            bedrockFloorRule,
            lunarWastesRule);
    }


    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
