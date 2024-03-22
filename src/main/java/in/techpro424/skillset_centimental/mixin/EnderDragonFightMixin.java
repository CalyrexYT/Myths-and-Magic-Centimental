package in.techpro424.skillset_centimental.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import in.techpro424.skillset_centimental.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {

    @Final @Mutable @Shadow private ServerWorld world;
    @Final @Mutable @Shadow private BlockPos origin;


    //Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z
    @Inject(method = "dragonKilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
    private void spawnMjolnir(EnderDragonEntity enderDragonEntity, CallbackInfo ci) {
        BlockPos pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.offsetOrigin(origin)).up();

        world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());

        world.setBlockState(pos.north().up(), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos.south().up(), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos.east().up(), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos.west().up(), Blocks.OBSIDIAN.getDefaultState());

        world.setBlockState(pos.up(2), Blocks.OBSIDIAN.getDefaultState());

        ItemEntity entity = new ItemEntity(world, pos.up().getX(), pos.up().getY(), pos.up().getZ(), new ItemStack(ModItems.MJOLNIR));
        world.spawnEntity(entity);
    }
}
