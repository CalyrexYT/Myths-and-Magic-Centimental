package in.kairoku.skillset_centimental.datagen;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementsProvider extends FabricAdvancementProvider {
    protected ModAdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry barrierStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.BARRIER_STAFF, // The display icon
                        Text.literal("Emerge from the darkness, blacker than darkness. Purify that which is impure."), // The title
                        Text.literal("obtained the barrier staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_barrier_staff", InventoryChangedCriterion.Conditions.items(ModItems.BARRIER_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_barrier_staff");
        AdvancementEntry dasherStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.DASHER_STAFF, // The display icon
                        Text.literal("Evasive Manoeuvres!"), // The title
                        Text.literal("obtained the dasher staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_dasher_staff", InventoryChangedCriterion.Conditions.items(ModItems.DASHER_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_dasher_staff");
        AdvancementEntry barbarianStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.BARBARIAN_STAFF, // The display icon
                        Text.literal("Rage Mode!"), // The title
                        Text.literal("obtained the barbarian staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_barbarian_staff", InventoryChangedCriterion.Conditions.items(ModItems.BARBARIAN_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_barbarian_staff");
        AdvancementEntry cryomancerStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.CRYOMANCER_STAFF, // The display icon
                        Text.literal("Subzero!"), // The title
                        Text.literal("obtained the cryomancer staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_cryomancer_staff", InventoryChangedCriterion.Conditions.items(ModItems.CRYOMANCER_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_cryomancer_staff");
        AdvancementEntry infernalStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.INFERNAL_STAFF, // The display icon
                        Text.literal("Wildfire!"), // The title
                        Text.literal("obtained the infernal staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_infernal_staff", InventoryChangedCriterion.Conditions.items(ModItems.INFERNAL_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_infernal_staff");
        AdvancementEntry thundererStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.THUNDERER_STAFF, // The display icon
                        Text.literal("Thunderclap!"), // The title
                        Text.literal("obtained the thunderer staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_thunderer_staff", InventoryChangedCriterion.Conditions.items(ModItems.THUNDERER_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_thunderer_staff");
        AdvancementEntry burrowerStaffAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.BURROWER_STAFF, // The display icon
                        Text.literal("Tunnelling Mastery!"), // The title
                        Text.literal("obtained the burrower staff"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_burrower_staff", InventoryChangedCriterion.Conditions.items(ModItems.BURROWER_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_burrower_staff");
        AdvancementEntry modStaffAdvancement = Advancement.Builder.create().parent(barrierStaffAdvancement)
                .parent(burrowerStaffAdvancement)
                .parent(cryomancerStaffAdvancement)
                .parent(infernalStaffAdvancement)
                .parent(barbarianStaffAdvancement)
                .parent(barrierStaffAdvancement)
                .parent(dasherStaffAdvancement)
                .display(
                        ModItems.DASHER_STAFF, // The display icon
                        Text.literal("THE collector - staffs and magic"), // The title
                        Text.literal("Collected all mod staffs"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.GOAL, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_barrier_staff", InventoryChangedCriterion.Conditions.items(ModItems.BARRIER_STAFF))
                .criterion("got_cryomancer_staff", InventoryChangedCriterion.Conditions.items(ModItems.CRYOMANCER_STAFF))
                .criterion("got_burrower_staff", InventoryChangedCriterion.Conditions.items(ModItems.BURROWER_STAFF))
                .criterion("got_thunderer_staff", InventoryChangedCriterion.Conditions.items(ModItems.THUNDERER_STAFF))
                .criterion("got_infernal_staff", InventoryChangedCriterion.Conditions.items(ModItems.INFERNAL_STAFF))
                .criterion("got_barbarian_staff", InventoryChangedCriterion.Conditions.items(ModItems.BARBARIAN_STAFF))
                .criterion("got_dasher_staff", InventoryChangedCriterion.Conditions.items(ModItems.DASHER_STAFF))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_all_staffs");

        AdvancementEntry prisonOfSoulsAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.PRISON_OF_SOULS, // The display icon
                        Text.literal("So am I the warden now?"), // The title
                        Text.literal("Acquired the prison of souls"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_prison_of_souls", InventoryChangedCriterion.Conditions.items(ModItems.BLADE_OF_DIMENSIONS))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_prison_of_souls");

        AdvancementEntry bladeOfDimensionsAdvancement = Advancement.Builder.create().parent(prisonOfSoulsAdvancement)
                .display(
                        ModItems.BLADE_OF_DIMENSIONS, // The display icon
                        Text.literal("This feels.....multidimensional"), // The title
                        Text.literal("Acquired the blade of dimensions"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_blade_of_dimension", InventoryChangedCriterion.Conditions.items(ModItems.BLADE_OF_DIMENSIONS))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_blade_of_dimension");
        AdvancementEntry thanotasianScytheAdvancement = Advancement.Builder.create().parent(bladeOfDimensionsAdvancement)
                .display(
                        ModItems.THANOTASIAN_SCYTHE, // The display icon
                        Text.literal("\"Bloody Hell!\""), // The title
                        Text.literal("Collected the scythe of darkness"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_scythe", InventoryChangedCriterion.Conditions.items(ModItems.THANOTASIAN_SCYTHE))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_scythe");

        AdvancementEntry chorusOfTheVoidAdvancement = Advancement.Builder.create().parent(thanotasianScytheAdvancement)
                .display(
                        ModItems.CHORUS_OF_THE_VOID, // The display icon
                        Text.literal("Cosmic Convergence - Mastery Unleashed"), // The title
                        Text.literal("Collected the sword born from the void"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_chorus_of_the_void", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_OF_THE_VOID))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_chorus_of_the_void");
        AdvancementEntry mjolnirAdvancement = Advancement.Builder.create().parent(chorusOfTheVoidAdvancement)
                .display(
                        ModItems.MJOLNIR, // The display icon
                        Text.literal("By the will of the gods, i'm alive! I'm invincible! I'm, THOR!!"), // The title
                        Text.literal("Basking in the presence of the mythical Mjolnir"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_mjolnir", InventoryChangedCriterion.Conditions.items(ModItems.MJOLNIR))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_mjolnir");
        AdvancementEntry chorusLumpAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.CHORUS_LUMP, // The display icon
                        Text.literal("End Resonance - Unearthed"), // The title
                        Text.literal("Obtained chorus lump"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_chorus_lump", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_LUMP))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_chorus_lump");
        AdvancementEntry infusedChorusAdvancement = Advancement.Builder.create()
                .display(
                        ModItems.INFUSED_CHORUS_FRUIT, // The display icon
                        Text.literal("End Resonance - Infused"), // The title
                        Text.literal("Created an infused chorus fruit"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_infused_chorus", InventoryChangedCriterion.Conditions.items(ModItems.INFUSED_CHORUS_FRUIT))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_infused_chorus");
        AdvancementEntry chorusIngotAdvancement = Advancement.Builder.create().parent(chorusLumpAdvancement).parent(infusedChorusAdvancement)
                .display(
                        ModItems.CHORUS_INGOT, // The display icon
                        Text.literal("End Resonance - Fused"), // The title
                        Text.literal("Crafted a chorus ingot"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_chorus_ingot", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_INGOT))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_chorus_ingot");
        AdvancementEntry choursArmorAdvancement = Advancement.Builder.create().parent(chorusIngotAdvancement)
                .display(
                        ModItems.CHORUS_CHESTPLATE, // The display icon
                        Text.literal("End Resonance - Done and dusted"), // The title
                        Text.literal("Collected the full set of chorus armor"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_chorus_helmet", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_HELMET))
                .criterion("got_chorus_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_CHESTPLATE))
                .criterion("got_chorus_leggings", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_LEGGINGS))
                .criterion("got_chorus_boots", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_BOOTS))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_chorus_armor");
        AdvancementEntry modWeaponAdvancement = Advancement.Builder.create().parent(mjolnirAdvancement)
                .display(
                        ModItems.MJOLNIR, // The display icon
                        Text.literal("THE collector - myths"), // The title
                        Text.literal("Collected all mod weapons"), // The description
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"), // Background image used,
                        AdvancementFrame.GOAL, // Options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_mjolnir", InventoryChangedCriterion.Conditions.items(ModItems.MJOLNIR))
                .criterion("got_scythe", InventoryChangedCriterion.Conditions.items(ModItems.THANOTASIAN_SCYTHE))
                .criterion("got_blade_of_dimension", InventoryChangedCriterion.Conditions.items(ModItems.BLADE_OF_DIMENSIONS))
                .criterion("got_prison_of_souls", InventoryChangedCriterion.Conditions.items(ModItems.PRISON_OF_SOULS))
                .criterion("got_chorus_of_the_void", InventoryChangedCriterion.Conditions.items(ModItems.CHORUS_OF_THE_VOID))
                .build(consumer, SkillsetCentimental.MOD_ID + "/got_mod_weapons");
    }
}