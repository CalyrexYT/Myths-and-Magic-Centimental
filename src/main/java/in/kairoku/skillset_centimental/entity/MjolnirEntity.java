package in.kairoku.skillset_centimental.entity;


import in.kairoku.skillset_centimental.SkillsetCentimental;
import in.kairoku.skillset_centimental.mixin.TridentEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;

public class MjolnirEntity extends TridentEntity {
    
    public MjolnirEntity(EntityType<? extends MjolnirEntity> entityType, World world) {
        super(entityType, world);
    }

    public MjolnirEntity(World world, LivingEntity owner, ItemStack stack) {
        super(world, owner, stack);
        this.dataTracker.set(TridentEntityAccessor.getLoyalty(), (byte)3);
    }

    @Override
    public boolean hasChanneling() {
        return true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Vec3d pos = entityHitResult.getPos();
        this.getWorld().createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 7, ExplosionSourceType.MOB);
        BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
        if(this.getWorld().isSkyVisible(blockPos)) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.getWorld());
            if(lightningEntity != null) {
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                Entity owner = this.getOwner();
                lightningEntity.setChanneler(owner instanceof ServerPlayerEntity ? (ServerPlayerEntity)owner : null);
                this.getWorld().spawnEntity(lightningEntity);

                this.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, 5.0f, 1.0f);
            }
        }
            
        
        super.onEntityHit(entityHitResult);
    }
    
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Vec3d pos = blockHitResult.getPos();
        this.getWorld().createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 4, ExplosionSourceType.MOB);
        this.getWorld().createExplosion(this, pos.getX(), pos.getY(), pos.getZ(), 4, ExplosionSourceType.MOB);
        BlockPos blockPos = blockHitResult.getBlockPos();
        if(this.getWorld().isSkyVisible(blockPos)) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.getWorld());
            if(lightningEntity != null) {
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                Entity owner = this.getOwner();
                lightningEntity.setChanneler(owner instanceof ServerPlayerEntity ? (ServerPlayerEntity)owner : null);
                this.getWorld().spawnEntity(lightningEntity);

                this.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, 5.0f, 1.0f);
            }
        }
            
        SkillsetCentimental.LOGGER.info(this.pickupType.toString());
        super.onBlockHit(blockHitResult);
    }
    
    @Override
    protected boolean shouldFall() {
        Vec3d pos = this.getPos();
        if(pos.getY() <= -63) {
            this.teleport(pos.getX(), -62, pos.getZ());
            return false;
        }
        return super.shouldFall();
    }

    @Override
    public EntityType<?> getType() {
        return SkillsetCentimental.MJOLNIR;
    }


    
}
