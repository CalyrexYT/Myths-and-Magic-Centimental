package in.techpro424.skillset_centimental.entity;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import in.techpro424.skillset_centimental.SkillsetCentimental;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.ServerConfigHandler;
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
    public void onPlayerCollision(PlayerEntity player) {
        if(this.canAttack(player)) player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 1, 4), this);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
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