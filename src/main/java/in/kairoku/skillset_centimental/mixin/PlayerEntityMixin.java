package in.kairoku.skillset_centimental.mixin;

import in.kairoku.skillset_centimental.item.RequiemItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.TimeUnit;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    private int cooldown;
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onPlayerDeath(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack heldItem = player.getMainHandStack();
        if (heldItem.getItem() instanceof RequiemItem) {
            startIncorporealForm(player);
        }
    }

    private void startIncorporealForm(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 200, 0, false, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 4, false, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1, false, false));

        ServerWorld world = (ServerWorld) player.getWorld();

    }
}

