package in.kairoku.skillset_centimental;

import in.kairoku.skillset_centimental.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.kairoku.skillset_centimental.entity.BlackHoleEntity;
import in.kairoku.skillset_centimental.entity.MjolnirEntity;
import in.kairoku.skillset_centimental.entity.PortalEntity;
import in.kairoku.skillset_centimental.item.ModItems;
import in.kairoku.skillset_centimental.networking.SummonPortalPacket;
import in.kairoku.skillset_centimental.networking.SummonPortalPacketHandler;

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

	public static final EntityType<PortalEntity> PORTAL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "portal"),
			FabricEntityTypeBuilder.<PortalEntity>create(SpawnGroup.CREATURE, PortalEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build()
	);

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(SummonPortalPacket.TYPE.getId(), SummonPortalPacketHandler::receive);
		ModItems.initialize();
		ModWorldGeneration.generateModWorldGen();
	}
}