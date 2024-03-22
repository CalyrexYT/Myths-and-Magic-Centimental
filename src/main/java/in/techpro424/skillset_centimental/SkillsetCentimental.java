package in.techpro424.skillset_centimental;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.techpro424.skillset_centimental.entity.BlackHoleEntity;
import in.techpro424.skillset_centimental.entity.MjolnirEntity;
import in.techpro424.skillset_centimental.item.ModItems;

public class SkillsetCentimental implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "skillset-centimental";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<MjolnirEntity> MJOLNIR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "mjolnir"),
			FabricEntityTypeBuilder.<MjolnirEntity>create(SpawnGroup.CREATURE, MjolnirEntity::new).build()
	);

	public static final EntityType<BlackHoleEntity> BLACK_HOLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "black_hole"),
			FabricEntityTypeBuilder.<BlackHoleEntity>create(SpawnGroup.CREATURE, BlackHoleEntity::new).build()
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ModItems.initialize();
	}
}