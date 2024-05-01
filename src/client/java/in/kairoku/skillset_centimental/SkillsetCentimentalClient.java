package in.kairoku.skillset_centimental;

import org.lwjgl.glfw.GLFW;

import in.kairoku.skillset_centimental.item.ModItems;
import in.kairoku.skillset_centimental.networking.SummonPortalPacket;
import in.kairoku.skillset_centimental.rendering.BlackHoleModel;
import in.kairoku.skillset_centimental.rendering.BlackHoleRenderer;
import in.kairoku.skillset_centimental.rendering.MjolnirModel;
import in.kairoku.skillset_centimental.rendering.MjolnirRenderer;
import in.kairoku.skillset_centimental.rendering.PortalModel;
import in.kairoku.skillset_centimental.rendering.PortalRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class SkillsetCentimentalClient implements ClientModInitializer {
	private static KeyBinding portals;
	public static final EntityModelLayer MODEL_MJOLNIR_LAYER = new EntityModelLayer(new Identifier(SkillsetCentimental.MOD_ID, "mjolnir"), "bone");
	public static final EntityModelLayer MODEL_BLACKHOLE_LAYER = new EntityModelLayer(new Identifier(SkillsetCentimental.MOD_ID, "black_hole"), "main");
	public static final EntityModelLayer MODEL_PORTAL_LAYER = new EntityModelLayer(new Identifier(SkillsetCentimental.MOD_ID, "portal"), "everything");

	@Override
	public void onInitializeClient() {
		//KEYBIND

		portals = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SkillsetCentimental.MOD_ID + ".portals", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_R, // The keycode of the key
				"category." + SkillsetCentimental.MOD_ID // The translation key of the keybinding's category.
		));

		//MODEL
		ModelPredicateProviderRegistry.register(ModItems.PRISON_OF_SOULS, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> {
			if (livingEntity == null) return 0;
			return itemStack.getOrCreateNbt().getInt("charge") == 5 ? 1 : 0;
		});

		EntityRendererRegistry.register(SkillsetCentimental.MJOLNIR, (context) -> { return new MjolnirRenderer(context); });
		EntityRendererRegistry.register(SkillsetCentimental.BLACK_HOLE, (context) -> { return new BlackHoleRenderer(context); });
		EntityRendererRegistry.register(SkillsetCentimental.PORTAL, (context) -> { return new PortalRenderer(context); });


		ClientTickEvents.END_CLIENT_TICK.register(client -> { while (portals.wasPressed()) ClientPlayNetworking.send(new SummonPortalPacket()); });


		EntityModelLayerRegistry.registerModelLayer(MODEL_MJOLNIR_LAYER, MjolnirModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MODEL_BLACKHOLE_LAYER, BlackHoleModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MODEL_PORTAL_LAYER, PortalModel::getTexturedModelData);
	}

}