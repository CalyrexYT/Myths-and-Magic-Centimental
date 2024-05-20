package in.kairoku.skillset_centimental.block;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final Block CHORUS_ORE = registerBlock("chorus_ore", new Block(Blocks.ANCIENT_DEBRIS.getSettings().hardness(100.0f)));
    public static final Block BARRIER_BLOCK = registerBlock("barrier_block", new Block(Blocks.END_STONE.getSettings().mapColor(MapColor.BLACK).hardness(-1.0f).dropsNothing().noBlockBreakParticles().resistance(2000.0f).luminance((state) -> {
        return 15;
    })));
    private static Block registerBlock(String name, Block block){
        registerBlocksItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(SkillsetCentimental.MOD_ID, name), block);
    }
    private static Item registerBlocksItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(SkillsetCentimental.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }
    public static void registerModBlocks(){
        SkillsetCentimental.LOGGER.info("Registering ModBlocks for " + SkillsetCentimental.MOD_ID);
    }
}
