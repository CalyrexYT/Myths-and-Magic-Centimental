package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.attribute.EntityAttributes;
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

public class EnlargementScrollItem extends Item {
    public EnlargementScrollItem() {
        super(new Item.Settings().maxCount(64));
    }
    private double baseSize = 1.0;
    private double baseInteractionDistance = 4.5;
    private double baseStepHeight = 0.6;
    private int jumpBoost = 1;
    private double baseFallDamageMultiplier = 1.0;
    private double baseSpeed = 0.1;

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
            if (user.getScale() < 5.0F) {
                StatusEffectInstance effect = user.getStatusEffect(StatusEffects.JUMP_BOOST);
                user.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(user.getScale() + baseSize*0.2);
                user.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(user.getStepHeight() + baseStepHeight*0.2);
                user.getAttributeInstance(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE).setBaseValue(user.getEntityInteractionRange() + baseInteractionDistance*0.01);
                user.getAttributeInstance(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE).setBaseValue(user.getBlockInteractionRange() + baseInteractionDistance*0.01);
                user.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(user.getMovementSpeed() + baseSpeed*0.2);
                user.getAttributeInstance(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER).setBaseValue(user.getSafeFallDistance() - baseFallDamageMultiplier*0.6);
                user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth() + 2);
                if (effect == null)
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, 0));
                else if (jumpBoost%3 == 0 && jumpBoost < 15) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, (effect.getAmplifier() + jumpBoost / 3)));
                    jumpBoost++;
                } else if (jumpBoost < 15)
                    jumpBoost++;
                user.sendMessage(Text.literal("Permanent size increased!").formatted(Formatting.GREEN), true);
                stack.decrement(1);
            } else {
                user.sendMessage(Text.literal("Maximum size reached!").formatted(Formatting.RED), true);
            }

            user.getItemCooldownManager().set(this, 20);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
