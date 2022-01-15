package net.mrscauthd.beyond_earth.world.oregen;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.mrscauthd.beyond_earth.ModInit;

import java.util.Random;

public class RuleTests {

    public static class MoonRuleTest extends RuleTest {
        static final MoonRuleTest INSTANCE = new MoonRuleTest();
        static final Codec<MoonRuleTest> codec = Codec.unit(() -> INSTANCE);

        public boolean test(BlockState blockAt, Random random) {
            if (blockAt.getBlock() == ModInit.MOON_STONE.get().defaultBlockState().getBlock()) {
                return true;
            }

            return false;
        }

        @Override
        protected RuleTestType<?> getType() {
            return OreGeneration.MOON_MATCH;
        }
    }

    public static class MarsRuleTest extends RuleTest {
        static final MarsRuleTest INSTANCE = new MarsRuleTest();
        static final Codec<MarsRuleTest> codec = Codec.unit(() -> INSTANCE);

        public boolean test(BlockState blockAt, Random random) {
            if (blockAt.getBlock() == ModInit.MARS_STONE.get().defaultBlockState().getBlock()) {
                return true;
            }

            return false;
        }

        protected RuleTestType<?> getType() {
            return OreGeneration.MARS_MATCH;
        }
    }

    public static class MercuryRuleTest extends RuleTest {
        static final MercuryRuleTest INSTANCE = new MercuryRuleTest();
        static final Codec<MercuryRuleTest> codec = Codec.unit(() -> INSTANCE);

        public boolean test(BlockState blockAt, Random random) {
            if (blockAt.getBlock() == ModInit.MERCURY_STONE.get().defaultBlockState().getBlock()) {
                return true;
            }

            return false;
        }

        protected RuleTestType<?> getType() {
            return OreGeneration.MERCURY_MATCH;
        }
    }

    public static class VenusRuleTest extends RuleTest {
        static final VenusRuleTest INSTANCE = new VenusRuleTest();
        static final Codec<VenusRuleTest> codec = Codec.unit(() -> INSTANCE);

        public boolean test(BlockState blockAt, Random random) {
            if (blockAt.getBlock() == ModInit.VENUS_STONE.get().defaultBlockState().getBlock()) {
                return true;
            }

            return false;
        }

        protected RuleTestType<?> getType() {
            return OreGeneration.VENUS_MATCH;
        }
    }

    public static class GlacioRuleTest extends RuleTest {
        static final GlacioRuleTest INSTANCE = new GlacioRuleTest();
        static final Codec<GlacioRuleTest> codec = Codec.unit(() -> INSTANCE);

        public boolean test(BlockState blockAt, Random random) {
            if (blockAt.getBlock() == ModInit.GLACIO_STONE.get().defaultBlockState().getBlock()) {
                return true;
            }

            return false;
        }

        protected RuleTestType<?> getType() {
            return OreGeneration.GLACIO_MATCH;
        }
    }

    public static class GlacioDeepslateRuleTest extends RuleTest {
        static final GlacioDeepslateRuleTest INSTANCE = new GlacioDeepslateRuleTest();
        static final Codec<GlacioDeepslateRuleTest> codec = Codec.unit(() -> INSTANCE);

        public boolean test(BlockState blockAt, Random random) {
            if (blockAt.getBlock() == Blocks.DEEPSLATE.defaultBlockState().getBlock()) {
                return true;
            }

            return false;
        }

        protected RuleTestType<?> getType() {
            return OreGeneration.GLACIO_DEEPSLATE_MATCH;
        }
    }
}
