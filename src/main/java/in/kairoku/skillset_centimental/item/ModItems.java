package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item PRISON_OF_SOULS = register(new PrisonOfSoulsSwordItem(), "prison_of_souls");
    public static final Item MJOLNIR = register(new MjolnirItem(), "mjolnir");
    public static final Item CHORUS_OF_THE_VOID = register(new ChorusOfTheVoidItem(), "chorus_of_the_void");
    public static final Item BLADE_OF_DIMENSIONS = register(new BladeOfDimensionsItem(), "blade_of_dimensions");
    public static final Item THANOTASIAN_SCYTHE = register(new ThanotasianScythe(), "thanotasian_scythe");

    // We can use generics to make it so we don't need to
    // cast to an item when using this method.
    public static final Item CHORUS_ORE = register(new Item(new Item.Settings()), "chorus_ore");
    public static final Item CHORUS_HELMET = register(new ModArmorItem(ModArmorMaterials.CHORUS, ArmorItem.Type.HELMET, new FabricItemSettings()), "chorus_helmet");
    public static final Item CHORUS_CHESTPLATE = register(new ModArmorItem(ModArmorMaterials.CHORUS, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()), "chorus_chestplate");
    public static final Item CHORUS_LEGGINGS = register(new ModArmorItem(ModArmorMaterials.CHORUS, ArmorItem.Type.LEGGINGS, new FabricItemSettings()), "chorus_leggings");
    public static final Item CHORUS_BOOTS = register(new ModArmorItem(ModArmorMaterials.CHORUS, ArmorItem.Type.BOOTS, new FabricItemSettings()), "chorus_boots");

    public static <T extends Item> T register(T item, String ID) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier("skillset-centimental", ID);
        
        // Register the item.
        T registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

    public static void initialize() {
        SkillsetCentimental.LOGGER.info("Initializing mod items...");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
            itemGroup.add(PRISON_OF_SOULS);
            itemGroup.add(MJOLNIR);
            itemGroup.add(CHORUS_OF_THE_VOID);
            itemGroup.add(BLADE_OF_DIMENSIONS);
            itemGroup.add(THANOTASIAN_SCYTHE);
            itemGroup.add(CHORUS_HELMET);
            itemGroup.add(CHORUS_CHESTPLATE);
            itemGroup.add(CHORUS_LEGGINGS);
            itemGroup.add(CHORUS_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> {
            itemGroup.add(ModBlocks.CHORUS_ORE);
        });
    }
}
