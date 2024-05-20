package in.kairoku.skillset_centimental.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
    
   
    //TODO continue making a way to make furnace accept recipe remainders
    //Lnet/minecraft/item/ItemStack;decrement(I)V
    //Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;
    @Inject(method = "craftRecipe", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private static void craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> cir, @Local ItemStack itemStack) {
        if(itemStack.getCount() == 1 && itemStack.getItem().getMaxCount() == 1 && itemStack.getItem().hasRecipeRemainder()) {
            slots.set(0, itemStack.getRecipeRemainder());
        }
    }
}
