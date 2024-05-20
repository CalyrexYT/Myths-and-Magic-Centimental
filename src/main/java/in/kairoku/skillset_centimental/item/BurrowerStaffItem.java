package in.kairoku.skillset_centimental.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BurrowerStaffItem extends Item {
    private int level;

    public BurrowerStaffItem(int level) {
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
            int cooldownTicks = getCooldownTicks(level);
            if (user.isSneaking()) {
                if (level < 6 && user.experienceLevel >= getXpCost()) {
                    user.addExperienceLevels(-getXpCost());
                    level++;
                    user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown reduced, ability upgraded!").formatted(Formatting.GREEN), true);
                } else if (level >= 6) {
                    user.sendMessage(Text.literal("Staff at maximum level!").formatted(Formatting.RED), true);
                } else {
                    user.sendMessage(Text.literal("You don't have enough experience levels to upgrade!").formatted(Formatting.RED), true);
                }
                user.getItemCooldownManager().set(this, 20);
            } else if (user.getItemCooldownManager().isCoolingDown(this)) {
                user.sendMessage(Text.literal("Burrower ability is on cooldown").formatted(Formatting.RED), true);
            } else {
                HitResult hit = user.raycast(20, 0, false);
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    BlockPos pos = blockHit.getBlockPos();
                    Direction direction = blockHit.getSide();

                    createTunnel(world, pos, direction, user);
                    applyPermanentEffects(user);

                    user.getItemCooldownManager().set(this, cooldownTicks);
                }
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    private void createTunnel(World world, BlockPos startPos, Direction direction, PlayerEntity user) {
        int[] size = getTunnelSize(level);
        int width = size[0];
        int height = size[1];

        Direction sideways = direction.rotateYClockwise();
        Direction vertical = Direction.UP;

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                BlockPos pos = startPos.offset(direction).offset(sideways, w).offset(vertical, h);
                BlockState blockState = world.getBlockState(pos);

                if (!world.isAir(pos) && blockState.getHardness(world, pos) >= 0) {
                    if (user.getInventory().getEmptySlot() != -1 || blockState.isAir()) {
                        world.breakBlock(pos, true, user);

                    }
                }
            }
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }


    private void applyPermanentEffects(PlayerEntity user) {
        if (level >= 3) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, 0));
        }
        if (level >= 4) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 60, 0));
        }
        if (level == 6) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 60, 0));
        }
    }

    private int getCooldownTicks(int level) {
        switch (level) {
            case 2: return 9 * 20;
            case 3: return 8 * 20;
            case 4: return 7 * 20;
            case 5: return 5 * 20;
            case 6: return 20 * 20;
            default: return 10 * 20;
        }
    }

    private int[] getTunnelSize(int level) {
        switch (level) {
            case 2: return new int[]{1, 2};
            case 3, 4: return new int[]{2, 2};
            case 5: return new int[]{3, 3};
            case 6: return new int[]{5, 5};
            default: return new int[]{1, 1};
        }
    }
}