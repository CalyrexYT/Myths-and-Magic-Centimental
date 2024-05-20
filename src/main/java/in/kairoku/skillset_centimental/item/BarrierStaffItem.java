package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;


public class BarrierStaffItem extends Item {
    private int level;
    private boolean isBarrierTime = false;
    private int userX;
    private int userY;
    private int userZ;
    private Iterable<Entity> entities;
    private PlayerEntity user;
    private int barrierTime = 0;
    public BarrierStaffItem() {
        super(new Item.Settings().maxCount(1));
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
            if (user.isSneaking() && !user.getItemCooldownManager().isCoolingDown(this)) {
                if (level < 6 && user.experienceLevel >= getXpCost()) {
                    level++;
                    if (level == 2 || level == 4 || level == 6) {
                        user.sendMessage(Text.literal("Upgraded to level " + level + "! Barrier duration increased!").formatted(Formatting.GREEN), true);
                    } else {
                        user.sendMessage(Text.literal("Upgraded to level " + level + "! Cooldown decreased").formatted(Formatting.GREEN), true);
                    }
                    user.getItemCooldownManager().set(this, 20);
                } else if (level >= 6) {
                    user.sendMessage(Text.literal("Staff at maximum level!").formatted(Formatting.RED), true);
                } else {
                    user.sendMessage(Text.literal("You don't have enough experience levels to upgrade!").formatted(Formatting.RED), true);
                }
            }
            else if (!user.getItemCooldownManager().isCoolingDown(this)) {
                // coordinates for the barrier
                userX = (int) user.getX();
                userY = (int) user.getY();
                userZ = (int) user.getZ();
                BlockPos playerPos = new BlockPos(userX, userY, userZ);
                Box box = new Box(playerPos.getX() - 15, playerPos.getY() - 15, playerPos.getZ() - 15,
                        playerPos.getX() + 15, playerPos.getY() + 15, playerPos.getZ() + 15);
                // Get the entities within the box
                entities = world.getEntitiesByClass(Entity.class, box, entity -> true);
                summonBarrier(world, userX, userZ);
                for (Entity e: entities){
                    if (e instanceof LivingEntity){
                        e.setPosition(user.getX(), 312.0, user.getZ());
                    }
                }
                user.teleport(user.getX(), 312.0, user.getZ());
                // Apply cooldown
                user.getItemCooldownManager().set(this, getCooldownTicks(level));
                // Apply barrier time
                barrierTime = getBarrierTime(level);
                isBarrierTime = true;
                this.user = user;
            } else {
                user.sendMessage(Text.literal("Barrier ability is on cooldown!").formatted(Formatting.RED), true);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof PlayerEntity) {
            barrierTime--;
            if (barrierTime<=0&&isBarrierTime){
                clearBarrier(world, userY, userX, userZ);
                for (Entity e: entities){
                    if (e instanceof LivingEntity){
                        e.setPosition(user.getX(), userY, user.getZ());
                    }
                }
                user.teleport(user.getX(), userY, user.getZ());
                barrierTime = getBarrierTime(level);
                isBarrierTime = false;
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
    private int getCooldownTicks(int level) {
        switch (level) {
            case 1, 2, 6: return 50 * 20;
            case 3, 4: return 45 * 20;
            case 5: return 30 * 20;
            default: return 60 * 20;
        }
    }
    private int getBarrierTime(int level) {
        switch (level) {
            case 2, 3: return 15 * 20;
            case 4, 5 : return 18 * 20;
            case 6: return 20 * 20;
            default: return 12 * 20;
        }
    }
    private void summonBarrier(World world, int playerX, int playerZ) {
        //Layer 1
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 - 1, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 - 1, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 - 1, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 - 1, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 - 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 - 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 - 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 - 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 - 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 - 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 - 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 - 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 - 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 - 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 - 1, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 - 1, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 - 1, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 - 1, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 - 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 - 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 - 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 - 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 - 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 - 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 - 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 - 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //Layer 2
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 3
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 1, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 1, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 1, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 1, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 1, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 1, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 1, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 1, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 1, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 1, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 1, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 1, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 4
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310 + 2, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 2, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 2, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 2, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 2, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 2, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 2, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 2, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 2, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 2, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 2, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 2, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 2, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 2, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 2, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 2, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 2, playerZ + 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 2, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 2, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 2, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 2, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 2, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 2, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 2, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 2, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 2, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 2, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 2, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 2, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 2, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 2, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 2, playerZ - 8), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 5
        if (true) {
            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ + 2 ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 3, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 3, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 3, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 3, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 3, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 3, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 3, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 3, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 3, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 3, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 6
        if (true) {
            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ + 2 ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 4, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 4, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 4, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 4, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 4, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 4, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 4, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 4, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 4, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 4, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 4, playerZ + 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 4, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 4, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 4, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 4, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 4, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 4, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 4, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 4, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 4, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 4, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 4, playerZ - 7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 7
        if (true) {
            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ + 2 ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 5, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 5, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 5, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 5, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 5, playerZ + 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 5, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 5, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 5, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 5, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 5, playerZ - 6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 8
        if (true) {
            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 6, playerZ + 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 6, playerZ - 5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }
        //layer 9
        if (true) {
            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ + 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ - 4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ + 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ - 3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
        }

        //layer 10
        if (true) {
            world.setBlockState(new BlockPos(playerX, 310 +8, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 +8, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 8, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 8, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 8, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 8, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ + 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ + 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ - 2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 8, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 8, playerZ - 1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

        }
        //floor
        if (true){
            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);


            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ+1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ+2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);


            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);


            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ-1), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ-2), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-3), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-4), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-5), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-6), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-7), ModBlocks.BARRIER_BLOCK.getDefaultState(), 3);

        }

    }
    private void clearBarrier(World world, int tpY, int playerX, int playerZ) {
        //Layer 1
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 - 1, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 - 1, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 - 1, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 - 1, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 - 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 - 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 - 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 - 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 - 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 - 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 - 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 - 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 - 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 - 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 - 1, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 - 1, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 - 1, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 - 1, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 - 1, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 - 1, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 - 1, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 - 1, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 - 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 - 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 - 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 - 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 - 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 - 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 - 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 - 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
        }
        //Layer 2
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 3
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 1, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 1, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 1, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 1, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 1, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 1, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 1, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 1, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 1, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 1, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 1, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 1, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 1, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 1, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 1, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 1, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 4
        if (true) {
            world.setBlockState(new BlockPos(playerX + 8, 310 + 2, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 2, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 2, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 2, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 2, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 2, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 2, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 2, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 2, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 2, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 2, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 2, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 2, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 2, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 2, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 2, playerZ + 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 2, playerZ + 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 2, playerZ - 8), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 8, 310 + 2, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 8, 310 + 2, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 2, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 2, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 2, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 2, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 2, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 2, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 2, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 2, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 2, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 2, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 2, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 2, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 2, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 2, playerZ - 8), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 5
        if (true) {
            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ + 2 ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 3, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 3, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 3, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 3, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 3, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 3, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 3, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 3, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 3, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 3, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 3, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 3, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 3, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 3, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 6
        if (true) {
            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ + 2 ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 4, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 4, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 4, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 4, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 4, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 4, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 4, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 4, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 4, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 4, playerZ + 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 4, playerZ + 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 4, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 7, 310 + 4, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 7, 310 + 4, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 4, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 4, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 4, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 4, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 4, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 4, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 4, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 4, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 4, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 4, playerZ - 7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 4, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 4, playerZ - 7), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 7
        if (true) {
            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ + 2 ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 5, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 5, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 5, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 5, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 5, playerZ + 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 6, 310 + 5, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 6, 310 + 5, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 5, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 5, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 5, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 5, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 5, playerZ - 6), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 8
        if (true) {
            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 6, playerZ + 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 5, 310 + 6, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 5, 310 + 6, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 6, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 6, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 6, playerZ - 5), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 9
        if (true) {
            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ + 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ + 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 4, 310 + 7, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 4, 310 + 7, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 3, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 3, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 7, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 7, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 7, playerZ - 4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 7, playerZ - 4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ + 3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 7, playerZ - 3), Blocks.AIR.getDefaultState(), 3);
        }
        //layer 10
        if (true) {
            world.setBlockState(new BlockPos(playerX, 310 +8, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 +8, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 8, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 8, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 8, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 8, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ + 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ + 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ + 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ + 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX, 310 + 8, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 1, 310 + 8, playerZ - 2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 1, 310 + 8, playerZ - 2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX + 2, 310 + 8, playerZ - 1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX - 2, 310 + 8, playerZ - 1), Blocks.AIR.getDefaultState(), 3);

        }
        //floor
        if (true){
            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);


            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ+1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ+2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ+4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ+5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ+6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ+7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ+7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ+7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ+7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ+7), Blocks.AIR.getDefaultState(), 3);


            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);


            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ-1), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+7, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-7, 310 -1, playerZ-2), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-3), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+6, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-6, 310 -1, playerZ-4), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+5, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-5, 310 -1, playerZ-5), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+3, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-3, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+4, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-4, 310 -1, playerZ-6), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX, 310 -1, playerZ-7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+1, 310 -1, playerZ-7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-1, 310 -1, playerZ-7), Blocks.AIR.getDefaultState(), 3);

            world.setBlockState(new BlockPos(playerX+2, 310 -1, playerZ-7), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(new BlockPos(playerX-2, 310 -1, playerZ-7), Blocks.AIR.getDefaultState(), 3);

        }
    }
}
