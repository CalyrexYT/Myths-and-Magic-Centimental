package in.kairoku.skillset_centimental.world;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;
public class ModConfiguredFeatures {
        public static final RegistryKey<ConfiguredFeature<?, ?>> CHORUS_ORE_KEY = registerKey("chorus_ore");
        public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
            RuleTest endReplacables = new BlockMatchRuleTest(Blocks.END_STONE);

            List<OreFeatureConfig.Target> endChorusOres =
                    List.of(OreFeatureConfig.createTarget(endReplacables, ModBlocks.CHORUS_ORE.getDefaultState()));

            register(context, CHORUS_ORE_KEY, Feature.ORE, new OreFeatureConfig(endChorusOres, 3));
        }
        public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
            return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(SkillsetCentimental.MOD_ID, name));
        }

        private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                       RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
            context.register(key, new ConfiguredFeature<>(feature, configuration));
        }
}
