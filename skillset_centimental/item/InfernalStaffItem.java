package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class InfernalStaffItem extends Item {
    private int level;
    private int explosionPower;
    private int COOLDOWN_TICKS;
    private float fireballSpeed;

    public InfernalStaffItem() {
        super(new Item.Settings().maxCount(1));
        this.level = 0;
        this.COOLDOWN_TICKS = 20 * 10; // Initial cooldown of 10 seconds
        this.explosionPower = 2; // Initial explosion power
        this.fireballSpeed = 2.0F;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        double x = user.getRotationVector().x * 5;
        double y = user.getRotationVector().y * 5;
        double z = user.getRotationVector().z * 5;

        if (user.isSneaking()) {
            if (level < 6 && user.experienceLevel >= getXpCost()) {
                level++;
                if (level == 2 || level == 4 || level == 6) {
                    COOLDOWN_TICKS -= 20 * 2;
                    user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown decreased!").formatted(Formatting.GREEN), true);
                } else {
                    explosionPower += 1;
                    fireballSpeed += 1.0F;
                    user.sendMessage(Text.literal("Upgraded to level " + level + "! Fireball explosion power increased!").formatted(Formatting.GREEN), true);
                }
                user.getItemCooldownManager().set(this, 20);
            } else if (level >= 6) {
                user.sendMessage(Text.literal("Staff at maximum level!").formatted(Formatting.RED), true);
            } else {
                user.sendMessage(Text.literal("You don't have enough experience levels to upgrade!").formatted(Formatting.RED), true);
            }
        } else if (!user.getItemCooldownManager().isCoolingDown(this)) {
            FireballEntity fireball = new FireballEntity(world, user, x, y, z, explosionPower);
            fireball.setPos(user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ());
            fireball.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, fireballSpeed, 1.0F);
            world.spawnEntity(fireball);
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        } else {
            user.sendMessage(Text.literal("Infernal ability is on cooldown").formatted(Formatting.RED), true);
        }

        return TypedActionResult.success(stack);
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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
