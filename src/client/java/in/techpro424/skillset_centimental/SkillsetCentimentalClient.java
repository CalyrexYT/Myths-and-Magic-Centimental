package in.techpro424.skillset_centimental;

import org.lwjgl.glfw.GLFW;

import in.techpro424.skillset_centimental.item.ModItems;
//import in.techpro424.skillset_centimental.networking.SummonPortalPacket;
import in.techpro424.skillset_centimental.rendering.MjolnirModel;
import in.techpro424.skillset_centimental.rendering.MjolnirRenderer;
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

	@Override
	public void onInitializeClient() {
		//KEYBIND
		/*
		portals = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.examplemod.spook", // The translation key of the keybinding's name
    		InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
    		GLFW.GLFW_KEY_R, // The keycode of the key
    	"category." + SkillsetCentimental.MOD_ID // The translation key of the keybinding's category.
		));
		*/
		//MODEL
		ModelPredicateProviderRegistry.register(ModItems.PRISON_OF_SOULS, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> {
			if (livingEntity == null) return 0;
			return itemStack.getOrCreateNbt().getInt("charge") == 5 ? 1 : 0;
		});

		EntityRendererRegistry.register(SkillsetCentimental.MJOLNIR, (context) -> {
			return new MjolnirRenderer(context);
		});
		/*
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
    		while (portals.wasPressed()) {
				ClientPlayNetworking.send(new SummonPortalPacket());
    		}
});
		*/

		EntityModelLayerRegistry.registerModelLayer(MODEL_MJOLNIR_LAYER, MjolnirModel::getTexturedModelData);
	}

}