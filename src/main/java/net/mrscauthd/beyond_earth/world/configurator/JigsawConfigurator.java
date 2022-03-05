package net.mrscauthd.beyond_earth.world.configurator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class JigsawConfigurator extends JigsawConfiguration {
    public static final Codec<JigsawConfiguration> CODEC = RecordCodecBuilder.create((p_67764_) -> {
        return p_67764_.group(StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(JigsawConfiguration::startPool), Codec.intRange(0, 30).fieldOf("size").forGetter(JigsawConfiguration::maxDepth)).apply(p_67764_, JigsawConfiguration::new);
    });

    public JigsawConfigurator(Holder<StructureTemplatePool> p_204800_, int p_204801_) {
        super(p_204800_, p_204801_);
    }
}
