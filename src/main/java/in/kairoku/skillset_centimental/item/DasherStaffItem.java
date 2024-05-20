package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class DasherStaffItem extends Item {
    private static double launchSpeed = 2.0;
    private int level;
    private static int cooldownTicks = 20 * 10;
    private double yLevel;
    private boolean isLaunched;
    public DasherStaffItem() {
        super(new Item.Settings().maxCount(1));
        this.level = 0;
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
        if (user.isSneaking()) {
            if (level < 6 && user.experienceLevel >= getXpCost()) {
                level++;
                if (level == 2 || level == 4 || level == 6) {
                    launchSpeed += 0.5;
                    user.sendMessage(Text.literal("Upgraded to level " + level + "! Launched distance increased!").formatted(Formatting.GREEN), true);
                } else {
                    cooldownTicks -= 20 * 2;
                    user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown reduced by 2 sec!").formatted(Formatting.GREEN), true);
                }
                user.getItemCooldownManager().set(this, 20);
            } else if (level >= 6) {
                user.sendMessage(Text.literal("Staff at maximum level!").formatted(Formatting.RED), true);
            } else {
                user.sendMessage(Text.literal("You don't have enough experience levels to upgrade!").formatted(Formatting.RED), true);
            }
        } else if (user.getItemCooldownManager().isCoolingDown(this)) {
            user.sendMessage(Text.literal("Dasher ability is on cooldown").formatted(Formatting.RED), true);
        } else {
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, user.getBoundingBox().expand(15), e -> e != user);
            double x = user.getRotationVector().x * launchSpeed;
            double y = user.getRotationVector().y * launchSpeed;
            double z = user.getRotationVector().z * launchSpeed;
            yLevel = user.getY();
            isLaunched = true;
            user.addVelocity(x, y, z);
            if (level == 6) {
                for (LivingEntity e : entities) {
                    e.addVelocity(x, y, z);
                }
            }

            user.getItemCooldownManager().set(this, cooldownTicks);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity && !world.isClient) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isOnGround()){
                player.removeStatusEffect(StatusEffects.RESISTANCE);
                isLaunched = false;
            } else if (isLaunched) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 2, 4));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
