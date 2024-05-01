package in.kairoku.skillset_centimental.item;

import in.kairoku.skillset_centimental.entity.MjolnirEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MjolnirItem extends AxeItem {

    public MjolnirItem() {
        super(CustomToolMaterial.INSTANCE, 10.0f, -3.0f, new FabricItemSettings().maxCount(1).fireproof().recipeRemainder(ModItems.MJOLNIR));
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
        boolean riptide = playerEntity.isSneaking();
        if (!world.isClient) {
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
            
            if(!riptide) {
                
                MjolnirEntity mjolnir = new MjolnirEntity(world, playerEntity, stack);
                mjolnir.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f, 1.0f);

                if (playerEntity.getAbilities().creativeMode) mjolnir.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                
                world.spawnEntity(mjolnir);
                world.playSoundFromEntity(null, mjolnir, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);

                if (!playerEntity.getAbilities().creativeMode) playerEntity.getInventory().removeOne(stack);
                
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        if (riptide) {
            int j = 5;
            float f = playerEntity.getYaw();
            float g = playerEntity.getPitch();
            float h = -MathHelper.sin(f * ((float)Math.PI / 180)) * MathHelper.cos(g * ((float)Math.PI / 180));
            float k = -MathHelper.sin(g * ((float)Math.PI / 180));
            float l = MathHelper.cos(f * ((float)Math.PI / 180)) * MathHelper.cos(g * ((float)Math.PI / 180));
            float m = MathHelper.sqrt(h * h + k * k + l * l);
            float n = 3.0f * ((1.0f + (float)j) / 4.0f);
            playerEntity.addVelocity(h *= n / m, k *= n / m, l *= n / m);
            playerEntity.useRiptide(20);
            if (playerEntity.isOnGround()) {
                float o = 1.1999999f;
                playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
            }
            SoundEvent soundEvent = j >= 3 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_3 : (j == 2 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_2 : SoundEvents.ITEM_TRIDENT_RIPTIDE_1);
            world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

}
