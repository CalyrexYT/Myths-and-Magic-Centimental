package in.kairoku.skillset_centimental.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class CryomancerStaffItem extends Item {
    private int level;
    private int COOLDOWN_TICKS;

    public CryomancerStaffItem() {
        super(new Item.Settings());
        this.level = 1;
        this.COOLDOWN_TICKS = 20 * 8; // 15 seconds
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
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            if (user.isSneaking()) {
                if (level < 6 && user.experienceLevel >= getXpCost()) {
                    level++;
                    if (level == 2 || level == 4) {
                        COOLDOWN_TICKS -= 20 * 2;
                        user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown decreased!").formatted(Formatting.GREEN), true);
                    } else if (level == 6){
                        COOLDOWN_TICKS += 20 * 6;
                    } else{
                        user.sendMessage(Text.literal("Upgraded to level " + level + "! Cryomancer ability upgraded!").formatted(Formatting.GREEN), true);
                    }
                    user.getItemCooldownManager().set(this, 20);
                } else if (level >= 6) {
                user.sendMessage(Text.literal("Staff at maximum level!").formatted(Formatting.RED), true);
                }
            } else if (!user.getItemCooldownManager().isCoolingDown(this)) {
                switch (level) {
                    case 0, 1:
                        shootIceShot(world, user);
                        break;
                    case 2, 3:
                        shootFrostSpread(world, user);
                        break;
                    case 4, 5:
                        shootFrostSpread2(world, user);
                        break;
                    case 6:
                        summonSubzero(world, user);
                        break;
                }
                user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
            } else {
                user.sendMessage(Text.literal("Cryomancer ability is on cooldown").formatted(Formatting.RED), true);
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void shootIceShot(World world, PlayerEntity player) {
        SnowballEntity snowball = new SnowballEntity(world, player);
        snowball.setPos(player.getX(), player.getEyeY(), player.getZ());
        snowball.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 4.0F, 1.0F);
        world.spawnEntity(snowball);
    }

    private void shootFrostSpread(World world, PlayerEntity player) {
        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(15), e -> e != player)) {
            entity.damage(player.getDamageSources().magic(), 10.0F);
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 1));
        }
    }

    private void shootFrostSpread2(World world, PlayerEntity player) {
        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(15), e -> e != player)) {
            entity.damage(player.getDamageSources().magic(), 20.0F);
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 1));
        }
    }

    private void summonSubzero(World world, PlayerEntity player) {
        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(15), e -> e != player)) {
            if (entity != player) {
                for (int xOffset = -1; xOffset <= 1; xOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        world.setBlockState(entity.getBlockPos().add(xOffset, -1, zOffset), Blocks.PACKED_ICE.getDefaultState());
                        world.setBlockState(entity.getBlockPos().add(xOffset, 0, zOffset), Blocks.PACKED_ICE.getDefaultState());
                        world.setBlockState(entity.getBlockPos().add(xOffset, 1, zOffset), Blocks.PACKED_ICE.getDefaultState());
                        world.setBlockState(entity.getBlockPos().add(xOffset, 2, zOffset), Blocks.PACKED_ICE.getDefaultState());
                    }
                }
                entity.damage(player.getDamageSources().magic(), 10.0F);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 2));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 60, 5));
            }
        }
    }
}