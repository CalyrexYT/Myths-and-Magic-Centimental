package in.kairoku.skillset_centimental.entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.DataTracker.Builder;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class BlackHoleEntity extends Entity {
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(BlackHoleEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public BlackHoleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public BlackHoleEntity(PlayerEntity owner, EntityType<?> type, World world) {
        this(type, world);
        this.setOwner(owner);
    }

    @Override
    public void tick() {

        super.tick();
        BlockPos pos = this.getBlockPos();
        Box box  = Box.enclosing(pos.east(15).north(15).up(15), pos.west(15).south(15).down(15));
        LivingEntity owner = this.getOwner();
        List<Entity> entities = this.getWorld().getOtherEntities(owner != null ? owner : this, box, (entity) -> {
            return (int)this.getPos().distanceTo(entity.getPos()) <= 15;
        });
        if(this.age >= 20 * 10) {
            for (Entity e: entities){
                if (e instanceof LivingEntity){
                    ((LivingEntity) e).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20*10, 2), this);
                    ((LivingEntity) e).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20*10, 5), this);
                    ((LivingEntity) e).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 20*10, 2), this);
                }
            }
            this.discard();
            return;
        }
        for(Entity e : entities) {

            if (e instanceof PlayerEntity) {
                Vec3d velocity = this.getPos().subtract(e.getPos()).normalize().multiply(0.1235);
                e.addVelocity(velocity);
                if (e.getPos().distanceTo(this.getPos())<=1.0)
                    ((LivingEntity) e).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 10, 5), this);
            } else if (e instanceof LivingEntity) {
                Vec3d velocity = this.getPos().subtract(e.getPos()).normalize().multiply(0.2);
                e.addVelocity(velocity);
                if (e.getPos().distanceTo(this.getPos())<=1.0)
                    ((LivingEntity) e).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 10, 5), this);
            }
            else{
                Vec3d velocity = this.getPos().subtract(e.getPos()).normalize().multiply(0.3);
                e.addVelocity(velocity);
            }
        }
    }

    public boolean canAttack(LivingEntity entity) {
        if(this.isOwner(entity) || this.isTeammate(entity)) return false;
        return true;
    }

    public LivingEntity getOwner() {
        UUID uUID = this.getOwnerUuid();
        if (uUID == null) {
            return null;
        }
        return this.getWorld().getPlayerByUuid(uUID);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Nullable
    public UUID getOwnerUuid() {
        return this.dataTracker.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player) {
        this.setOwnerUuid(player.getUuid());
        
    }

    @Override
    protected void initDataTracker(Builder builder) {
        builder.add(OWNER_UUID, Optional.empty());
    }
    
    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        UUID uUID;
        if (nbt.containsUuid("Owner")) {
            uUID = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID != null) {
            try {
                this.setOwnerUuid(uUID);
                
            } catch (Throwable throwable) {
                SkillsetCentimental.LOGGER.error(throwable.getLocalizedMessage(), throwable);
            }
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    @Override
    public Team getScoreboardTeam() {
        LivingEntity livingEntity;
        if ((livingEntity = this.getOwner()) != null) {
            return livingEntity.getScoreboardTeam();
        }
        return super.getScoreboardTeam();
    }

    @Override
    public EntityType<?> getType() {
        return SkillsetCentimental.BLACK_HOLE;
    }

    
    
}
