package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BarbarianStaffItem extends Item {
    private int level;

    public BarbarianStaffItem(int level) {
        super(new Item.Settings().maxCount(1));
        this.level = level;
    }
    private int getXpCost() {
        switch (level) {
            case 5:
                return 500;
            case 4:
                return 200;
            case 3:
                return 100;
            case 2:
                return 50;
            case 1:
                return 25;
            case 0:
                return 10;
            default:
                return 0;
        }
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            int cooldownTicks = getCooldownTicks(level);
            if (user.isSneaking()) {
                if (level < 6 && user.experienceLevel >= getXpCost()) {
                    level++;
                    user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown reduced, ability upgraded!").formatted(Formatting.GREEN), true);
                } else if (level >= 6) {
                    user.sendMessage(Text.literal("Staff at maximum level!").formatted(Formatting.RED), true);
                } else {
                    user.sendMessage(Text.literal("You don't have enough experience levels to upgrade!").formatted(Formatting.RED), true);
                }
                user.getItemCooldownManager().set(this, 20);
            } else if (user.getItemCooldownManager().isCoolingDown(this)) {
                user.sendMessage(Text.literal("Barbarian ability is on cooldown").formatted(Formatting.RED), true);
            } else {
                activateRageMode(world, user);
                user.getItemCooldownManager().set(this, cooldownTicks);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    private void activateRageMode(World world, PlayerEntity user) {
        int duration = getDuration(level);
        switch (level) {
            case 1, 2:
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, duration, 0));
                break;
            case 3:
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, duration, 1));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 0));
                break;
            case 4:
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, duration, 1));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 0));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 0));
                break;
            case 5:
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, duration, 2));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 0));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 0));
                break;
            case 6:
                // Permanent strength
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 0));
                // Frenzied Leap effect
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 1, 1));
                world.createExplosion(user, user.getX(), user.getY(), user.getZ(), 4.0F, false, World.ExplosionSourceType.NONE);
                world.getOtherEntities(user, user.getBoundingBox().expand(10), entity -> entity instanceof LivingEntity && !(entity instanceof PlayerEntity))
                        .forEach(entity -> ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1)));
                // Post-leap buffs
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 1)); // 5 seconds
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 0));
                break;
        }
    }

    private int getCooldownTicks(int level) {
        return switch (level) {
            case 2 -> 8 * 20;
            case 3 -> 6 * 20;
            case 4 -> 5 * 20;
            case 5 -> 4 * 20;
            case 6 -> 3 * 20;
            default -> 10 * 20;
        };
    }

    private int getDuration(int level) {
        return switch (level) {
            case 2 -> 3 * 20;
            case 3 -> 4 * 20;
            case 4, 6 -> 5 * 20;
            case 5 -> 6 * 20;
            default -> 2 * 20;
        };
    }
}
