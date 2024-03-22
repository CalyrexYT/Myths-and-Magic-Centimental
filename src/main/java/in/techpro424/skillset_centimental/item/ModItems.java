package in.techpro424.skillset_centimental.item;

import in.techpro424.skillset_centimental.SkillsetCentimental;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item PRISON_OF_SOULS = register(new PrisonOfSoulsSwordItem(), "prison_of_souls");
    public static final Item MJOLNIR = register(new MjolnirItem(), "mjolnir");
    // We can use generics to make it so we dont need to 
    // cast to an item when using this method.
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
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> {
            itemGroup.add(MJOLNIR);
        });
    }
}
