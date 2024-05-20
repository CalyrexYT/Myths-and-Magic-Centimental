package in.kairoku.skillset_centimental;

import in.kairoku.skillset_centimental.block.ModBlocks;
import in.kairoku.skillset_centimental.item.ModItemGroups;
import in.kairoku.skillset_centimental.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.kairoku.skillset_centimental.entity.BlackHoleEntity;
import in.kairoku.skillset_centimental.entity.MjolnirEntity;
import in.kairoku.skillset_centimental.item.ModItems;
import java.util.EnumMap;
import java.util.ArrayList;

public class SkillsetCentimental implements ModInitializer {
	public static final ArmorMaterial CHORUS = new ArmorMaterial(Util.make(new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.BODY, 11);

    }), 19, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT), new ArrayList<>(), 2f, 0.1f);
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "skillset-centimental";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<MjolnirEntity> MJOLNIR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "mjolnir"),
			EntityType.Builder.<MjolnirEntity>create(MjolnirEntity::new, SpawnGroup.CREATURE).build()
	);
	public static final EntityType<BlackHoleEntity> BLACK_HOLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "black_hole"),
			EntityType.Builder.<BlackHoleEntity>create(BlackHoleEntity::new, SpawnGroup.CREATURE).build()
	);

	@Override
	public void onInitialize() {
		Registry.register(Registries.ARMOR_MATERIAL, "chorus", CHORUS);
		ModItems.initialize();
		ModWorldGeneration.generateModWorldGen();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
	}
}