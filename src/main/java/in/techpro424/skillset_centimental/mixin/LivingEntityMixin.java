package in.techpro424.skillset_centimental.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import in.techpro424.skillset_centimental.item.PrisonOfSoulsSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onKilledBy", at = @At("TAIL"))
    private void increaseSwordCharge(@Nullable LivingEntity adversary, CallbackInfo ci) {
        if (adversary instanceof ServerPlayerEntity player) {
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof PrisonOfSoulsSwordItem) {
                CommandManager commandManager = player.getServer().getCommandManager();
                NbtCompound nbt = stack.getOrCreateNbt();
                int chance = Random.create().nextBetween(1, 100);
                long exponential = Math.round(100 * (Math.pow(0.84, nbt.getInt("damage"))));
                if (exponential == 0) {
                    exponential++;
                }
                if (chance <= exponential) {
                    nbt.putInt("damage", nbt.getInt("damage") + 1);
                    commandManager.executeWithPrefix(player.getServer().getCommandSource(), "/item replace entity @p weapon.mainhand with skillset-centimental:prison_of_souls{damage:" + nbt.getInt("damage") + ",Enchantments:[{id:sharpness,lvl: " + (nbt.getInt("damage")) + "}]}");
                    commandManager.executeWithPrefix(player.getServer().getCommandSource(), "/particle enchanted_hit " + player.getX() + " " + player.getY() + " " + player.getZ() + " 1 1 1 4 1000 normal");
                    }
            }
        }
    }
}
