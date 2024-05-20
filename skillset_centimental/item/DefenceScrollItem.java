package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DefenceScrollItem extends Item {
    public DefenceScrollItem() {
        super(new Item.Settings().maxCount(64));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            if (user.experienceLevel >= 20) {
                user.addExperienceLevels(-20);
            } else {
                user.sendMessage(Text.literal("Not enough experience to use the scroll!").formatted(Formatting.RED), true);
                return TypedActionResult.fail(stack);
            }

            if (user.hasStatusEffect(StatusEffects.RESISTANCE)) {
                StatusEffectInstance effect = user.getStatusEffect(StatusEffects.RESISTANCE);
                if (effect.getAmplifier() < 3) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, effect.getAmplifier() + 1));
                    user.sendMessage(Text.literal("Resistance level increased!").formatted(Formatting.GREEN), true);
                    stack.decrement(1);
                } else {
                    user.sendMessage(Text.literal("Maximum resistance level reached!").formatted(Formatting.RED), true);
                }
            } else {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));
                user.sendMessage(Text.literal("Resistance level 1 applied!").formatted(Formatting.GREEN), true);
                stack.decrement(1);
            }

            user.getItemCooldownManager().set(this, 10 * 20);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
