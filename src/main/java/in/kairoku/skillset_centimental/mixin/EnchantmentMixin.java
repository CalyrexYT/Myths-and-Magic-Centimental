package in.kairoku.skillset_centimental.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import in.kairoku.skillset_centimental.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(at = @At("HEAD"), method = "isAcceptableItem")
    private void mjolnirAcceptsAllEnchants(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        if (stack.isOf(ModItems.MJOLNIR)) if(enchantment.equals(Enchantments.FIRE_ASPECT) || enchantment.equals(Enchantments.KNOCKBACK))  cir.setReturnValue(true);
    }
}
