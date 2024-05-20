package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item PRISON_OF_SOULS = register(new PrisonOfSoulsSwordItem(), "prison_of_souls");
    public static final Item MJOLNIR = register(new MjolnirItem(), "mjolnir");
    public static final Item CHORUS_OF_THE_VOID = register(new ChorusOfTheVoidItem(), "chorus_of_the_void");
    public static final Item BLADE_OF_DIMENSIONS = register(new BladeOfDimensionsItem(), "blade_of_dimensions");
    public static final Item THANOTASIAN_SCYTHE = register(new ThanotasianScythe(), "thanotasian_scythe");
    
    static RegistryEntry<ArmorMaterial> CHORUS = Registries.ARMOR_MATERIAL.getEntry(SkillsetCentimental.CHORUS);
    public static final Item CHORUS_HELMET = register(new ModArmorItem(CHORUS, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)), "chorus_helmet");
    public static final Item CHORUS_CHESTPLATE = register(new ModArmorItem(CHORUS, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)), "chorus_chestplate");
    public static final Item CHORUS_LEGGINGS = register(new ModArmorItem(CHORUS, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)), "chorus_leggings");
    public static final Item CHORUS_BOOTS = register(new ModArmorItem(CHORUS, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)), "chorus_boots");
    public static final Item CHORUS_LUMP = register(new Item(new Item.Settings().maxCount(64)), "chorus_lump");
    public static final Item INFUSED_CHORUS_FRUIT = register(new Item(new Item.Settings().maxCount(64)), "infused_chorus_fruit");
    public static final Item CHORUS_INGOT = register(new Item(new Item.Settings().maxCount(64)), "chorus_ingot");

    public static final Item DASHER_STAFF = register(new DasherStaffItem(), "dasher_staff");
    public static final Item BARRIER_STAFF = register(new BarrierStaffItem(), "barrier_staff");
    public static final Item INFERNAL_STAFF = register(new InfernalStaffItem(), "infernal_staff");
    public static final Item CRYOMANCER_STAFF = register(new CryomancerStaffItem(), "cryomancer_staff");
    public static final Item THUNDERER_STAFF = register(new ThundererStaffItem(0), "thunderer_staff");
    public static final Item BARBARIAN_STAFF = register(new BarbarianStaffItem(0), "barbarian_staff");
    public static final Item BURROWER_STAFF = register(new BurrowerStaffItem(0), "burrower_staff");


    public static final Item HEALTH_SCROLL = register(new HealthScrollItem(), "health_scroll");
    public static final Item POWER_SCROLL = register(new PowerScrollItem(), "power_scroll");
    public static final Item DEFENCE_SCROLL = register(new DefenceScrollItem(), "defence_scroll");
    public static final Item ENLARGEMENT_SCROLL = register(new EnlargementScrollItem(), "enlargement_scroll");
    public static final Item MINIATURISE_SCROLL = register(new MiniaturiseScrollItem(), "miniaturise_scroll");
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

            itemGroup.add(BARRIER_STAFF);
            itemGroup.add(DASHER_STAFF);
            itemGroup.add(INFERNAL_STAFF);
            itemGroup.add(CRYOMANCER_STAFF);
            itemGroup.add(BARBARIAN_STAFF);
            itemGroup.add(THUNDERER_STAFF);
            itemGroup.add(BURROWER_STAFF);
        });
    }
}
