package in.techpro424.skillset_centimental.mixin;

import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import in.techpro424.skillset_centimental.item.PrisonOfSoulsSwordItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getAttackDamage", at = @At("TAIL")) 
    private static void increaseSharpness(ItemStack stack, EntityGroup group, CallbackInfoReturnable<Float> cir, @Local MutableFloat mutableFloat) {
        if (stack.getItem() instanceof PrisonOfSoulsSwordItem) {
                NbtCompound nbt = stack.getOrCreateNbt();
                int charge = nbt.getInt("charge");
                //See the DamageEnchantment and EnchantmentHelper classes for the reasoning behind this calculation
                //This calculation should ideally work the same way as that of the Sharpness enchantment 
                mutableFloat.add(1.0f + (float)Math.max(0, charge - 1) * 0.5f);
            }
        
    }
}
