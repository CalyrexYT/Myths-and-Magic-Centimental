package in.kairoku.skillset_centimental.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ThundererStaffItem extends Item {
    private int level;
    private boolean isThunderRainActive = false;
    private final int THUNDERRAIN_DELAY = 5;
    private int delayTime = 0;
    private int durationTime = 0;

    public ThundererStaffItem(int level) {
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
                    level++;
                    if (level == 2 || level == 4 || level == 6) {
                        user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown decreased, Lightning power increased!").formatted(Formatting.GREEN), true);
                    } else {
                        user.sendMessage(Text.literal("Upgraded to level " + level + "! Lightning power increased!").formatted(Formatting.GREEN), true);
                    }
                    user.getItemCooldownManager().set(this, 20);
                } else if (level >= 6) {
                    user.sendMessage(Text.literal("Staff is at max level!").formatted(Formatting.RED), true);
                } else {
                    user.sendMessage(Text.literal("You don't have enough experience levels to upgrade!").formatted(Formatting.RED), true);
                }
            } else if (user.getItemCooldownManager().isCoolingDown(this)) {
                user.sendMessage(Text.literal("Thunderer ability is on cooldown").formatted(Formatting.RED), true);
            } else {
                if (level == 6) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10*20, 4));
                    isThunderRainActive = true;
                    durationTime = 10 * 20; // 10 seconds
                    delayTime = 0;
                    user.getItemCooldownManager().set(this, cooldownTicks);
                } else {
                    HitResult hit = user.raycast(20, 0, false);
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        Vec3d pos = blockHit.getPos();

                        // Summon lightning based on level
                        for (int i = 0; i < getLightningCount(level); i++) {
                            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                            if (lightningEntity != null) {
                                lightningEntity.refreshPositionAfterTeleport(pos.x, pos.y, pos.z);
                                world.spawnEntity(lightningEntity);
                            }
                        }

                        // Additional effects based on level
                        if (level == 5 || level == 4) {
                            world.createExplosion(user, pos.x, pos.y, pos.z, 3, World.ExplosionSourceType.MOB);
                        } else if (level == 3 || level == 2) {
                            world.createExplosion(user, pos.x, pos.y, pos.z, 2, World.ExplosionSourceType.MOB);
                        }

                        user.getItemCooldownManager().set(this, cooldownTicks);
                    }
                }
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        PlayerEntity player = (PlayerEntity) entity;
        List <LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(10), e -> e != player);
        if (isThunderRainActive && durationTime > 0 && !world.isClient) {
            if (delayTime <= 0) {
                for (LivingEntity e : entities) {
                    if (e != player) {
                        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                        if (lightningEntity != null) {
                            lightningEntity.refreshPositionAfterTeleport(e.getX(), e.getY(), e.getZ());
                            world.spawnEntity(lightningEntity);
                        }
                        e.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20, 2));
                    }
                }
                delayTime = THUNDERRAIN_DELAY;
            }
            delayTime--;
            durationTime--;
        } else if (durationTime <= 0) {
            isThunderRainActive = false;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private int getCooldownTicks(int level) {
        switch (level) {
            case 1, 2: return 10 * 20;
            case 3, 4: return 8 * 20;
            case 5: return 6 * 20;
            case 6: return 15 * 20;
            default: return 12 * 20;
        }
    }

    private int getLightningCount(int level) {
        return (level == 2 || level == 4) ? 2 : 1;
    }
}
