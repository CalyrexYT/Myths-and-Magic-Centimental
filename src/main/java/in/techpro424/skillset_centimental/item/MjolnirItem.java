package in.techpro424.skillset_centimental.item;

import in.techpro424.skillset_centimental.entity.MjolnirEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MjolnirItem extends AxeItem {

    public MjolnirItem() {
        super(ToolMaterials.NETHERITE, 6.0f, -3.0f, new FabricItemSettings().maxCount(1).fireproof().recipeRemainder(ModItems.MJOLNIR));
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        if(stack.isOf(ModItems.MJOLNIR)) return stack.copy();
        return super.getRecipeRemainder(stack);
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
    @Override
    public boolean damage(DamageSource source) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) return TypedActionResult.fail(itemStack);

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        if (i < 10) {
            return;
        }
        if (!world.isClient) {
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));

            MjolnirEntity mjolnir = new MjolnirEntity(world, playerEntity, stack);
            mjolnir.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f, 1.0f);

            if (playerEntity.getAbilities().creativeMode) mjolnir.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

            world.spawnEntity(mjolnir);
            world.playSoundFromEntity(null, mjolnir, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);

            if (!playerEntity.getAbilities().creativeMode) playerEntity.getInventory().removeOne(stack);


        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }
}