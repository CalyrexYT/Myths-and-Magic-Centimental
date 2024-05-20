package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HealthScrollItem extends Item {
    public HealthScrollItem() {
        super(new Item.Settings().maxCount(64));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            if (user.experienceLevel >= 20) {
                user.addExperienceLevels(-20);
            }else {
                user.sendMessage(Text.literal("Not enough experience to use the scroll!").formatted(Formatting.RED), true);
                return TypedActionResult.fail(stack);
            }

            if (user.getMaxHealth() < 40.0F) {
                user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth() + 2.0F);
                user.sendMessage(Text.literal("Permanent heart added!").formatted(Formatting.GREEN), true);
                stack.decrement(1);
            } else {
                user.sendMessage(Text.literal("Maximum hearts reached!").formatted(Formatting.RED), true);
            }

            user.getItemCooldownManager().set(this, 10 * 20);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
