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

public class MiniaturiseScrollItem extends Item {
    public MiniaturiseScrollItem() {
        super(new Item.Settings().maxCount(64));
    }
    private double baseSize = 1.0;
    private double baseHealth = 20.0;
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

            if (user.getScale() > 0.1F) {
                user.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(user.getScale() - baseSize*0.1);
                user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth() - 1);

                user.sendMessage(Text.literal("Permanent size decreased!").formatted(Formatting.GREEN), true);
                stack.decrement(1);
            } else {
                user.sendMessage(Text.literal("Minimum size reached!").formatted(Formatting.RED), true);
            }

            user.getItemCooldownManager().set(this, 20);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
