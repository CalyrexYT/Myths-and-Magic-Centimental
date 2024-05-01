package in.kairoku.skillset_centimental.entity;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import in.kairoku.skillset_centimental.SkillsetCentimental;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PortalEntity extends Entity {
    protected static final TrackedData<Optional<UUID>> PAIR_UUID = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public PortalEntity(EntityType<?> type, World world) {
        super(type, world);

    }

    public PortalEntity(PortalEntity pair, EntityType<?> type, World world) {
        this(type, world);
        this.setPair(pair);
        pair.setPair(this);
    }

    public PortalEntity(ServerPlayerEntity owner, EntityType<?> type, World world) {
        this(type, world);
        this.setOwner(owner);
    }

    public PortalEntity(ServerPlayerEntity owner, PortalEntity pair, EntityType<?> type, World world) {
        this(pair, type, world);
        this.setOwner(owner);
    }
    @Override
    public void onPlayerCollision(PlayerEntity player) {
        PortalEntity pair = this.getPair();
        if(pair != null) {
            player.teleport(pair.getX(), pair.getY(), pair.getZ());
            pair.discard();
            this.discard();
        }
    }

    public static void summon(ServerPlayerEntity player) {
        HitResult hit = player.raycast(20, 0, false); // 20 is distance used by the DebugHud for "looking at block", false means ignore fluids
        Vec3d pos = hit.getPos();

        ServerWorld world = player.getServerWorld();
        List<PortalEntity> portals = world.getEntitiesByType(SkillsetCentimental.PORTAL, new Box(player.getBlockPos()).expand(Math.pow(10, 7) * 3), (portal) -> portal.getPair() == null);
        portals.forEach((portal) -> {
            if(portal.isOwner(player)) {
                if(portal.getPair() != null) return;
                PortalEntity portal1 = new PortalEntity(player, portal, SkillsetCentimental.PORTAL, world);
                portal1.setPosition(pos);
                world.spawnEntity(portal1);
            }

        });

        PortalEntity portal2 = new PortalEntity(player, SkillsetCentimental.PORTAL, world);
        portal2.setPosition(pos);
        world.spawnEntity(portal2);

    }

    public PortalEntity getPair() {
        if(!(this.getWorld() instanceof ServerWorld)) return null;
        UUID uUID = this.getPairUuid();
        if (uUID == null) return null;

        ServerWorld serverWorld = ((ServerWorld)this.getWorld());
        Entity entity = serverWorld.getEntity(uUID);
        if(entity instanceof PortalEntity) return ((PortalEntity)entity);
        return null;
    }



    public boolean isPair(PortalEntity entity) {
        return this.getPair().isOwner(entity.getOwner());
    }

    @Nullable
    public UUID getPairUuid() {
        return this.dataTracker.get(PAIR_UUID).orElse(null);
    }

    public void setPairUuid(@Nullable UUID uuid) {
        this.dataTracker.set(PAIR_UUID, Optional.ofNullable(uuid));
    }

    public void setPair(PortalEntity pair) {
        this.setPairUuid(pair.getUuid());

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
    protected void initDataTracker() {
        this.dataTracker.startTracking(PAIR_UUID, Optional.empty());
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        UUID uUID;
        if (nbt.containsUuid("Pair")) {
            uUID = nbt.getUuid("Pair");
        } else {
            String string = nbt.getString("Pair");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID != null) {
            try {
                this.setPairUuid(uUID);

            } catch (Throwable throwable) {
                SkillsetCentimental.LOGGER.error(throwable.getLocalizedMessage(), throwable);
            }
        }

        UUID uUID2;
        if (nbt.containsUuid("Owner")) {
            uUID2 = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID2 != null) {
            try {
                this.setOwnerUuid(uUID2);

            } catch (Throwable throwable) {
                SkillsetCentimental.LOGGER.error(throwable.getLocalizedMessage(), throwable);
            }
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.getPairUuid() != null) {
            nbt.putUuid("Pair", this.getPairUuid());
        }

        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    @Override
    public EntityType<?> getType() {
        return SkillsetCentimental.PORTAL;
    }

}