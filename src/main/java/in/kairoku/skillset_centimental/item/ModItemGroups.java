package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(SkillsetCentimental.MOD_ID, "mod_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.mod"))
                    .icon(() -> new ItemStack(ModItems.CHORUS_OF_THE_VOID)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.CHORUS_ORE);
                        entries.add(ModItems.CHORUS_LUMP);
                        entries.add(ModItems.INFUSED_CHORUS_FRUIT);
                        entries.add(ModItems.CHORUS_INGOT);

                        entries.add(ModItems.CHORUS_OF_THE_VOID);
                        entries.add(ModItems.MJOLNIR);
                        entries.add(ModItems.PRISON_OF_SOULS);
                        entries.add(ModItems.THANOTASIAN_SCYTHE);
                        entries.add(ModItems.BLADE_OF_DIMENSIONS);

                        entries.add(ModItems.CHORUS_HELMET);
                        entries.add(ModItems.CHORUS_CHESTPLATE);
                        entries.add(ModItems.CHORUS_LEGGINGS);
                        entries.add(ModItems.CHORUS_BOOTS);

                        entries.add(ModItems.DASHER_STAFF);
                        entries.add(ModItems.BARRIER_STAFF);
                        entries.add(ModItems.INFERNAL_STAFF);
                        entries.add(ModItems.CRYOMANCER_STAFF);
                        entries.add(ModItems.BARBARIAN_STAFF);
                        entries.add(ModItems.THUNDERER_STAFF);
                        entries.add(ModItems.BURROWER_STAFF);

                        entries.add(ModItems.HEALTH_SCROLL);
                        entries.add(ModItems.POWER_SCROLL);
                        entries.add(ModItems.DEFENCE_SCROLL);
                        entries.add(ModItems.ENLARGEMENT_SCROLL);
                        entries.add(ModItems.MINIATURISE_SCROLL);
                    }).build());


    public static void registerItemGroups() {
        SkillsetCentimental.LOGGER.info("Registering Item Groups for " + SkillsetCentimental.MOD_ID);
    }
}
